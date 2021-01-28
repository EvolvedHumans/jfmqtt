package dti.org.model;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import dti.org.base.BaseCallbcak;
import dti.org.dao.GroundNailInstallObtain;
import dti.org.updater.AppUpdater;
import dti.org.updater.net.INetCallBack;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.model
 * 描述：      TODO 地钉GroundNail请求
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 28日 15时 57分
 */
public class GroundNailModel {

    private final static String TAG = "dti.org.model.GroundNailModel";

    public static void GroundNailInput(String url, String json, BaseCallbcak<String> callbcak) {
        AppUpdater.getInstance().getOKHttpNetManager().postJsonExecute(url, json, new INetCallBack() {
            @Override
            public void success(String content) {
                //todo 获取到了安装完成的响应回调
                callbcak.onSuccess(content);
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
}
