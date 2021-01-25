package dti.org.base;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 16日 18时 56分
 * @Data： Presenter中可共用的代码就是对View引用的方法了。值得注意的是，上面已经定义好了BaseView，
 * 所以我们希望Presenter中持有的View都是BaseView的子类，这里同样需要泛型来约束：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class BasePresenter<V extends BaseView>{
    /**
     * 绑定的View
     */
    private V view;

    /**
     * 获取连接的view
     */
    public V getView(){
        return view;
    }

    /**
     * 绑定View，一般在初始化中调用该方法
     */
    public void attachView(V view) {
        this.view = view;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.view = null;
    }
    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return view != null;
    }
}
