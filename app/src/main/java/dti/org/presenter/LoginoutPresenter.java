package dti.org.presenter;

import android.os.Handler;
import android.os.Looper;

import com.yangf.pub_libs.Date;
import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.HashMap;

import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.SharedPreferenceConfig;
import dti.org.config.SignConfig;
import dti.org.config.UrlConfig;
import dti.org.dao.LoginGroup;
import dti.org.dao.Login;
import dti.org.model.Model;
import dti.org.views.LoginoutView;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 03日 20时 30分
 * @Data： 登出窗口交互层
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class LoginoutPresenter extends BasePresenter<LoginoutView> {
    //创建一个UI线程，并将这个UI线程放在主UI线程中去执行
    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 刷新页面
     * 1.查数据 -> todo 未查到需要重新跳转到登录界面(暂时不实现)
     * 2.绘制
     */
    public void drawLoginout(){
        String content = getView().exportStringCache(SharedPreferenceConfig.APP_LOGIN,SharedPreferenceConfig.NO);
        if(!content.equals(SharedPreferenceConfig.NO)){
            if(GsonYang.IsJson(content)){
                LoginGroup loginGroup = GsonYang.JsonObject(content, LoginGroup.class);
                Login login = loginGroup.getUser();
                if(login !=null){
                    String username = login.getName(); // 用户名
                    String departmentName = login.getDepartmentName(); //公司
                    if(username!=null && departmentName!=null){
                        handler.post(() -> {
                            getView().setUserTips(username);
                            getView().setPasswordTips(departmentName);
                        });
                        return;
                    }
                }
            }
        }
        getView().showErr("DrawLoginout is error:"+content);
    }

    /**
     * 登出请求
     */
    public void loginout(){
        //1,
        getView().showLoading();

        //2.取一个时间点的时间戳
        String timestamp = Date.sTimestamp();

        HashMap<String, String> header = new HashMap<>();
        header.put("ts", timestamp);
        header.put("Content-Type","application/x-www-form-urlencoded"); //提交格式

        HashMap<String, String> body = new HashMap<>();
        body.put("appKey", SignConfig.APP_KEY);    //应用标识
        body.put("sign", SignConfig.sign(timestamp)); //将缓存区中的应用标识上传

        Log4j.d("ts",timestamp);
        Log4j.d("appKey",SignConfig.APP_KEY);
        Log4j.d("sign",SignConfig.sign(timestamp));

        Model.LoginoutModel(UrlConfig.LoginOutUrl, header, body, new BaseCallbcak<String>() {
            @Override
            public void onSuccess(String data) {
                //3.提示语
                getView().showToast(data);

                //4.清除记录
                getView().clearCache();

                //5.跳转
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
                getView().hideLoading();
            }
        });
    }

}
