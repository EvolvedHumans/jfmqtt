package com.example.iosdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import view.Mdialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Mdialog mdialog = new Mdialog(this);

        mdialog.setTextView("你好世界，我叫杨帆，自定义Dialog我很开心");

        mdialog.show();

        mdialog.setOnCancalListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果对话框是显示状态
                if(mdialog.isShowing()){
                    mdialog.dismiss();
                    finish();
                }
            }
        });

        mdialog.setOnExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mdialog!=null && mdialog.isShowing()){
                    mdialog.dismiss(); //关闭对话框
                }
            }
        });

    }

}
