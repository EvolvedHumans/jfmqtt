package com.yangf.pub_libs.image.url;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yangf.pub_libs.DensityUtil;
import com.yangf.pub_libs.DimensionImage;
import com.yangf.pub_libs.Log4j;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 05日 12时 26分
 * @Data： 自定义ImageView，加载网络图片，在使用该方法时需要添加网络请求权限，使用时需要添加URL完整地址
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class UrlImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final String TAG = "com.yangf.pub_libs.url.UrlImageView";

    private boolean isRefresh = false;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public UrlImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置网络图片
     *
     * @param url 网络链接
     * @param bitmapWidth 设置图片宽度
     * @param bitmapHeight 设置图片的高度
     */
    public void setImageURL(String url,int bitmapWidth,int bitmapHeight) {
        //事件发射器
        Observable<Bitmap> observable = Observable.create(emitter -> {
            Bitmap bitmap = DimensionImage.getOkhttpBitmap(url);
            //如果获取不为null，则将其剪拆
            if (bitmap != null) {
                bitmap = DimensionImage.zoomBitmap(bitmap, bitmapWidth, bitmapHeight);
                emitter.onNext(bitmap);
                emitter.onComplete();
            } else {
                throw new NullPointerException("Bitmap取空");
            }
        });

        //UI处理，放到UI线程中进行，并在此处设置刷新后的图片的高度和宽度
        DisposableObserver<Bitmap> disposableObserver = new DisposableObserver<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                if (bitmap != null){
                    setImageBitmap(bitmap);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onComplete() {
                isRefresh = true;
                Log.d(TAG, "刷新完成");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    /*
  销毁对象时，取消订阅
   */
    @Override
    protected void finalize() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
