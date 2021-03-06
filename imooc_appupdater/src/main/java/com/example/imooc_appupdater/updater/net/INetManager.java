package com.example.imooc_appupdater.updater.net;

import java.io.File;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 20日 12时 24分
 * @Data： 网络请求接口
 * 设计思路：首先要考虑这个接口要提供什么样的能力
 * 一.首先，它能支持简单的get请求
 * get请求需要我们提供请求的url和callback回调，具体的回调需要在新增接口文件INetCallBack
 *
 * 二、除了get请求，我们还需要一个download请求
 * download请求需要我们提供请求的url、下载的地址File、callback回调，
 * 具体回调需要在新增接口文件INetDownloadCallBack
 *
 * 三、如果有新的需求的话，那么可以在这个接口里新增对应方法
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface INetManager {

    void get(String url,INetCallBack callBack);

    void download(String url,File targetFile,INetDownloadCallBack callBack);

}
