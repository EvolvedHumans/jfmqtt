package com.example.dti14_open_cpe;

import android.app.Activity;
import android.os.Bundle;

import com.yangf.pub_libs.Log4j;

import java.io.File;

import corre.ware.OkWare;
import corre.ware.OkWareManager;
import corre.ware.OkWareOpenCallBack;

public class MainActivity extends Activity {

    private static String TAG = "com.example.dti14_open_cpe.MainActivity";

    OkWareManager okWareManager = OkWare.getInstance().setGoogleOkWare();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(3000);
        }catch (Exception e){

        }


        okWareManager.open(new File("/dev/ttyHSL1"), 9600, 0, new OkWareOpenCallBack() {
            @Override
            public void success(String response) {
                Log4j.d(TAG,response);
            }

            @Override
            public void failed(Throwable throwable) {
                Log4j.d(TAG,throwable.toString());
            }

            @Override
            public void deadly(String response) {
                Log4j.e(TAG,response);
            }
        });

    }

}
