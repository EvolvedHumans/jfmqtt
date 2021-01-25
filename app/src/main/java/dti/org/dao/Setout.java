package dti.org.dao;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 11日 09时 50分
 * @Data： 产品类型放回数据
 * @TechnicalPoints：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
@AllArgsConstructor
public class Setout implements Serializable {
    //产品类型id
    private String id;

    //产品类型学名称
    private String name;

    //产品类型图片地址
    private String picture;

    //产品类型
    private Integer type;
}
