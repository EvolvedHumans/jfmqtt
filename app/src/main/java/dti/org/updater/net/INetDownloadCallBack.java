package dti.org.updater.net;

import java.io.File;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 20日 12时 24分
 * @Data： INetDownloadCallBack接口中download方法的callback回调
 * callback回调是INetManager接口中download方法的网络请求方法
 * 需要根据网络请求方法文件下载回调的特性考虑返回对应的方法
 * 网络请求方法的回调无非包括：
 * 1.下载成功，回调File下载的文件
 * 2.下载进度，回调当前的下载进度
 * 3.下载失败，回调失败的异常Throwable
 *
 *
 *
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface INetDownloadCallBack {
    void success(File file);
    void progress(int progress);
    void failed(Throwable throwable);
}
