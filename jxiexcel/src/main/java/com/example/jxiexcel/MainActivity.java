package com.example.jxiexcel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.jxiexcel.save2excel.ExcelUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //如果文件不存在，则创建表
        if (ExcelUtils.wwbExist()){
            Log.d("1","111111");
            ExcelUtils.createExcel(this);
        }
    }

}
