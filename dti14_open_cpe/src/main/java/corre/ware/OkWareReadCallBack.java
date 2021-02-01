package corre.ware;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 12时 17分
 * @Data： kWareReadCallBack接口
 * 读数据成功与否的回调
 * 1.当读取数据成功后，会将读取到的数据返回
 * 2.异常失败，抛异常
 * 3.出现致命，必须马上关闭串口通讯，并返回我这边得出的错误原因
 * @TechnicalPoints：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface OkWareReadCallBack {

    void success(byte[] bytes);

    void failed(Throwable throwable);

    void deadly(String response);

}
