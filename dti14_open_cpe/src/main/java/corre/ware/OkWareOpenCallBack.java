package corre.ware;

import android_serialport_api.SerialPort;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 12时 53分
 * @Data： OkWareOpenCallBack接口，
 * 串口通讯回调接口
 * 1.如果回调成功，则返回实例对象
 * 2.失败，抛异常
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface OkWareOpenCallBack {

    void success(String response);

    void failed(Throwable throwable);

    void deadly(String response);

}
