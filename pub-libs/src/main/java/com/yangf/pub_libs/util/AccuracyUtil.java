package com.yangf.pub_libs.util;

import java.text.DecimalFormat;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 01日 13时 46分
 * @Data： 数字类型精度取值
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class AccuracyUtil {
    /**
     * Double类型取小数点后四位
     */
    public static String accuracyDouble(double data){
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        return decimalFormat.format(data);
    }

}
