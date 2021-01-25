package dti.org.dao;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 03日 11时 57分
 * @Data： 登录接口 用户名密码登录: /login
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */

@lombok.Data
public class LoginObtain {
    //请求结果：1.成功 否则失败
    private Integer rt;

    //msg字段
    private String msg;

    //返回消息
    private String comments;

    //data 字段返回数据
    private LoginGroup data;
}
