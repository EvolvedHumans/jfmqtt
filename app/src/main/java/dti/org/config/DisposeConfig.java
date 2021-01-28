package dti.org.config;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.config
 * 描述：      TODO 设备信息选择 , 通过Share存储
 * 智能井盖 -> 1
 * 智能井盖下：设备选择信息
 * "type":1 "name":"选择班组"
 * "type":2 "name":"选择配置
 * "type":3 "name":"安装场景"
 * "type":4 "name":"外井盖类型"
 * "type":5 "name":"是否有RFID模块"
 * "type":6 "name":"基座类型"
 *
 * 地钉 -> 5
 * "type":1 "name":"选择班组"
 * "type":3 "name":"安装场景"
 *
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 18时 08分
 */
public final class DisposeConfig {

    public final static String WellTeam = "WellTeam";
    public final static String WellConfigure = "WellConfigure";
    public final static String WellScene = "WellScene";
    public final static String WellOutside = "WellOutside";
    public final static String WellRfid = "WellRfid";
    public final static String WellPedestal = "WellPedestal";

    public final static String GroundNailTeam = "GroundNailTeam";
    public final static String GroundNailScene = "GroundNailScene";
}
