package com.yangf.pub_libs.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 15日 09时 32分
 * @Data： 控件单位进制转换类
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class DensityUtil {

    private static DisplayMetrics sDM = Resources.getSystem().getDisplayMetrics();

    /*
    获取屏幕的宽度，单位(px)
     */
    public static int getScreenWidth() {
        return sDM.widthPixels;
    }

    /*
    获取屏幕的高度,单位(px)
     */
    public static int getScreenHeight() {
        return sDM.heightPixels;
    }

    /*
    获取屏幕的密度
     */
    public static float getDensity() {
        return sDM.density;
    }

    /*
    和获取屏幕密度DPI
     */
    public static float getDensityDpi() {
        return sDM.densityDpi;
    }

    /*
    根据手机的分辨率从dp(单位)转成为px(像素)
     */
    public static int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, sDM);
    }

    /*
    根据手机分辨率从sp(单位)转成为px(像素)
     */
    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, sDM);
    }

    /*
    根据手机分辨率从px(单位)转成为dp(单位)
     */
    public static int pxToDp(float px) {
        return Math.round(px / getDensity());
    }

    /*
    根据手机分辨率从px(单位)转成为sp(单位)
     */
    public static float pxToSp(float px) {
        return (px / sDM.scaledDensity);
    }
}
