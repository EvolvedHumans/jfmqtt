package dti.org.updater.net;

import okhttp3.Response;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 20日 12时 24分
 * @Data： INetCallBack是INetManager接口中get方法的callback回调的方法
 * callback回调是INetManager接口中get方法的网络请求方法
 * 需要根据网络请求方法回调的特性考虑返回对应的方法
 * 网络请求方法的回调无非包括：
 * 1.网络请求成功,回调成功提示字符串
 * 2.网络请求失败，带抛异常处理
 *
 *
 *
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface INetCallBack {
    void success(String content);
    void failed(Throwable throwable);
    void onComplete();
}
