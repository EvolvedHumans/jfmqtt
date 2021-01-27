package com.example.jymqtt;

import android.os.Handler;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      MqttClient客户端
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 13时 40分
 */
public class MqttClient implements MqttClientInterface {

    private final static String TAG = "com.example.jymqtt.MqttClient";

    private MqttAndroidClient mqttAndroidClient;

    private String host;

    private String clientId;

    private Handler handler;

    public static String getTAG() {
        return TAG;
    }

    public MqttAndroidClient getMqttAndroidClient() {
        return mqttAndroidClient;
    }

    public void setMqttAndroidClient(MqttAndroidClient mqttAndroidClient) {
        this.mqttAndroidClient = mqttAndroidClient;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 初始化Mqtt 断开重连、清除缓存、超时时间、心跳包、用户名、密码
     *
     * @return 实例返回
     */
    private MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(10);
        mqttConnectOptions.setKeepAliveInterval(100);
        return mqttConnectOptions;
    }


    @Override
    public void connect(){
        //不允许重复连接
        if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
            disconnect();
        }
        mqttAndroidClient = new MqttAndroidClient(AppUtils.getContext(), host, clientId);
        mqttAndroidClient.setCallback(new MqttClientCallback(this)); //MqttCallback监听
        try {
            mqttAndroidClient.connect(getMqttConnectOptions(),new ClientConnectActionListener());
        }catch (MqttException e){
            Log.e(TAG,"[MQTT] client 抛异常"+e);
        }
    }

    @Override
    public void subscribe(ClientSubscribe clientSubscribe) {
        if (mqttAndroidClient == null) {
            Log.e(TAG, "[MQTT] client 请先连接MQTT服务器");
        } else if (!mqttAndroidClient.isConnected()){
            Log.e(TAG,"[MQTT] client 处于断开状态");
        }else {
            try {
                mqttAndroidClient.subscribe(clientSubscribe.topic,clientSubscribe.qos,AppUtils.getContext(),new ClientSubscribeActionListener());
            }catch (MqttException e){
                Log.e(TAG,"[MQTT] client subscribe 抛异常："+e);
            }
        }
    }


    @Override
    public void disconnect() {
        if (mqttAndroidClient == null) {
            Log.e(TAG, "[MQTT] client 请先连接MQTT服务器");
        } else {
            mqttAndroidClient.close();
        }
    }

    @Override
    public void sendMsg(Object msg) {
        if (mqttAndroidClient == null) {
            Log.e(TAG, "[MQTT] client 请先连接MQTT服务器");
        } else if (!mqttAndroidClient.isConnected()){
            Log.e(TAG,"[MQTT] client 处于断开状态");
        }else {
            ClientPublish clientPublish = (ClientPublish)msg;
            if(clientPublish!=null){
                try {
                    mqttAndroidClient.publish
                            (clientPublish.getTopic(),clientPublish.getMessage().getBytes(StandardCharsets.UTF_8),clientPublish.getQos(),false);
                }catch (MqttException e){
                    Log.e(TAG,"[MQTT] client send 抛出异常"+e);
                }
            }
        }
    }

    @Override
    public boolean isConnect() {
        if(mqttAndroidClient == null){
            return false;
        }
        else return mqttAndroidClient.isConnected();
    }
}
