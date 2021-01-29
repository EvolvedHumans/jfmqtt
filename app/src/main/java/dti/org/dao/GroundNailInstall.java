package dti.org.dao;

import lombok.Data;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.dao
 * 描述：      TODO 道钉安装接口方法类
 * <p>
 * * 地钉安装按钮
 * * 1.产品类型 lineType  String类型
 * * 2.道钉二维码标识 stakeUid String类型
 * * 3.经度 lon  String类型
 * * 4.纬度 lat  String类型
 * * 5.设备名称 name   String类型
 * * 6.班组id banzuId String类型
 * * 6.公司iD departmentId  String类型
 * * 7.安装场景  sceneType  int类型
 * * 8.安装图片 pictures    String类型
 * * 9.如有被使用的情况下是否继续安装 install  int类型
 * <p>
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 28日 10时 21分
 */
@Data
public class GroundNailInstall {

    private String url; //请求地址

    private String lineType; //地图端传递

    private String stakeUid; //扫码获取

    private String lon; //地图端传递

    private String lat; //地图端传递

    private String name; //地图端传递

    private String banzuId; //全局缓存获取

    private String departmentId; //全局缓存获取

    private int sceneType; //场景type

    private String scene; //场景名

    private String pictures; //无

    private int install; //默认
}
