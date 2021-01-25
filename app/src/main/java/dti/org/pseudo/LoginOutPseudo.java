package dti.org.pseudo;

import com.yangf.pub_libs.GsonYang;

import dti.org.dao.LoginoutObtain;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 14日 21时 11分
 * @Data： 登出操作假数据生成
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class LoginOutPseudo {
    public static String getJsonLoginOut(){
        LoginoutObtain loginoutObtain = new LoginoutObtain();
        loginoutObtain.setRt(1);
        loginoutObtain.setMsg("OK");
        loginoutObtain.setComments("返回数据成功");
        loginoutObtain.setData("登出操作完成");
        return GsonYang.JsonString(loginoutObtain);
    }
}
