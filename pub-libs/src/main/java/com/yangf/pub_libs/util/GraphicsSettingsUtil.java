package com.yangf.pub_libs.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;

import com.yangf.pub_libs.log.Log4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 15日 12时 40分
 * @Data： 修改图片尺寸
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class GraphicsSettingsUtil {

    //okhttp请求实例
    private static OkHttpClient okHttpClient;

    //在代码块中创建OKHTTPCLIENT对象
    static {
        //OkHttpClient构建对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //方法实现者进行设置，连接超时规定，供使用者调用
        builder.connectTimeout(5*10, TimeUnit.SECONDS);
        builder.readTimeout(5*10, TimeUnit.SECONDS);
        //获取OkHttpClient实例
        okHttpClient = builder.build();
    }

    /**
     * 图片缩放
     *
     * @param bitmap 对象
     * @param w      要缩放的宽度
     * @param h      要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    /**
     * 通过传入Uri来更改图片的大小
     */
    public static Bitmap getScaledBitmap(Uri uri, int destWidth, int destHeight) {
        // read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri.getPath(), options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        // figure out how much to scale down by
        int inSampleSize = 1;
        if (srcWidth > destHeight || srcHeight > destHeight) {
            inSampleSize = Math.round(Math.max((srcWidth / destWidth), (srcHeight / destHeight)));
        }

        // create a bitmap
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(uri.getPath(), options);
    }

    /**
     * 传入String地址来获取指定大小的Bitmap文件,异步。
     * 目前只能适用于get请求
     */
    public static Bitmap getUrlBitmap(String urlBitmap) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlBitmap);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 同步获取Bitmap，OKhttp框架实现，耗时操作需在IO线程中进行
     *
     * 异常难点:使用BitmapFactory的decodeStream()方法解码图片失败问题
     * 在这里会长时间阻塞。
     * 从网上查了查,有的说是网速不太好的情况下,会获取不了图片,有的说是低版本的API上会出现解码问题
     * 因此此处调用了BitmapFactory.decodeByteArray()方法进行获取Bitmap
     *
     * 已经确定从网络获取的数据流没有出现问题，而是在图片解码时出现错误。上网查找了不少资料，也没有得出确切的原因，不过有几条意见值得关注。
     *
     * 一种说法是在android 较低版本的api中会有不少内部的错误，我的代码运行时选择
     *
     * 2.1API Level 7和2.2API Level 8都会出现这个问题，而选择2.3 API Level 9后能够正常解码图片。
     *
     * 使用decodeByteArray（）方法在低版本的API上也能够正常解码，解决了这个问题。
     *
     * 引用了getBytes获取解码后的字节数组
     *
     */
    public static Bitmap getOkhttpBitmap(String url) {
        Bitmap bitmap = null;
        //创建Request构建对象
        Request.Builder builder = new Request.Builder();
        builder.url(url); //请求地址
        builder.get(); //请求类型get
        //获取request请求实例
        Request request = builder.build();
        //同步获取
        Call call = okHttpClient.newCall(request);
        //同步，需要在子线程1中
        try {
            Response response = call.execute(); //同步，很快
            if(response.isSuccessful() && response.code()==200){
                //获取图片byte[]字节流大小
                byte[] data = getBytes(Objects.requireNonNull(response.body()).byteStream());
                bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 得到图片字节流 数组大小。
     *
     * @param is 输入流
     * @return byte[] 解码字节
     * @throws IOException 异常处理
     */
    public static byte[] getBytes(InputStream is) throws IOException {

        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        byte[] bytes =new byte[1024];
        int len;

        while ((len = is.read(bytes, 0, 1024)) !=-1) {
            baos.write(bytes, 0, len);
            baos.flush();
        }
        return baos.toByteArray();
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param //uri 图片Uri
     */

    private Bitmap getImageFromNet(String url) {
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET"); //设置请求方法
            // conn.setConnectTimeout(10000);设置连接服务器超时时间
            conn.setReadTimeout(5000); //设置读取数据超时时间
            conn.connect(); //开始连接
            int responseCode = conn.getResponseCode();
            //得到服务器的响应码
            if (responseCode == 200) { //访问成功
                InputStream is = conn.getInputStream(); //获得服务器返回的流数据
                return BitmapFactory.decodeStream(is);
            } else { //访问失败
                Log.d("lyf--", "访问失败===responseCode：" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect(); //断开连接
            }
        }
        Log4j.d("为","null");
        return null;
    }

    /**
     * 质量压缩方法
     *
     * @param bitmap 图片bitmap
     * @return 压缩后bitmap返回
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差 ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

}
