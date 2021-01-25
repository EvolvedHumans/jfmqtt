package com.yangf.pub_libs;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 14日 14时 02分
 * @Data： HTTP各种类型请求封装，用的是OKHTTP框架
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class HttpUtil {

    private static HttpUtil httpUtil;

    private OkHttpClient client;

    private Integer connectTimeout; //连接超时时间

    private Integer readTimeout; //读取超时时间

    //TimeUnit.SECONDS 以秒为单位
    //TimeUnit.MILLISECONDS 以毫秒为单位

    /**
     * 毫秒为单位
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     */
    public HttpUtil(Integer connectTimeout,Integer readTimeout){
        if(client == null){
            client = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                    .readTimeout(readTimeout,TimeUnit.MILLISECONDS)
                    .build();
        }
    }

    /**
     * 初始化方法，确保只存在一个类实例，可配置连接超时和读取超时，单例模式
     * @Integer connectTimeout 连接超时
     * @Integer readTimeout 读取超时
     */
    public static synchronized HttpUtil getHttpUtil(Integer connectTimeout,Integer readTimeout){
        if (httpUtil == null){
            httpUtil = new HttpUtil(connectTimeout,readTimeout);
        }
        return httpUtil;
    }

    /**
     * POST请求内部类
     * @param url 网址
     * @param header 请求头
     * @param body 请求体
     * @return 完整请求参数Request
     */
    public synchronized Request formPost(String url, HashMap<String,String> header, HashMap<String,String> body){

        //表单体
        FormBody.Builder formbody = new FormBody.Builder();

        //表单参数
        if(body!=null){
            for (Map.Entry<String,String> arg:body.entrySet()){
                formbody.add(arg.getKey(),arg.getValue());
            }
        }

        //构造请求体
        RequestBody requestBody = formbody.build();

        //完整请求
        Request.Builder builder = new Request.Builder();

        builder.url(url); //创建url

        if(header!=null){
            for (Map.Entry<String,String> arg:header.entrySet()){
                builder.addHeader(arg.getKey(),arg.getValue());
            }
        }//请求头

        builder.post(requestBody); //请求体

        Request request = builder.build(); //创建完整请求参数

        return request;

    }


}
