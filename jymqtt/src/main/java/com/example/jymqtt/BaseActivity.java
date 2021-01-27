package com.example.jymqtt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 项目负责人： 杨帆
 * 包名：      com.example.jymqtt
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 13时 34分
 */
public abstract class BaseActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            message(msg.what, msg.obj);
        }
    };

    protected abstract void message(int what, Object obj);

}
