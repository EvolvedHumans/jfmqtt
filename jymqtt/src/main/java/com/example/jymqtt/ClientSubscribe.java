package com.example.jymqtt;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO 客户端订阅主题
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 14时 57分
 */
public class ClientSubscribe {
    String topic;
    int qos;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }
}
