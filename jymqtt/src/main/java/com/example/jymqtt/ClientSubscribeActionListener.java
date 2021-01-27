package com.example.jymqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO mqtt主题订阅监听
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 15时 09分
 */
public class ClientSubscribeActionListener implements IMqttActionListener {

    private final static String TAG = "com.example.jymqtt.ClientSubscribeActionListener";

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.d(TAG,"订阅成功");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.d(TAG,"订阅失败");
    }
}
