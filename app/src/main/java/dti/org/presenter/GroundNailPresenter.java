package dti.org.presenter;

import com.yangf.pub_libs.Date;
import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;
import com.yangf.pub_libs.jxi.ExcelUtils;
import com.yangf.pub_libs.util.FileUtil;
import com.yangf.pub_libs.util.UrlsplicingUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.DisposeConfig;
import dti.org.config.GroundNailConfig;
import dti.org.config.SharedPreferenceConfig;
import dti.org.config.UrlConfig;
import dti.org.dao.Dispose;
import dti.org.dao.GroundNailInstall;
import dti.org.dao.InstallObtain;
import dti.org.dao.LoginGroup;
import dti.org.dao.MapObtain;
import dti.org.dao.UidTest;
import dti.org.model.GroundNailModel;
import dti.org.model.WellModel;
import dti.org.views.GroundNailView;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.presenter
 * 描述：      TODO 道钉中间层交互界面
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 26日 09时 30分
 */
public class GroundNailPresenter extends BasePresenter<GroundNailView> {

    private final static String TAG = "dti.org.activity.GroundNailPresenter";

    private static MapObtain mapObtain;

    public static GroundNailInstall groundNailInstall = new GroundNailInstall();


    //道钉UI绘制
    public void drawGroundNail() {
        mapObtain = getView().getMapContainer();
        assert mapObtain != null;
        getView().setUserName(mapObtain.getAddress());
        getView().setPasswordTips(mapObtain.getAddress());
    }

