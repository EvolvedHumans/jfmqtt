package dti.org.config;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 03日 11时 39分
 * @Data： 所用到的所有请求地址
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class UrlConfig {

    public static String URL = "http://192.168.1.197:8080/scaninstall_war_exploded";

    //登录
    public static String LoginUrl = URL + "/login";

    //登出
    public static String LoginOutUrl = URL + "/loginOut";

    //设备类型
    public static String Setout = URL + "/lineTypeConfig/list";

    //设备信息
    public static String SetoutConfigList = URL + "/lineTypeConfig/configList";

    //检测锁二维码(入参字段：lockeUid)
    public static String Lock = URL + "/nbset/checkLock";

    //检测03或32设备情况（入参字段：pickproofUid）
    public static String Sm32Or03 = URL + "/nbset/checkPickproofUid";

    //检测01或31设备情况（入参字段：monitoringUid）
    public static String Sm01Or31 = URL + "/nbset/checkNbsetUid";

    //检测RFID
    public static String RFID = URL + "/nbset/checkRfid";

}
