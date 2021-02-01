package com.example.imooc_appupdater.updater.net;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Looper;
import android.util.Log;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 20日 13时 14分
 * @Data： OKHttpNetManager是继承INetManager接口，变为OKHTTP框架的具体实现类。
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class OKHttpNetManager implements INetManager {
    /**
     * 静态代码块，在虚拟机加载类的时候就会加载执行，而且只执行一次;
     * 非静态代码块，在创建对象的时候（即new一个对象的时候）执行，每次创建对象都会执行一次
     * <p>
     * 一个程序可以有多个静态非静态代码区域
     * <p>
     * 这里使用静态代码块，让使用的引用这个类时有且只加载一次OKHTTPClient方法
     *
     * @error 曾今由于多次创建OkHttpClient导致程序终止，抛异常。
     * 改用此方法就完美的避免了，这种问题的出现。
     * <p>
     * 当程序进行更新换代时，可能不在以HTTP，而是更新为HTTPS的方法时，HTTP就不适用了。
     * <p>
     * HTTPS:自签名的，如果使用http协议进行，则OKHttp会出现握手时的错误
     * <p>
     * 可以通过:builder.sslSocketFactory()设置,这里先埋一个坑，先暂时不去考虑了。
     */

    //okhttp请求实例
    private static OkHttpClient okHttpClient;
    //创建一个UI线程，并将这个UI线程放在主UI线程中去执行
    private static android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());

    //在代码块中创建OKHTTPCLIENT对象
    static {
        //OkHttpClient构建对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //方法实现者进行设置，连接超时规定，供使用者调用
        builder.connectTimeout(15, TimeUnit.SECONDS);
        //获取OkHttpClient实例
        okHttpClient = builder.build();
    }

    /**
     * * okhttp框架:
     * *
     * 1.Requests 请求
     * 每个HTTP请求包含一个URL，一个请求方法（像GET，POST），和一个头部列表，
     * 请求也包含一个body：特殊内容类型的数据流，比如文件，json数据...
     * *
     * 2.Responses 响应
     * 响应使用代码（如200获得成功或404找不到）来应答请求，标题及其自己的可选正文。
     * *
     * *
     * 3.Calls 呼叫
     * (1).通过重写，重定向，后续跟踪和重试，您的简单请求可能会产生许多请求和响应，
     * OkHttp使用Call来建立满足您的请求的任务，但是需要许多中间请求和响应。
     * 有两种执行呼叫的方式
     * <p>
     * (2).Synchronous 同步：你的线程被阻塞，直到响应可读。
     * Asynchronous 异步：您在任何线程上排队请求，并在响应可读时在另一个线程获取回调
     * 呼叫可以在任何线程被取消，如果呼叫尚未完成，这将呼叫失败！
     * 编写请求正文或读取响应主体的代码在其呼叫被取消时将抛出IOException。
     * <p>
     * Response 通过异步获取后，此时还不是非UI线程，为了能让使用者拿到这个参数后，进行UI操作。
     * 我们可以将callback放在ui线程中，当使用调用callback来获取返回结果后，直接能够进行UI操作
     *
     * @param url      地址
     * @param callBack 请求成功与否的回调接口
     */
    @Override
    public void get(String url, final INetCallBack callBack) {
        Log.e("get", "请求类型");

        //创建Request构建对象
        Request.Builder builder = new Request.Builder();
        builder.url(url); //请求地址
        builder.get(); //请求类型get()

        builder.addHeader("Content-Type", "text/html;charset=UTF-8"); //以text文本形式，utf格式
//        builder.addHeader() //添加头部请求
//        builder.method() // 设置请求的方法和请求体

        //获取request请求实例
        Request request = builder.build(); //请求对象构建完毕

        //通过okhttp的call对象获取请求返回值
        Call call = okHttpClient.newCall(request);
        Log.e("okhttp", "call对象获取请求返回值");


        //同步请求，获取Response
//        Response response = call.execute();

        //2.异步操作，获取Response
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {
                //Log.e("获取了响应","onResponse");
                if (response.isSuccessful() && response.code() == 200) {
                    final String str = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(str);
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }
        });

    }

    @Override
    public void download(String url, File targetFile, INetDownloadCallBack callBack) {


    }
}