    //地钉接口入参校验
    public void updateScancode(String text) {
        getView().showLoading();

        HashMap<String, String> param = new HashMap<>(); //入参参数

        param.put("stakeUid", text);

        Log4j.d(TAG, text);

        String url = UrlsplicingUtil.attachHttpGetParams(UrlConfig.GroundNail, param);

        Log4j.d(TAG, url);

        WellModel.isTesting(url, new BaseCallbcak<String>() {
            @Override
            public void onSuccess(String data) {
                Log4j.d(TAG, data);
                if (GsonYang.IsJson(data)) {
                    UidTest uidTest = GsonYang.JsonObject(data, UidTest.class);
                    if (uidTest.getRt() != null && uidTest.getData() != null && uidTest.getMsg() != null) {
                        if (uidTest.getRt() == 1) {
                            groundNailInstall.setStakeUid(text);
                            if (uidTest.getData()) {
                                groundNailInstall.setInstall(1);
                                getView().setUserTips(text);
                                getView().scancodeNormal();
                            } else {
                                groundNailInstall.setInstall(0);
                                getView().setUserTips(text);
                                getView().idWarning();
                            }
                            getView().showToast(uidTest.getMsg());
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
                getView().showErr("与服务器无响应");
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }

    /**
     * 地钉安装按钮
     * 1.产品类型 lineType  String类型
     * 2.道钉二维码标识 stakeUid String类型
     * 3.经度 lon  String类型
     * 4.纬度 lat  String类型
     * 5.设备名称 name   String类型  //未知
     * 6.班组id banzuId String类型
     * 6.公司iD departmentId  String类型
     * 7.安装场景  sceneType  int类型
     * 8.安装图片 pictures    String类型
     * 9.如有被使用的情况下是否继续安装 install  int类型
     */
    public void clickInstall() {

        //   Log4j.d(TAG,content);

        if (groundNailInstall.getStakeUid() == null) {
            getView().idWarning();
            // Log4j.d(TAG,"弹出警告");
            getView().showErr("地钉二维码未扫描");
            return;
        }

        getView().showLoading();

        String temp;

        //公司id获取
        String departmentId = null;
        LoginGroup loginGroup = GsonYang.JsonObject(getView().exportStringCache
                (SharedPreferenceConfig.APP_LOGIN, SharedPreferenceConfig.NO), LoginGroup.class);
        Log4j.d("公司id获取", loginGroup.toString());
        if (loginGroup != null) {
            departmentId = loginGroup.getUser().getDepartmentId();
        }

        //获取班组id
        String banzuId = null;
        temp = getView().exportStringCache(DisposeConfig.GroundNailTeam, DisposeConfig.GroundNailTeam);
        Log4j.d("班组id", temp);
        if (GsonYang.IsJson(temp)) {
            Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
            Log4j.d("获取班组id", dispose.toString());
            if (dispose != null) {
                banzuId = dispose.getId();
            }
        }

        //获取安装场景sceneType
        Integer sceneType = null;
        String scene = null;
        String sceneTypeId = null;
        temp = getView().exportStringCache(DisposeConfig.GroundNailScene, DisposeConfig.GroundNailScene);
        if (GsonYang.IsJson(temp)) {
            Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
            Log4j.d("获取安装场景sceneType", dispose.toString());
            if (dispose != null) {
                sceneType = dispose.getType();
                scene = dispose.getName();
                sceneTypeId = dispose.getId();
            }
        }

        //获取地图端传递过来的信息（产品类型、经度、纬度、设备名称）
        String lineType = String.valueOf(mapObtain.getBaseType());
        Log4j.d("地图段传递过来的信息，产品类型", String.valueOf(mapObtain.getBaseType()));
        String lon = mapObtain.getLongitude();
        String lat = mapObtain.getLatitude();
        String name = mapObtain.getAddress();

        //获取扫码后获取到的信息
        String stakeUid = groundNailInstall.getStakeUid();

        //int install = 0; //继续安装

        //道钉无图片信息，传递道钉图片照片信息即可
        String pictures = "http://www.yyj2857.cn/wp-content/uploads/2020/12/b.png";
//             * 1.产品类型 lineType  String类型
//                * 2.道钉二维码标识 stakeUid String类型
//                * 3.经度 lon  String类型
//                * 4.纬度 lat  String类型
//                * 5.设备名称 name   String类型  //未知
//                * 6.班组id banzuId String类型
//                * 7.公司iD departmentId  String类型
//                * 8.安装场景  sceneType  int类型
//                * 9.安装图片 pictures    String类型
//                * 10.如有被使用的情况下是否继续安装 install  int类型

        String url = UrlConfig.ImportGroundNail;
//            //todo 数据导入
        Log4j.d("1.产品类型", lineType);
        Log4j.d("2.道钉二维码标识", stakeUid);
        Log4j.d("3.经度", lon);
        Log4j.d("4.纬度", lat);
        Log4j.d("5.设备名称", name);
        Log4j.d("6.班组id产品类型", banzuId);
        Log4j.d("7.公司id", departmentId);
        Log4j.d("8.安装场景", String.valueOf(sceneType));
        Log4j.d("9.安装图片", pictures);
        Log4j.d("10.如有被使用的情况下是否继续安装", String.valueOf(groundNailInstall.getInstall()));
        groundNailInstall.setUrl(url);
        groundNailInstall.setLineType(lineType);
        groundNailInstall.setStakeUid(stakeUid);
        groundNailInstall.setLon(lon);
        groundNailInstall.setLat(lat);
        groundNailInstall.setName(name);
        groundNailInstall.setBanzuId(banzuId);
        groundNailInstall.setDepartmentId(departmentId);
        groundNailInstall.setSceneType(sceneType);
        groundNailInstall.setScene(scene);
        groundNailInstall.setScaneTypeId(sceneTypeId);
        groundNailInstall.setPictures(pictures);

        String json = GsonYang.JsonString(groundNailInstall);
        Log4j.d(TAG, json);

        //表头
        List<String> list = new LinkedList<>();
        list.add("地钉二维码");
        list.add("经度");
        list.add("纬度");
        list.add("设备名称");
        list.add("班组id");
        list.add("公司id");
        list.add("场景");
        list.add("照片");
        list.add("有无导入");
        List<String> listForm = new LinkedList<>();
        listForm.add(stakeUid);
        listForm.add(lon);
        listForm.add(lat);
        listForm.add(name);
        listForm.add(banzuId);
        listForm.add(departmentId);
        listForm.add(scene);
        listForm.add(pictures);

        GroundNailModel.GroundNailInput(url, json, new BaseCallbcak<String>() {
            @Override
            public void onSuccess(String content) {
                if (GsonYang.IsJson(content)) {
                    InstallObtain installObtain =
                            GsonYang.JsonObject(content, InstallObtain.class);
                    if (installObtain != null) {
                        if (installObtain.getRt() != null && installObtain.getMsg() != null) {
                            if (installObtain.getRt() == 1) {
                                getView().showToast(installObtain.getMsg());
                                getView().installSuccessful
                                        (GroundNailConfig.GroundNailImportSuccess, json);
                                listForm.add("导入成功");
                            } else {
                                getView().showExportPopup("提示", installObtain.getMsg());
                                listForm.add("导入失败");
                            }
                            ExcelUtils.createFile(FileUtil.getExcelFileName("道钉" + Date.timestamp()), getView().getContext());
                            ExcelUtils.createExcel(list, listForm);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                // getView().showErr(msg);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().showErr("与服务器无响应");
                getView().installFailed
                        (GroundNailConfig.GroundNailImportFail, json);
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });

    }

    /**
     * 重复安装事故回调方法,地钉
     * 将install -> 置为1，当再次点击安装时，直接覆盖原有的安装记录
     */
    public void overlap() {
        groundNailInstall.setInstall(1);
    }

    public void release() {
        groundNailInstall = new GroundNailInstall();
    }

}