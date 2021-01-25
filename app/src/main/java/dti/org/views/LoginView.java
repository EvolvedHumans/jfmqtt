package dti.org.views;

import dti.org.base.BaseView;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 20时 39分
 * @Data： 登录view接口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface LoginView extends BaseView {
    /**
     *
     * @return 获取用户名
     */
    String getUserName();

    /**
     *
     * @return 获取密码
     */
    String getPassword();

    /**
     *
     * @param userTips 设置用户区域提示内容
     */
    void setUserTips(String userTips);

    /**
     *
     * @param passwordTips 设置密码区域提示内容
     */
    void setPasswordTips(String passwordTips);

    /**
     * 界面跳转
     */
    void jump();
}
