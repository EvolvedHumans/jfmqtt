package com.yangf.pub_libs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @name： 杨帆
 * @Time： 2020年 11月 25日 17时 49分
 * @Data： GSON框架的封装
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class GsonYang {

    /**
     * 判断该字符串是否是json类型
     *
     * @param content 随机字符串
     * @return true is jsonn
     */
    public static boolean IsJson(String content) {
        if (!content.startsWith("{") || !content.endsWith("}")) {
            return false;
        }
        try {
            new JSONObject(content);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    将JSON字符串转化成对象
     */
    public static <T> T JsonObject(String json, Class<T> tClass) {

        return new Gson().fromJson(json, tClass);
    }

    /*
    将对象或List集合转化为JSON字符串
     */
    public static <T> String JsonString(T object) {
        return new Gson().toJson(object);
    }

    /*
    将JSON字符串转化为List对象(String类型)
     */
    public static <T> T JsonList(String json, TypeToken<T> token) {
        token = new TypeToken<T>() {
        };
        return new Gson().fromJson(json, token.getType());
    }

    /**
     *
     * @param object 对象
     * @param <T> 泛型类
     * @return  判断这个类是否Serializable序列化过
     */
    public static <T> boolean isSerializable(T object) {
        return object instanceof Serializable;
    }
}
