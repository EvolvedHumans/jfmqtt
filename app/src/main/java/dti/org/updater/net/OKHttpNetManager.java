package dti.org.updater.net;

import android.annotation.SuppressLint;
import android.util.Log;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;
import com.yangf.pub_libs.util.UrlsplicingUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dti.org.pseudo.LoginPseudo;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private final String TAG = "updater.net.OKHttpNetManager";

    //okhttp请求实例
    private static OkHttpClient okHttpClient;

    //RXJAVA实现请求IO，将请求结果回调在主线程当中
    private volatile CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //在代码块中创建OKHTTPCLIENT对象
    static {
        //OkHttpClient构建对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //方法实现者进行设置，连接超时规定，供使用者调用
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(5, TimeUnit.SECONDS);
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
    public void getExecute(String url, final INetCallBack callBack) {
        //事件发射器
        Observable<String> observable = Observable.create(emitter -> {
            //创建Request构建对象
            Request.Builder builder = new Request.Builder();
            builder.url(url); //请求地址
            builder.get(); //请求类型get()
            builder.addHeader("Content-Type", "text/html;charset=UTF-8"); //以text文本形式，utf格式
            //获取request请求实例
            Request request = builder.build(); //请求对象构建完毕
            //通过okhttp的call对象获取请求返回值
            Call call = okHttpClient.newCall(request);
            //同步，需要在子线程中
            Response response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                emitter.onNext(Objects.requireNonNull(response.body()).string());
            } else {
                throw new Exception("response.isSuccessful()、response.code() == 200异常");
            }
            emitter.onComplete();
        });

        //调度到主线程
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String response) {
                callBack.success(response);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                callBack.failed(e);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onComplete() {
                Log.d(TAG, "getExecute请求完毕");
                callBack.onComplete();
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        compositeDisposable.add(disposableObserver);
    }

    @Override
    public void postFormExecute(String url, HashMap<String, String> header, HashMap<String, String> body, INetCallBack callBack) {

        //todo 此处作了修改
        //callBack.success(GsonYang.JsonString(LoginPseudo.getJsonLogin()));

        //事件发射器
        Observable<String> observable = Observable.create(emitter -> {

            //表单体,表单参数
            FormBody.Builder formbody = new FormBody.Builder()
                    .add("Content-Type", "application/x-www-form-urlencoded");

            if (body != null) {
                for (Map.Entry<String, String> arg : body.entrySet())
                    formbody.add(arg.getKey(), arg.getValue());
            }

            //构造请求体,请求体参数
            RequestBody requestBody = formbody.build();
            Request.Builder builder = new Request.Builder();
            builder.url(url).post(requestBody);

            //获取请求头
            if (header != null) {
                for (Map.Entry<String, String> arg : header.entrySet()) {
                    builder.addHeader(arg.getKey(), arg.getValue());
                }
            }

            Request request = builder.build(); //创建完整请求参数

            //通过okhttp的Call对象获取请求返回值
            Call call = okHttpClient.newCall(request);

            //同步操作
            Response response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                String content = Objects.requireNonNull(response.body()).string();
                emitter.onNext(content);
            } else {
                throw new Exception("response.isSuccessful()、response.code() == 200异常");
            }
            emitter.onComplete();
        });

        //调度到主线程
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {

            @Override
            public void onNext(@NonNull String content) {
                Log4j.d("response", "成功");
                callBack.success(content);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log4j.d("response", "错误");
                callBack.failed(e);
            }

            @Override
            public void onComplete() {
                Log4j.d(TAG, "postFormExecute请求完成");
                callBack.onComplete();
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    /**
     * okhttp Json数据提交
     *
     * @param url  地址
     * @param json json格式字符串
     */
    @Override
    public void postJsonExecute(String url, String json, INetCallBack callBack) {
        //事件发射器
        Observable<String> observable = Observable.create(emitter -> {
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);

            //同步操作
            Response response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                String content = Objects.requireNonNull(response.body()).string();
                emitter.onNext(content); //获取JSON数据返回
            } else {
                throw new Exception("response.isSuccessful()、response.code() == 200异常");
            }
            emitter.onComplete();
        });

        //调度到主线程
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log4j.d(TAG, "成功");
                callBack.success(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log4j.e(TAG, e.toString());
                callBack.failed(e);
            }

            @Override
            public void onComplete() {
                Log4j.d(TAG, "postJsonExecute");
                callBack.onComplete(); //请求完毕
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);

    }

    @Override
    public void download(String url, File targetFile, INetDownloadCallBack callBack) {


    }

    /*
   销毁页面时，取消订阅
    */
    @Override
    protected void finalize() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
