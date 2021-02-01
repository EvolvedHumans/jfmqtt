package corre.ware;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 12时 18分
 * @Data： OkWareWriteCallBack接口
 * 写数据成功后，写数据成功与否的回调
 * 1.当发送数据成功后，会将数据以byte字节格式返回
 * 2.失败，抛异常
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface OkWareWriteCallBack {

    void success(byte[] bytes);

    void failed(Throwable throwable);

    void deadly(String response);

}
