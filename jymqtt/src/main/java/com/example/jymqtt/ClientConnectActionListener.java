package com.example.jymqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO Mqtt连接监听
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 15时 21分
 */
public class ClientConnectActionListener implements IMqttActionListener {

    private final static String TAG = "com.example.jymqtt.ClientConnectActionListener";

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.d(TAG,"连接成功");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.d(TAG,"连接失败:" + exception);
    }
}
