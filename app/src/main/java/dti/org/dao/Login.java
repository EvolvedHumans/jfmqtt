package dti.org.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 03日 20时 45分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class Login {
    //用户唯一标识
    private String id;

    //所属公司ID
    private String departmentId;

    //所属公司名称
    private String departmentName;

    //登录用户账号
    private String username;

    //用户名称
    private String name;

    //用户手机号
    private String phone;

    //用户邮箱
    private String email;

}
