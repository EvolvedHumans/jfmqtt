package dti.org.config;

import com.yangf.pub_libs.util.CryptUtil;

/**
 * 登录签名常量
 *
 * @Author : XuJian
 * @Date : 2019年04月03日 11:13
 */
public final class SignConfig {
    //应用表示
    public final static String APP_KEY = "c4d1a68dff4d4801b6eb326ba10dd2f2";
    public final static String APP_SECRET = "d171b6b11ac64834a12c167d3db053d2";
    public final static String SIGN_KEY = "dti@002";

    //MD5加密
    public static String sign(String ts) {
        return CryptUtil.encryptMD5(APP_SECRET + ts + SIGN_KEY);
    }

}
