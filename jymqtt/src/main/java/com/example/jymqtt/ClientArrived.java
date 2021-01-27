package com.example.jymqtt;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO 消息接收方法
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 14时 40分
 */
public class ClientArrived {
    private String topic;
    private String message;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
