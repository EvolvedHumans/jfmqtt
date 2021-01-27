package com.example.jymqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MqttClient mqttClient = new MqttClient();
        mqttClient.setHost("tcp://47.98.228.90:1883");
        mqttClient.setClientId("47.98.228.90:1883");
        mqttClient.setHandler(handler);
        mqttClient.connect();
    }

    /**
     * 消息接收通道
     *
     * @param what 消息条数记录
     * @param obj  消息内容
     */
    @Override
    protected void message(int what, Object obj) {
        Log.d(String.valueOf(what), String.valueOf(obj));
    }
}
