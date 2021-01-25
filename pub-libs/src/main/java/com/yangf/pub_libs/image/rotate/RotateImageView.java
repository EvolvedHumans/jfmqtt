package com.yangf.pub_libs.image.rotate;

import android.content.Context;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yangf.pub_libs.Log4j;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 11日 10时 26分
 * @Data： 滚动加载条,
 * @TechnicalPoints： 设置旋转角度与旋转该角度所需的时间，并控制开始和停止
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class RotateImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final static String TAG = "com.yangf.pub_libs.image.rotate.RototeImageView";

    /*
    定时器管理
     */
    private Disposable disposable;

    /*
    设置旋转属性
     */
    public RotateImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    Integer code 0~7次
     */
    private synchronized void startRatote(Integer code) {
        //开始旋转的角度为零，结束旋转的角度为45，imageView控件以自身中心为圆心旋转45度
        int fromDegrees = code * 45;
        int toDegrees = fromDegrees + 45;
        RotateAnimation rotateAnimation;
        rotateAnimation = new RotateAnimation
                (0, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //延迟0秒，瞬间到达45度这个位置
        rotateAnimation.setDuration(0);

        //动画结束后，保持动画最后时的位置
        rotateAnimation.setFillAfter(true);

        startAnimation(rotateAnimation); //开启旋转
    }

    /*
    开启持续转动的动画效果
     */
    public synchronized void show() {
        //被观察者,定时器启动事件,interval(1000, TimeUnit.MILLISECONDS)方法每1000毫秒发射一次数据
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()) //被观察者线程调度器，转向耗时的UI线程
                .observeOn(AndroidSchedulers.mainThread()) //观察者线程调度器，转向UI主线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                        int code = aLong.intValue() + 7;
                        startRatote(code % 8);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /*
    销毁对象时，取消订阅
     */
    @Override
    protected void finalize() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
