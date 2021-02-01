package corre.ware;

import java.io.File;

import android_serialport_api.SerialPort;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 12时 01分
 * @Data： OkWareManager串口通讯请求接口
 * 实现功能：
 * 一、能够支持简单串口开启请求
 * 二、能够支持简单串口关闭请求
 * 三、如果有新的需求的话，那么可以在这个接口里新增对应方法
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface OkWareManager {

    /**
     * open开启串口通讯回调
     *
     * @param file 串口读取文件路径（开启的对应端口号）
     * @param baudrate 波特率
     * @param flag 默认为0
     * @param okWareOpenCallBack 开启串口成功和失败的回调
     */
    void open(File file,int baudrate,int flag,OkWareOpenCallBack okWareOpenCallBack);

    /**
     * read读取串口数据回调
     *
     * @param okWareReadCallBack 读取成功与否返回的接口
     */
    void read(OkWareReadCallBack okWareReadCallBack);

    /**
     * write写串口数据回调
     *
     * @param data 串口传输数据
     * @param okWareWriteCallBack 传输成功和失败的回调
     */
    void write(String data,OkWareWriteCallBack okWareWriteCallBack);

    /**
     * 关闭串口通讯
     *
     * @param okWareCloseCallBack 关闭串口成功和失败的回调
     */
    void close(OkWareCloseCallBack okWareCloseCallBack);

}
