package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /*
    init
     */
    public void init(){
        IntentFilter intentFilter = new IntentFilter();
        //时间广播
        intentFilter.addAction("android.intent.action.TIME_TICK");

        TimeChangeReceiver timeChangeReceiver = new TimeChangeReceiver();

        registerReceiver(timeChangeReceiver,intentFilter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
