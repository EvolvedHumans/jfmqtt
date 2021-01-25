package dti.org.dao;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 03日 21时 11分
 * @Data： 登出响应参数
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class LoginoutObtain {
    private Integer rt;
    private String msg;
    private String comments;
    private Object data;
}
