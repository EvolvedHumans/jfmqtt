package com.example.qqmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {


    @BindView(R.id.buttonPanel)
    Button buttonPanel;
    @BindView(R.id.buttonPane2)
    Button buttonPane2;
    @BindView(R.id.buttonPane3)
    Button buttonPane3;
    @BindView(R.id.buttonPane4)
    Button buttonPane4;
    @BindView(R.id.buttonPane5)
    Button buttonPane5;
    @BindView(R.id.buttonPane6)
    Button buttonPane6;
    @BindView(R.id.buttonPane7)
    Button buttonPane7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //将这个对象序列化
        Bean bean = new Bean();
        bean.setName("杨帆");
        bean.setAge(19);
        Log.e("bean", String.valueOf(bean));

        //将序列化对象通过intent传递
        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("对象序列化",bean);
        startActivity(intent);

        for (int i = 1;i<20;i++){
            int j = i%10;
            Log.e(String.valueOf(i),String.valueOf(j));
        }
    }

    @OnClick({R.id.buttonPanel, R.id.buttonPane2, R.id.buttonPane3, R.id.buttonPane4, R.id.buttonPane5, R.id.buttonPane6, R.id.buttonPane7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonPanel:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第一个按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonPane2:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第二个按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonPane3:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第三个按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonPane4:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第四个按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonPane5:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第五个按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonPane6:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第六个按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonPane7:
                Log.e("1","1");
                Toast.makeText(MainActivity.this,"点击第七个按钮",Toast.LENGTH_SHORT).show();
                break;
        }
    }




}