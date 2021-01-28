package dti.org.base;

import android.content.Context;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 16日 18时 40分
 * @Data： View父类，用于定义Activity类中的UI逻辑，因为有很多方法几乎在每个Activity中偶会用到。
 * 例如：显示和隐藏正在加载进度条，显示Toast提示等等，索性将其方法变成通用的
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface BaseView<T> {
    /**
     * 显示正在加载View
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg 提示语
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showErr(String msg);

    /**
     * @param key   键
     * @param value 值
     */
    void importStringCache(String key, String value);

    /**
     * @return 缓存数据（缓存数据类型Integer）
     */
    void importIntegerCache(String key, Integer value);

    /**
     * @param key   键
     * @param value 值
     * @return 缓存数据(查看缓存中Login方法中的数据)
     */
    void importBooleanCache(String key, boolean value);

    /**
     * @param key   键
     * @param value 值
     * @return 缓存数据(查看缓存中Login方法中的数据)
     */
    String exportStringCache(String key, String value);

    /**
     * @param key   键
     * @param value 值
     * @return 缓存数据(查看缓存中Login方法中的数据)
     */
    int exportIntegerCache(String key, int value);

    /**
     * @param key   键
     * @param value 值
     * @return 缓存数据(查看缓存中Login方法中的数据)
     */
    boolean exportBooleanCache(String key, boolean value);

    /**
     * 清理缓存
     */
    void clearCache();

    /**
     * 获取上下文
     *
     * @return 上下文回调
     */
    Context getContext();


}
