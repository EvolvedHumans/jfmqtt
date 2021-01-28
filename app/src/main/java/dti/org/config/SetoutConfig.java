package dti.org.config;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.config
 * 描述：      TODO 产品选择界面
 * 智能井盖 -> 1
 * 智能井盖下：设备选择信息
 * "type":1 "name":"选择班组"
 * "type":2 "name":"选择配置
 * "type":3 "name":"安装场景"
 * "type":4 "name":"外井盖类型"
 * "type":5 "name":"是否有RFID模块"
 * "type":6 "name":"基座类型"
 * 地钉 -> 5
 * "type":1 "name":"选择班组"
 * "type":3 "name":"安装场景"
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 27日 18时 05分
 */
public final class SetoutConfig {
    public final static int Well = 1;
    public final static int WellTeam = 1;
    public final static int WellConfigure = 2;
    public final static int WellScene = 3;
    public final static int WellOutside = 4;
    public final static int WellRfid = 5;
    public final static int WellPedestal = 6;

    public final static int GroundNail = 5;
    public final static int GroundNailTeam = 1;
    public final static int GroundNailScene = 3;
}
