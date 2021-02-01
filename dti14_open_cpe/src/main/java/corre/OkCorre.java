package corre;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 22日 11时 06分
 * @Data： 通讯对外提供类
 * 对于使用者而言，只需要知道里面的方法，这相当于与一个接口屏蔽类
 * 使用者所需要的所有的操作，都可以在OkSerialPort中进行
 *
 * 设计模式：单例模式。
 * 防止使用者多次调用而产生的错误
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class OkCorre {

    /*
    自身对象实例化
     */
    private static OkCorre okCorre = new OkCorre();

    /**
     * 单例模式：外界获取OkSerialPort接口
     * @return 获取OkSerialPort实例
     */
    public static OkCorre getInstance(){
        return okCorre;
    }

}































