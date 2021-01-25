package dti.org.dao;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 04日 16时 22分
 * @Data： 沟盖板下设备信息
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class DisposeObtain implements Serializable {

    private Integer rt;

    private String comments;

    private List<DisposeGroup> data;
}
