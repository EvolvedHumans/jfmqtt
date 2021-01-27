package com.example.jymqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO MqttClient 连接状态监听和消息接口端口回调
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 14时 36分
 */
public class MqttClientCallback implements MqttCallbackExtended {

    private final static String TAG = "com.example.jymqtt.MqttClientCallback";

    private MqttClient mqttClient;

    int anInt = 0;

    public MqttClientCallback(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        //重连回调
        Log.d(TAG, "连接回调");
        Log.d(TAG, String.valueOf(reconnect));
        Log.d(TAG, serverURI);
    }

    @Override
    public void connectionLost(Throwable cause) {
        //失去连接
        Log.d(TAG, "失去连接");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        //消息接收
        Log.d(TAG, "消息接收");
        ClientArrived clientArrived = new ClientArrived();
        clientArrived.setTopic(topic);
        clientArrived.setMessage(new String(message.getPayload(), StandardCharsets.UTF_8));
        mqttClient.getHandler().obtainMessage(++anInt, clientArrived);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //未知
        Log.d(TAG, "未知");
    }
}
