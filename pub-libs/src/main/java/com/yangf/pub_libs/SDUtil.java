package com.yangf.pub_libs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 12日 19时 47分
 * @Data： SD存储卡
 * @TechnicalPoints： 相关方法存储集合
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class SDUtil {
    /**
     * 将信息存储到SD卡上,无法实现异步
     * 不同操作系统可能会出现乱码情况，客户端和服务端统一格式UTF-8
     * @File 文件路径
     * @Byte[] 字节流
     */
    public synchronized static boolean write(File file, byte[] bytes){
        //先判断是否有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //确保file不为null
            if(file!=null){
                try {
                    //写入文件流
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes);
                    //全部写完
                    outputStream.flush();
                    //关闭写入流
                    outputStream.close();
                    return true;
                }catch (Exception e){
                    //Objects.requireNonNull 判断一个对象为null
                    Log.e("SD卡->write错误原因", Objects.requireNonNull(e.getMessage()));
                }

            }
        }
        return false;
    }

    /**
     * 读取存储在SD卡上的信息，无法实现异步
     * 可能出现乱码情况，需要保持读、存数据格式的一致
     * @File 文件路径
     */
    public synchronized static byte[] read(File file){
        //先判断是否有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //确保file不为null
            if(file!=null){
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[inputStream.available()];
                    //读取文件流
                    inputStream.read(bytes);
                    //关闭读取流
                    inputStream.close();
                    return bytes;
                }catch (Exception e){
                    //读取失败
                    //Objects.requireNonNull 判断一个对象为null
                    Log.e("SD卡->read错误原因", Objects.requireNonNull(e.getMessage()));
                }
            }
        }
        return null;
    }


}
