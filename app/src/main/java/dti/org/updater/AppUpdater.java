package dti.org.updater;

import dti.org.updater.net.INetManager;
import dti.org.updater.net.OKHttpNetManager;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 20日 12时 24分
 * @Data： 网络请求对外类
 * 对于其他开发者，可以是不可见的，它们只需要知道里面的方法就行。
 * 使用者所有的操作都可以AppUpdater进行调用
 *
 * 设计模式：单例模式
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class AppUpdater {

    /**
     * @return 获取AppUpdater实例
     */
    private static volatile AppUpdater mAppUpdater;
    public static AppUpdater getInstance() {
        if (mAppUpdater == null) {
            synchronized (AppUpdater.class) {
                //未初始化，则初始化instance变量
                if (mAppUpdater == null) {
                    mAppUpdater = new AppUpdater();
                }
            }
        }
        return mAppUpdater;
    }

    /**
     * 实现网络请求下载功能，下载能力方法
     *
     * 这里的网络请求可能是OKhttp、volley、httpclient等等。
     * 因此需要通过接口来隔离实现。
     *
     * 接口隔离实现：我们的接口通过AppUpdater这个对外类来获取我们定义的接口，并提供给外界。
     *
     * 将核心接口封装在内层，不对外！只通过中间类AppUpdater对外提供。
     *
     * 保证接口安全的同时固定了后续开发的编写规范！
     *
     * 不管调用什么样的网络请求框架，都需要在实现的过程中调用INetManager来实现，
     * 这样就可以固定我们的代码格式了。
     *
     * 这里我们封装的OKHTTP调用INetManager接口实现
     *
     * 具体请看OKHttpNetManager类
     *
     * 假如OKHTTP不行了，改用volley了
     *
     * 那么就创建一个volleyNetManager类，在里面继承INetManager。
     *
     * 这就叫做接口隔离具体实现了！！！！！
     * 这样我们基本框架就搭建起来了！！！！
     *
     */

    public synchronized INetManager getOKHttpNetManager(){
        return new OKHttpNetManager();
    }

}
