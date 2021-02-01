package corre.ware;

import com.yangf.pub_libs.CRCUtil;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import android_serialport_api.SerialPort;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 13时 22分
 * @Data： GoogleOkWare，底层逻辑接口由Google提供,故命名为：GoogleOkWare方法
 * 继承方法OkWareManager，对串口通讯的开、读、写、关闭进行重写
 * 统一格式，在读、写中为保证数据的绝对准确与安全进行内部封装
 * 改写的这个方法与其它外部接口不兼容，因为在数据传输过程中进行了报文头、报文尾的校验
 * <p>
 * 1.报文头： 3 byte  （0x53、0x5A、0x59）
 * 2.报文尾： 2 byte  （0x50、0x26）
 * 4.校验位： 8 byte （通过CRC32校验算法换算进行加密、解密校验）
 * 3.报文内容：可变 byte ，在OkWareReadCallBack中返回
 * <p>
 * 数据按utf-8格式发送
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class GoogleOkWare implements OkWareManager {

    private static InputStream inputStream;
    private static OutputStream outputStream;

    /**
     * 串口打开标志位，用于记录出串口开启情况
     * <p>
     * Init is false
     * <p>
     * open->true
     * <p>
     * close->false
     * <p>
     * catch->false
     */
    private static boolean wareStates = false;

    /**
     * 读取线程状态
     * <p>
     * Init is false
     * <p>
     * open->true
     * <p>
     * close->false
     * <p>
     * catch->false
     */
    private static boolean threadStatus = false;


    /**
     * 定义一次能收到的最大包长（字节！！！）
     * <p>
     * 静态，固定不可变
     * <p>
     * 一条信息绝对不能超过这个数
     * <p>
     * 架构规范！
     */
    private static final int MAX_LENGTH = 16777221;


    /**
     * 定义请求头的字节数
     * <p>
     * 静态，固定不可变
     * <p>
     * 架构规范！
     */
    private static final int HEARDER_LENGTH = 3;

    /**
     * 协议帧头字节
     * <p>
     * 静态，固定不可变
     * <p>
     * 架构规范
     */
    private static final byte[] HEAD = {0x53, 0x5A, 0x59};


    /**
     * 定义帧尾的字节数
     * <p>
     * 静态，固定不可变
     * <p>
     * 架构规范！
     */
    private static final int TAIL_LENGHT = 2;

    /**
     * 协议帧尾字节
     * <p>
     * 静态，固定不可变
     * <p>
     * 架构规范
     */
    private static final byte[] END = {0x50, 0x26};

    /**
     * 获取协议内容长度
     * 总字节
     * 无用字节数
     * 校验字节数
     * 总字节 - 无用字节数 = 有效字节数
     * 有效字节数 = 校验字节数 + 数据字节
     *
     * @return 返回有效字节长度
     */
    public int parseLen(byte[] buffer, int index, int headerLength) {
        /*
        后两位帧头校验
         */
        byte a = buffer[index + 1];
        byte b = buffer[index + 2];
        int length = 0;

        if (a == 0x5A && b == 0x59) {
            //获取有效数据包
            //去掉帧头
            length = buffer.length - index - headerLength; //有效帧头长度
        }

        return length;
    }


    /**
     * tailLen方法，判断帧尾的完整性
     * <p>
     * 已经读到了最后一位，结束符，这里只需对结束符前一位进行校验
     * <p>
     * 因此只需要判断bytes-1位符不符合验证符就好了
     * <p>
     * 遇到过的问题：@java.lang.ArrayIndexOutOfBoundsException——数组越界
     * 传入错误
     *
     * @return 帧尾的校验结果
     */
    public boolean tailLen(byte[] buffer, int index) {

        //当index为0时，第一项就读到了终止位，如果放到了下一步，会出现上述备注出现的问题
        if (index == 0) {
            return false;
        }

        byte a = buffer[index - 1];

        return a == 0x50;
    }


    @Override
    public void open(File file, int baudrate, int flag, OkWareOpenCallBack okWareOpenCallBack) {
        if (!wareStates) {
            try {
                SerialPort serialPort = new SerialPort(file, baudrate, flag);
                inputStream = serialPort.getInputStream();
                outputStream = serialPort.getOutputStream();

                wareStates = true;
                threadStatus = true;

                okWareOpenCallBack.success("GoogleOkWare open successful!");
            } catch (Exception e) {
                okWareOpenCallBack.failed(e);
            }
        } else {
            okWareOpenCallBack.deadly("open异常(22000)");
        }

    }

    /**
     * 在读取操作中，需要对字节进行，截取、校验。拿到最终我们想要的字节，抛弃不需要的
     * <p>
     * 读取是非常耗时的操作，需要在子线程中进行
     * <p>
     * read 异常标号：
     * 1.异常(20000)
     * // 按常规来讲这里不可能为null，除非读终止位时超出了最大的包长;
     * // 导致后面的byteBuffer没能实例化,遇到这种情况马上关闭串口通讯。
     * <p>
     * 2.异常(20001)
     * // 总数据 - 终止位和起始位 = 最大内容长度，如果内容包大于这个长度，则说明这个包有问题，丢弃
     * <p>
     * 3.异常(20002)
     * // 当前获取到长度小于整个包的长度，则跳出循环等待接收数据
     * <p>
     * 4.异常(20003)
     * // 将数据包进行CRC校验的过程中异常，进行了丢包操作
     * <p>
     * 5.异常(22000)
     *
     * @param okWareReadCallBack 读取成功与否返回的接口
     */
    @Override
    public void read(OkWareReadCallBack okWareReadCallBack) {

        if (wareStates) {
            new Thread(() -> {

                byte[] byteBuffer = null;
                byte[] buffer = new byte[MAX_LENGTH]; //最大包
                int bytes; //字符串长度(当前已经收到包的长度)
                int ch; //读取字符的变量

                while (threadStatus) {
                    bytes = 0;//初长度
                    while (bytes < MAX_LENGTH) {
                        //循环获取字节流
                        //如果读到的是0x26,那么相当于读到了终止位
                        try {
                            if ((ch = inputStream.read()) != 0x26) {
                                if (ch != -1) {
                                    buffer[bytes] = (byte) ch; //将读取到的字符写入
                                    bytes++; //读取字符串+1
                                    continue;//重新循环
                                }
                            }
                        } catch (Exception e) {
                            //在读取过程中抛出异常，将异常放入回调接口中，让使用者去进行对应的处理
                            okWareReadCallBack.failed(e);
                        }

                        //java.lang.ArrayIndexOutOfBoundsException——数组越界
                        //错误原因：bytes>buffer最大数组长，
                        //否则，则读取到了结束位,验证帧尾完整性
                        if (!tailLen(buffer, bytes)) {
                            //如果不搜，那么则加上0x26。加上读到的错误的终止位
                            buffer[bytes] = 0x26;
                            bytes++;
                            continue;
                        }

                        //去掉帧尾
                        --bytes;

                        //中间变量，将后面的无用字节过滤掉，去到终止位前的所有的字节
                        byteBuffer = new byte[bytes];

                        //中间变量获取终止位前的所有字节
                        System.arraycopy(buffer, 0, byteBuffer, 0, bytes);

                        break;
                    }

                    //前面我们过滤了终止位后的无用字节。从这里开始，我们要过滤到前面的无用字符，记录起始位前的无用字节数
                    int cursor = 0;

                    //如果收到的包的长度大于起始位，那么则解析当前包
                    //->在这里作出假设：假设bytes位字节全为有效字节

                    if (bytes >= HEARDER_LENGTH && byteBuffer == null) {
                        // 按常规来讲这里不可能为null，除非读终止位时超出了最大的包长。
                        // 导致后面的byteBuffer没能实例化,遇到这种情况马上关闭串口通讯。
                        okWareReadCallBack.deadly("OkWareReadCallBack read is deadly error!!!,异常(20000)");
//                        break;
                    }


                    //前提byteBuffer != null
                    assert byteBuffer != null;
                    //清除出起始位前的无用字节,直到读取到0x53为止
                    if (byteBuffer[cursor] != 0x53) {
                        ++cursor; //无用字节+1
                        --bytes; //有效字节-1
                        continue;
                    }

                    //上一步已经读取到了0x53这个重要的帧头，现在开始进行帧头校验
                    //已知起始位前的阶段无用字节cursor、包含起始位的有效字节bytes
                    //1.开始帧头校验
                    //(一)、true 截取帧头 获取实际发送包
                    //(二)、false 继续当做无用字节丢弃，并重新循环
                    //contentLenght是有效字节长度
                    int contentLenght = parseLen(byteBuffer, cursor, HEARDER_LENGTH);

                    //MAX_LENGTH-5 为 总数据 - 终止位和起始位 = 最大内容长度，如果内容包大于这个长度，则说明这个包有问题，丢弃
                    if (contentLenght <= 0 && contentLenght > MAX_LENGTH - 3) {
                        bytes = 0; //将数据位置为 0
                        okWareReadCallBack.deadly("检测到内容包异常，异常(20001)");
                        break;
                    }

                    // factPackLen = 有效数据位 + 字节头
                    int factPacklen = contentLenght + 3;

                    //如果当前获取到长度小于整个包的长度，则跳出循环等待接收数据
                    if (bytes < contentLenght + 3) {
                        okWareReadCallBack.deadly("检测到内容包异常，异常(20002)");
                        break;
                    }

                    //获取有效数据位(包含了验证真正需要数据的CRC校验位),有效数据位 = 无用字节 + 帧头
                    byte[] validBufferCRC = new byte[cursor + HEARDER_LENGTH];

                    System.arraycopy(buffer, cursor + HEARDER_LENGTH, validBufferCRC, 0, contentLenght);

                    //有效数据位CRC32校验异常
                    if (!CRCUtil.verCRC32(validBufferCRC)) {
                        //bytes = 0; //将数据位置为 0
                        okWareReadCallBack.deadly("检测到内容包异常，异常(20003)");
                        break;
                    }

                    //去掉后八位CRC校验
                    byte[] validBuffer = CRCUtil.cutCRC32(validBufferCRC);

                    //回调
                    okWareReadCallBack.success(validBuffer);

                    //最后只剩下残留字节数的处理
                    //总长度 - 需要发送的包的总长 = 残余包长
                    bytes -= factPacklen;

                    //残留,将残留字节移到缓冲区
                    if (bytes > 0) {
                        System.arraycopy(buffer, cursor, buffer, 0, bytes);
                        okWareReadCallBack.deadly("检测到内容包的残余包，转移进入缓冲区。");
                    }

                }
            });
        }

        //如果串口未打开就发数据，那么则抛出异常
        else {
            okWareReadCallBack.deadly("read异常(22000)");
        }
    }

    /**
     * 写入回调
     * <p>
     * 写入异常
     * 异常(22000)
     * // 串口未开启不允许关闭
     *
     * @param data                串口传输数据
     * @param okWareWriteCallBack 传输成功和失败的回调
     */
    @Override
    public void write(String data, OkWareWriteCallBack okWareWriteCallBack) {

        //有效数据按utf-8格式发送
        //头与尾是在内部进行的，不需要特定格式
        if (wareStates) {
            byte[] send = data.getBytes(StandardCharsets.UTF_8);
            try {
                outputStream.write(HEAD);
                outputStream.write(send);
                outputStream.write(END);
                outputStream.flush();
                okWareWriteCallBack.success(send);
            } catch (Exception e) {
                okWareWriteCallBack.failed(e);
            }
        }

        //如果串口未打开就发数据，那么则抛出异常
        else {
            okWareWriteCallBack.deadly("write异常(22000)");
        }
    }

    /**
     * 关闭异常
     * <p>
     * close 异常标号：
     * 异常(22000)
     * // 串口未开启不允许关闭
     *
     * @param okWareCloseCallBack 关闭串口成功和失败的回调
     */
    @Override
    public void close(OkWareCloseCallBack okWareCloseCallBack) {
        if (wareStates) {
            try {
                inputStream.close();
                outputStream.close();
                okWareCloseCallBack.success("Close success!!!");
            } catch (Exception e) {
                okWareCloseCallBack.failed(e);
            } finally {
                wareStates = false;
                threadStatus = false;
            }
        } else {
            threadStatus = false; //先关闭读取
            okWareCloseCallBack.deadly("close异常(23000)");
        }

    }
}
