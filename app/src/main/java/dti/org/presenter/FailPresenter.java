package dti.org.presenter;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.GroundNailConfig;
import dti.org.dao.GroundNailInstall;
import dti.org.dao.GroundNailInstallObtain;
import dti.org.model.GroundNailModel;
import dti.org.model.WellModel;
import dti.org.updater.AppUpdater;
import dti.org.updater.net.INetCallBack;
import dti.org.views.FailView;
import dti.org.views.WellView;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.presenter
 * 描述：      TODO 请求失败,重新发出请求界面
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 28日 15时 26分
 */
public class FailPresenter extends BasePresenter<FailView> {

    private final static String TAG = "dti.org.presenter";

    static GroundNailInstall groundNailInstall;

    public void retryRequest() {
        String json = getView().getStringIntent(GroundNailConfig.GroundNailImportFail);
        if (GsonYang.IsJson(json)) {
            getView().showLoading();
            groundNailInstall = GsonYang.JsonObject(json, GroundNailInstall.class);
            //获取url
            String url = groundNailInstall.getUrl();
            if (url != null) {
                //发起请求
                GroundNailModel.GroundNailInput(url, json, new BaseCallbcak<String>() {
                    @Override
                    public void onSuccess(String content) {
                        if (GsonYang.IsJson(content)) {
                            GroundNailInstallObtain groundNailInstallObtain =
                                    GsonYang.JsonObject(content, GroundNailInstallObtain.class);
                            if (groundNailInstallObtain != null) {
                                if (groundNailInstallObtain.getRt() != null && groundNailInstallObtain.getMsg() != null) {
                                    if (groundNailInstallObtain.getRt() == 1) {
                                        getView().showToast(groundNailInstallObtain.getMsg());
                                    } else {
                                        groundNailInstall.setInstall(1);
                                        getView().showToast(groundNailInstallObtain.getMsg());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        //getView().showErr(msg);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        getView().showErr("无法与服务器响应");
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
            }
        }
    }
}























