package com.example.jymqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 14时 13分
 */
public interface MqttClientInterface {

    void connect() throws MqttException;

    void subscribe(ClientSubscribe subscribe) throws MqttException;

    void disconnect();

    void sendMsg(Object msg);

    boolean isConnect();


}
