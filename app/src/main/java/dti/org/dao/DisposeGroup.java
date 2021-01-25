package dti.org.dao;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 04日 16时 23分
 * @Data： 设备信息返回
 * 智能井盖 对应 type = 1
 * 智能井盖下对应按钮组的Type
 * type = 1，选择班组
 * type = 2，基座类型
 * type = 3，选择配置
 * type = 4，安装场景
 * type = 5，外井盖类型
 * type = 6，RFID模块
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class DisposeGroup implements Serializable {

    /*
    Dispose配置名称(例如:选择班组、安装场景)
     */
    private String name;

    /*
    type字段
     */
    private Integer type;

    /*
    标题(弹出框的标题)
     */
    private String topic;

    /*
    Dispose下的数据组
     */
    private List<Dispose> details;

    /*
    选中状态(默认为false)
     */
    private boolean isSelected = false;

}
