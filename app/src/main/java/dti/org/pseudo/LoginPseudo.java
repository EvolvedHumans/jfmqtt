package dti.org.pseudo;

import dti.org.dao.LoginGroup;
import dti.org.dao.LoginObtain;
import dti.org.dao.Login;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 14日 19时 13分
 * @Data： 登录界面假数据生成
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class LoginPseudo {
    public static LoginObtain getJsonLogin(){
        LoginObtain loginObtain = new LoginObtain();
        loginObtain.setRt(1);
        loginObtain.setMsg("返回成功");
        loginObtain.setComments("返回");
        LoginGroup loginGroup = new LoginGroup();
        loginGroup.setToken("登录令牌");
        Login login = new Login();
        login.setDepartmentId("23234324234234");
        login.setName("用户名");
        login.setDepartmentName("公司名");
        login.setId("213123123123");
        loginGroup.setUser(login);
        loginObtain.setData(loginGroup);
        return loginObtain;
    }
}
