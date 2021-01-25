package dti.org.model;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.HashMap;
import java.util.List;

import dti.org.base.BaseCallbcak;
import dti.org.dao.DisposeGroup;
import dti.org.dao.DisposeObtain;
import dti.org.dao.LoginGroup;
import dti.org.dao.Setout;
import dti.org.dao.SetoutObtain;
import dti.org.dao.LoginObtain;
import dti.org.dao.LoginoutObtain;
import dti.org.pseudo.DisposePseudo;
import dti.org.pseudo.EquipPseudo;
import dti.org.pseudo.LoginOutPseudo;
import dti.org.pseudo.LoginPseudo;
import dti.org.updater.AppUpdater;
import dti.org.updater.net.INetCallBack;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 20时 29分
 * @Data： 网络数据获取接口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class Model {
    /**
     * 登录接口数据提取
     */
    public static void LoginModel(String url, HashMap<String, String> header,
                                  HashMap<String, String> body, BaseCallbcak<LoginGroup> callbcak) {
        //Post同步
        AppUpdater.getInstance().getOKHttpNetManager().postFormExecute(url, header, body, new INetCallBack() {
            @Override
            public void success(String content) {
                //content= GsonYang.JsonString(LoginPseudo.getJsonLogin());
                //协议规定必须是json字符串
                if (GsonYang.IsJson(content)) {
                    LoginObtain login = GsonYang.JsonObject(content, LoginObtain.class);
                    //判断是否是对应json字符串
                    if (login != null) {
                        if (login.getRt() != null && login.getComments() != null) {
                            if (login.getRt() == 1 && login.getData() != null) {
                                callbcak.onSuccess(login.getData());
                                return;
                            }
                        }
                    }
                }
                callbcak.onFailure(content);
            }

            @Override
            public void failed(Throwable throwable) {
                callbcak.onError(throwable);
            }

            @Override
            public void onComplete() {
                callbcak.onComplete();
            }
        });

    }

    /**
     * 登出接口数据提取
     */
    public static void LoginoutModel(String url, HashMap<String, String> header,
                                     HashMap<String, String> body, BaseCallbcak<String> callbcak) {
        AppUpdater.getInstance().getOKHttpNetManager().postFormExecute(url, header, body, new INetCallBack() {
            @Override
            public void success(String content) {
                // content = LoginOutPseudo.getJsonLoginOut();
                //判断它是否为json字符串格式
                if (GsonYang.IsJson(content)) {
                    LoginoutObtain loginOut = GsonYang.JsonObject(content, LoginoutObtain.class);
                    if (loginOut != null) {
                        if (loginOut.getRt() == 1 && loginOut.getComments() != null) {
                            callbcak.onSuccess(loginOut.getComments());
                            callbcak.onComplete();
                            return;
                        }
                    }
                }
                callbcak.onFailure(content);
                callbcak.onComplete();
            }

            @Override
            public void failed(Throwable throwable) {
                callbcak.onError(throwable);
                callbcak.onComplete();
            }

            @Override
            public void onComplete() {
                callbcak.onComplete();
            }
        });

    }

    /**
     * 产品选择，加载进入页面时的请求
     */
    public static void SetoutModel(String url, BaseCallbcak<List<Setout>> callbcak) {
        AppUpdater.getInstance().getOKHttpNetManager().postFormExecute(url, null, null, new INetCallBack() {
            @Override
            public void success(String content) {
                //content = EquipPseudo.getJsonEquip();
                if (GsonYang.IsJson(content)) {
                    SetoutObtain setoutObtain = GsonYang.JsonObject(content, SetoutObtain.class);
                    if (setoutObtain != null && setoutObtain.getRt() != null) {
                        if (setoutObtain.getRt() == 1 && setoutObtain.getData() != null) {
                            callbcak.onSuccess(setoutObtain.getData());
                            return;
                        }
                    }
                }
                callbcak.onFailure(content);
            }

            @Override
            public void failed(Throwable throwable) {
                callbcak.onError(throwable);
            }

            @Override
            public void onComplete() {
                callbcak.onComplete();
            }
        });
    }

    /**
     * 产品选择结束
     */
    public static void SetoutEndModel(String url, HashMap<String, String> header, HashMap<String, String> body, BaseCallbcak<String> callbcak) {
        AppUpdater.getInstance().getOKHttpNetManager().postFormExecute(url, header, body, new INetCallBack() {
            @Override
            public void success(String content) {
                callbcak.onSuccess(content);
            }

            @Override
            public void failed(Throwable throwable) {
                callbcak.onError(throwable);
            }

            @Override
            public void onComplete() {
                callbcak.onComplete();
            }
        });
    }


}
