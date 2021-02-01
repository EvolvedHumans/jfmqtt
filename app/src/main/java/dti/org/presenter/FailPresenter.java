package dti.org.presenter;

import com.yangf.pub_libs.GsonYang;

import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.GroundNailConfig;
import dti.org.config.InstallConfig;
import dti.org.config.SetoutConfig;
import dti.org.config.WellConfig;
import dti.org.dao.GroundNailInstall;
import dti.org.dao.InstallObtain;
import dti.org.dao.WellInstall;
import dti.org.model.GroundNailModel;
import dti.org.model.WellModel;
import dti.org.views.FailView;

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

    static WellInstall wellInstall;

    public void retryRequest() {
        //todo 1.先获取产品类型
        int type = getView().getIntIntent(InstallConfig.InstallTypeIntent);

        if (type == SetoutConfig.Well) {
            String json = getView().getStringIntent(WellConfig.WellImportFail);
            if (GsonYang.IsJson(json)) {
                wellInstall = GsonYang.JsonObject(json, WellInstall.class);
                //获取url
                String url = wellInstall.getUrl();
                if (url != null) {
                    getView().showLoading();
                    WellModel.WellInput(url, json, new BaseCallbcak<String>() {
                        @Override
                        public void onSuccess(String content) {
                            if (GsonYang.IsJson(content)) {
                                InstallObtain installObtain =
                                        GsonYang.JsonObject(content, InstallObtain.class);
                                if (installObtain != null) {
                                    if (installObtain.getRt() != null && installObtain.getMsg() != null) {
                                        if (installObtain.getRt() == 1) {
                                            //todo 跳转到设备信息选择界面
                                            getView().jump();
                                        } else {
                                            groundNailInstall.setInstall(1);
                                        }
                                        getView().showToast(installObtain.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(String msg) {

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

        if (type == SetoutConfig.GroundNail) {
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
                                InstallObtain installObtain =
                                        GsonYang.JsonObject(content, InstallObtain.class);
                                if (installObtain != null) {
                                    if (installObtain.getRt() != null && installObtain.getMsg() != null) {
                                        if (installObtain.getRt() == 1) {
                                            //todo 跳转到设备信息选择界面
                                            getView().showToast(installObtain.getMsg());
                                            getView().jump();
                                        } else {
                                            groundNailInstall.setInstall(1);
                                            getView().showToast(installObtain.getMsg());
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
}























