package com.example.qqmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //获取上一个activity中传过来的intent
        Intent intent = getIntent();

        Bean bean = (Bean) intent.getSerializableExtra("对象序列化");

        assert bean != null;
            Log.e("对象序列化", bean.toString());

        assert bean.getAge() != null;
            Log.e("对象序列化年龄取得", bean.getAge().toString());


        if(bean.getName()!=null)
            Log.e("对象序列化名字取得", bean.getName());

    }
}