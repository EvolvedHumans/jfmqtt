package dti.org.dao;

import lombok.Data;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.dao
 * 描述：      TODO 安装完成后接受响应回调接口
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 28日 12时 16分
 */
@Data
public class InstallObtain {
    private Integer rt;
    private String msg;
    private String comments;
    private Object data;
}
