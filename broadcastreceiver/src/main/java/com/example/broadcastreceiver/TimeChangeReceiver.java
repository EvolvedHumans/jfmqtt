package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 26日 17时 14分
 * @Data： 继承BroadcastReceiver，改写为时间广播。
 * 动态注册广播，改写就可以了，不需要在过多封装了
 * 系统每个一分钟就会发出一条"android.intent.action.TIME_TICK"的广播
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class TimeChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("1","1");
        Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
    }
}
