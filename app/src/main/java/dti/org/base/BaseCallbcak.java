package dti.org.base;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 16日 18时 32分
 * @Data： model数据情趣父类接口，引入泛型实现通用
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface BaseCallbcak<T> {
    /**
     * 数据请求成功
     * @param data 请求到的数据
     */
    void onSuccess(T data);

    /**
     * 使用网络API接口请求方式时，虽然已经成功，但出现@Code等原因无法正常返回数据时
     * @param msg 失败字符串返回
     */
    void onFailure(String msg);

    /**
     *
     * 请求数据失败，指定请求网络API接口请求方式时，出现无法联网。
     * 缺少权限，内存泄露等原因导致的错误
     *
     * @param throwable 错误信息
     */
    void onError(Throwable throwable);

    /**
     * 当请求数据结束时，无论请求结果是成功，失败或是抛出异常都会执行此方法给用户做出出力。
     * 通常用作网络请求结束时，用户隐藏加载的等待控件
     */
    void onComplete();
}
