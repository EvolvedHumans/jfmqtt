package dti.org.dao;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 11日 09时 41分
 * @Data： 设备类型接口 GET类型请求 ,请求地址 /loginOut
 * @TechnicalPoints：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */

@lombok.Data
@AllArgsConstructor
public class SetoutObtain {

    //请求结果：1.成功 否则失败
    private Integer rt;

    //msg字段
    private String msg;

    //返回消息
    private String comments;

    //产品信息集合
    List<Setout> data;

}
