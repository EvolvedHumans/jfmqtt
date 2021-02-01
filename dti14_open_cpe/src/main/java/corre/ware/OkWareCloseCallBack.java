package corre.ware;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 12时 53分
 * @Data： OkWareCloseCallBack接口
 * 关闭有无异常的回调
 * 1.成功，则返回成功提示语
 * 2.失败，抛出异常
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface OkWareCloseCallBack {

    void success(String response);

    void failed(Throwable throwable);

    void deadly(String response);

}
