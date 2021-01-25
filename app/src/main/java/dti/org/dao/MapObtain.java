package dti.org.dao;

import java.io.Serializable;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 17时 57分
 * @Data： 地图序列化传值到下个界面
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class MapObtain implements Serializable {
    /*
    经度
     */
    private String longitude;
    /*
    纬度
     */
    private String latitude;
    /*
    逆编码地址
     */
    private String address;
    /*
    产品Type（智能井盖举例）
     */
    private Integer baseType;
    /*
    产品下NB模块（智能井盖举例）
     */
    private Integer type;
}
