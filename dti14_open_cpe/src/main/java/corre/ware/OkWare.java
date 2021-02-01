package corre.ware;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 11时 19分
 * @Data： 串口通讯对外提供类
 * 对于使用者而言，只需要知道里面的方法，这相当于与一个接口屏蔽类
 * 使用者所需要的所有的操作，都可以在OkWare中进行。
 * 在这个类中，应该包含串口的开启监听、关闭监听两种方法的调用
 * <p>
 * 对外以提供类，只需要知道里面的方法，这相当于与一个接口屏蔽类
 * 使用者所需要的所有的操作，都可以在OkSerialPort中进行，
 * 底层封装，也可以在其他地方调用集合。
 * <p>
 * 设计模式：单例模式
 * <p>
 * 当前使用底层框架：由谷歌官方提供
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class OkWare {


    /**
     * 单例模式：外界获取OkWare接口
     * 饿汉式（线程安全，可用）
     *
     * 缺点： 类一加载的时候，就实例化，提前占用了系统资源。
     *
     * @return 获取OkWare实例
     */
//    private static OkWare okWare = new OkWare();
//    public static OkWare getInstance(){
//        return okWare;
//    }
    /**
     * 单例模式：外界获取OkWare接口
     * <p>
     * okWare = new OkWare()
     * 不是一个原子操作。（XXX）故须加 volatile 关键字修饰，该关键字在 jdk1.5 之后版本才有。
     * <p>
     * 优点：资源利用率高，第一次执行getInstance时单例对象才会被实例化，效率高。
     * 缺点：第一次加载时反应稍慢，也由于Java内存模型的原因偶尔会失败。
     * 在高并发环境下也有一定的缺陷，虽然发生的概率很小。
     * DCL模式是使用最多的单例实现方式，它能够在需要时才实例化单例对象。
     * 并且能够在绝大多数场景下保证单例对象的唯一性，
     * 除非你的代码在并发场景比较复杂或者低于jdk1.6版本下使用，否则这种方式一般能够满足需求。
     */
    private static volatile OkWare okWare;

    public static OkWare getInstance() {
        if (okWare == null) {
            synchronized (OkWare.class) {
                //未初始化，则初始化instance变量
                if (okWare == null) {
                    okWare = new OkWare();
                }
            }
        }
        return okWare;
    }

    /**
     * 实现串口通讯的开启、读取、写入、关闭
     * 这里引用的Google公司提供的串口通讯底层封装
     * 将核心方法接入在内，方便代码的更新换代，使用已经修改的GoogleOkWare方法
     * 外界获取改写的GoogleOkWare
     * <p>
     * 这样写可以随意获取GoogleOkWare改写后的实例
     * <p>
     * 端口: "/dev/ttyHSLx"
     *
     * @return 获取OkWareManager实例，通过GoogleOkWare进行改写
     */
    //    private OkWareManager okWareManager = new GoogleOkWare();

//    public OkWareManager getOkWareManager(){
//        return okWareManager;
//    }
    public synchronized OkWareManager setGoogleOkWare() {
        return new GoogleOkWare();
    }


}
