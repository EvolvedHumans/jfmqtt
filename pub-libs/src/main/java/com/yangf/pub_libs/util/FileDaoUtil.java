package com.yangf.pub_libs.util;

import android.content.Context;
import android.os.Environment;

import com.yangf.pub_libs.Log4j;

import java.io.File;
import java.util.Objects;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 08日 14时 58分
 * @Data： Android SD卡、内部，各类型持久化存储辅助工具类
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class FileDaoUtil {

    //EXCEL文件后缀名
    public final static String EXCEL_SUFFIX = ".xls";

    /**
     * EXCEL表
     *
     * @param name 文件名
     * @return 完整EXCEL表文件名获取
     */
    public static String getExcelFileName(String name){
        return name+EXCEL_SUFFIX;
    }

    /**
     * 判断该表是否为.xls格式的Excel表
     *
     * @param name 完整表名
     * @return true or false
     */
    public static boolean equipExcel(String name){
        return name.endsWith(EXCEL_SUFFIX);
    }

    /**
     * 设置导入文件目录位置Cache，SD卡或内部存储目录
     *
     * @param context 上下文
     * @param name 文件名
     * @return 返回的File类，Cache目录+文件名
     */
    public static File getCacheFile(Context context, String name) {
        String cachePath;
        //如果要存在SD卡中，需要先判断有无SD卡
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //获取sd卡的Cache缓存路径
            cachePath = Objects.requireNonNull(context.getExternalCacheDir()).getPath();
        }else {
            //获取手机内部的Cache缓存路径
            cachePath = context.getCacheDir().getPath();
        }
        Log4j.d("存储路径",cachePath+File.separator+name);
        return new File(cachePath + File.separator + name);
    }

}
