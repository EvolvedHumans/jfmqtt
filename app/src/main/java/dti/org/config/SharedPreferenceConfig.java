package dti.org.config;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 13日 10时 16分
 * @Data： 文件存储路径data
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class SharedPreferenceConfig {
    //APP登录时，服务器返回数据的存储（JSON格式）,数据类型Login
    public final static String APP_LOGIN = "APP_LOGIN";

    //产品类型TYPE
    public final static String Setout_TYPE = "Setout_TYPE";

    //安装后返回数据校验(DisposeObtain)
    public final static String Setout_OnClick = "Setout_OnClick";

    //智能井盖配置Type返回
    public final static String NB_Type = "NB_Type";

    //（Setout_OnClick）无数据返回时的默认数据
    public final static Integer TYPE_NO = 0;

    //（Setout_TYPE）无数据返回时的默认数据
    public final static String NO = "";
}
