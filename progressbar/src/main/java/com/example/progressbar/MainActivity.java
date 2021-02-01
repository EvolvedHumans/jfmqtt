package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

import com.yangf.pub_libs.Date;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("1",Date.modernClock()) ;

        //原生圆形加载进度
        ProgressBar progressBar = findViewById(R.id.progress_1);
        //在代码中控制显隐藏
        progressBar.setVisibility(View.VISIBLE);

        //横向加载条
        ProgressBar progressBar1 = findViewById(R.id.progress_2);

        //横向进度条，动态设置。
        //在音乐进度，网络下载时，需要动态加载进度条，默认情况下，设置进度条，使用setProgress()即可。
        //但有时除了动态设置进度，仍需要动态设置进度条颜色



        // 参数说明：  //new ColorDrawable(Integer类型表示要加载的背景)
        //        ClipDrawable drawable = new ClipDrawable(new ColorDrawable(R.drawable.progressbar_bg), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //        progressBar1.setProgressDrawable(drawable);
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(0,100,1, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 1. 从3开始，一共发送10个事件；
                // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        int i = Math.toIntExact(value);
                        Log.d(TAG, "当前进度" + i );
                        progressBar1.setProgress(i);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

    }
}
