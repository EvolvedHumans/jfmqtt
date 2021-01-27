package dti.org.presenter;

import com.google.gson.Gson;
import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.util.UrlsplicingUtil;

import java.util.HashMap;

import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.UrlConfig;
import dti.org.dao.UidTest;
import dti.org.model.WellModel;
import dti.org.views.GroundNailView;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.presenter
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 26日 09时 30分
 */
public class GroundNailPresenter extends BasePresenter<GroundNailView> {
    //地钉接口入参校验
    public void updateScancode(String text) {
        getView().showLoading();

        HashMap<String, String> param = new HashMap<>(); //入参参数

        param.put("stakeUid", text);

        String url = UrlsplicingUtil.attachHttpGetParams(UrlConfig.GroundNail, param);

        WellModel.isTesting(url, new BaseCallbcak<String>() {
            @Override
            public void onSuccess(String data) {
                if (GsonYang.IsJson(data)) {
                    UidTest uidTest = GsonYang.JsonObject(data, UidTest.class);
                    if (uidTest.getRt() != null && uidTest.getData() != null) {
                        if (uidTest.getRt() == 1) {
                            if (uidTest.getData()) {
                                getView().setUserTips(text);
                            } else {
                                getView().setUserTips(text);
                                getView().idWarning();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                getView().showErr(msg);
            }

            @Override
            public void onError(Throwable throwable) {
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
