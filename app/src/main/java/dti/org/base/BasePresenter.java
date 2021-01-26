package dti.org.base;


import com.yangf.pub_libs.Log4j;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 16日 18时 56分
 * @Data： Presenter中可共用的代码就是对View引用的方法了。值得注意的是，上面已经定义好了BaseView，
 * 所以我们希望Presenter中持有的View都是BaseView的子类，这里同样需要泛型来约束：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class BasePresenter<V extends BaseView> {

    // 目前来讲有2个公用方法 , 传递的时候 会有不同的View , 怎么办？ 采用泛型

//    private WeakReference<V> mViewReference;

    // View一般都是Activity，可能会涉及到内存泄露的问题，但是已经解绑了就不会内存泄露，
    // 这里最好还是用一下软引用

    /**
     * 绑定的View
     */
    private V mView;

    /**
     * 获取连接的view
     */
    public V getView() {
        return mView;
    }

    /**
     * 绑定View，一般在初始化中调用该方法
     */
    public void attachView(final V view) {
        //动态代理
        mView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //在View层显示数据之前用户可能退出了View层的页面，会在Activity的onDestroy()方法中会把mView置为null
                //由于View层都是接口，这里采用了动态代理，如果在View层显示数据之前用户可能退出了View层的页面，返回null的话，onSuccess()方法不会执行
                if (mView == null) {
                    return null;
                }
                //每次调用View层接口的方法，都会执行这里
                return method.invoke(view, args);
            }
        });
//        this.view = view;
//
//        this.mViewReference = new WeakReference<V>(view);
//
//        // 用代理对象 动态代理
//        view = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                // 动态代理每次都会执行这个方法，调用的是被代理的对象（就是mView）
//                if (mViewReference == null || mViewReference.get() == null) {
//                    Log4j.d("1","1");
//                    return null ;
//                }else{
//                    Log4j.d("1","1");
//
//                    return method.invoke(mViewReference.get(), args);
//                }
//            }
//        });
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.mView = null;
//        this.mViewReference.clear();
//        this.mViewReference = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     * 出现View 为null的情况，当页面销毁并重启时
     * todo 网上建立用网络代理方式去实现，这里暂时先这样
     */
    public boolean isViewAttached() {

        return mView!=null;
    }

}
