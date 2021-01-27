package com.yangf.pub_libs.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 12时 30分
 * @Data： GET带参请求url拼接
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class UrlsplicingUtil {

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url url
     * @param params 所需传参
     * @return 完整url
     */
    public static String attachHttpGetParams(String url, HashMap<String, String> params) {
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next()).append("=").append(value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
        }
        return url + stringBuffer.toString();
    }

}