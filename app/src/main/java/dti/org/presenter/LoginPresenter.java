package dti.org.presenter;

import com.yangf.pub_libs.Date;
import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.HashMap;

import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.LoginConfig;
import dti.org.config.SharedPreferenceConfig;
import dti.org.config.SignConfig;
import dti.org.config.UrlConfig;
import dti.org.dao.LoginGroup;
import dti.org.model.Model;
import dti.org.views.LoginView;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 22时 09分
 * @Data： 数据层，继承BasePresenter，实现与登录界面的绑定
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    /**
     * 进入登录界面，判断是否第一次进入
     */
    public void firstLogin() {
        Log4j.d("登录",String.valueOf(getView().exportBooleanCache(LoginConfig.LOGIN_KEY, false)));
        if (getView().exportBooleanCache(LoginConfig.LOGIN_KEY, false)) {
            getView().jump();
        }
    }

    /**
     * 登录功能实现
     * 1.获取账户密码判断是否符合并给与响应提示
     * 2.开启加载图标
     * 3.编写业务逻辑，合成网络请求所需数据
     * 4.通过网络请求访问数据
     * 5.请求完成隐藏加载图标
     * 6.存储数据
     * 7.跳转到ChoiceActivity界面
     */
    public void login() {

        //1.
        String username = getView().getUserName();
        String password = getView().getPassword();
        if (username.equals(LoginConfig.NULL)) {
            getView().setUserTips(LoginConfig.USER_ERROR_NULL);
            return;
        } else {
            getView().setUserTips(LoginConfig.NULL);
        }
        if (password.equals(LoginConfig.NULL)) {
            getView().setPasswordTips(LoginConfig.PASSWORD_ERROR_NULL);
            return;
        } else {
            getView().setPasswordTips(LoginConfig.NULL);
        }

        //2.
        Log4j.d("showLoading","开始加载");
        getView().showLoading();

        //3.
        String timestamp = Date.sTimestamp();

        HashMap<String, String> header = new HashMap<>();
        header.put("ts", timestamp);
//        header.put("Content-Type","application/x-www-form-urlencoded"); //提交格式

        HashMap<String, String> body = new HashMap<>();
        body.put("appKey", SignConfig.APP_KEY);    //应用标识
        body.put("sign", SignConfig.sign(timestamp)); //将缓存区中的应用标识上传
        body.put("username", username);  //用户名
        body.put("password", password);  //密码

        Log4j.d("ts",timestamp);
//        Log4j.d("Content-Type","application/x-www-form-urlencoded");
        Log4j.d("appKey",SignConfig.APP_KEY);
        Log4j.d("sign",SignConfig.sign(timestamp));
        Log4j.d("username", username);
        Log4j.d("password", password);

        //4.
        Model.LoginModel(UrlConfig.LoginUrl, header, body, new BaseCallbcak() {
            @Override
            public void onSuccess(Object data) {
                //(6).存数据,保留登录历史记录(LoginGroup存储)
                LoginGroup loginGroup = (LoginGroup) data;
                getView().importStringCache(SharedPreferenceConfig.APP_LOGIN, GsonYang.JsonString(loginGroup));
                getView().importBooleanCache(LoginConfig.LOGIN_KEY, true);
                //(7).跳转
                getView().jump();
            }

            @Override
            public void onFailure(String msg) {
                getView().showErr(msg);
            }

            @Override
            public void onError(Throwable throwable) {
                //todo 后期添加崩溃，上传到bugly
                throwable.printStackTrace();
                getView().showErr(throwable.toString());
            }

            @Override
            public void onComplete() {
                Log4j.d("onComlete","关闭");
                getView().hideLoading();
            }
        });
    }
}
