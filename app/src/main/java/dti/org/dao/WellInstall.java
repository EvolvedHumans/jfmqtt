package dti.org.dao;

import lombok.Data;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.dao
 * 描述：      TODO 智能井盖安装信息导入
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 29日 14时 01分
 */
@Data
public class WellInstall {
    private String url; //请求地址

    private String name; //设备名称

    private int lineType; //产品类型

    private String lon; //经度

    private String lat; //纬度

    private String departmentId; //公司id

    private String uid; //工井id

    private String pictures; //安装图片

    private int brfid;  //有无rfid

    private int configType; //配置类型
    private String config; //配置名



    private String banzuId; //班组id

    private String sceneTypeId; //安装场景ID
    private String scene; //场景名

    private String outerWellTypeId; //外井盖类型ID
    private String outerWell; //井盖名

    private String pedestalTypeId; //基座类型ID
    private String pedestal; //基座名

    /**
     * 只有锁、rfid  configType->1
     */
    private String lockUid; //锁二维码
    private String rfid; //rfid二维码

    /**
     * 锁、32/rfid   configType->2
     */
    //锁同上
    private String pickproofuid; //32二维码

    /**
     * 锁、03   configType->3
     */
    //锁同上
    //03与32同样

    /**
     * 锁、01   configType->4
     */
    //锁同上
    //01更新字段
    private String monitoringUid;

    /**
     * 锁、31   configType->5
     */
    //锁同上
    //31与32同上

    /**
     * 32   configType->6
     */
    //无锁
    //32

    /**
     * 01锁 + 01 + 03锁 + 03   configType->7
     */
    //01锁字段同上
    //03锁字段
    private String lockUid03;
    //01同上
    //03同上


}

















