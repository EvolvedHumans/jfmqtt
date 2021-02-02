package dti.org.views;

import android.net.Uri;

import dti.org.adapter.camera.CameraAdapter;
import dti.org.adapter.scancode.ScanCodeAdapter;

import dti.org.dao.MapObtain;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 14时 07分
 * @Data： 智能井盖View层视图
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface WellView extends LoginView {
    /**
     * 1.经度
     * 2.纬度
     * 3.逆编码地址
     * 4.主产品Type
     * 5.产品下从设备Type
     *
     * @return 获取地图Activity中传递过来的信息
     */
    MapObtain getMapContainer();

    /**
     * 添加扫码按钮适配器
     */
    void drawScanCode(ScanCodeAdapter scanCodeAdapter);

    /**
     * 添加拍照适配器
     */
    void drawCamera(CameraAdapter cameraAdapter);

    /**
     * 工井ID
     */
    String wellId();

    /**
     * 设备名称
     */

    /**
     * 工井id为空，填充颜色警告
     */
    void idWarning();

    /**
     * 设备名称为空，填充颜色警告
     */
    void nameWarning();

    /**
     * 位置信息为空，填充颜色警告
     */
    void localWarning();

    /**
     * 拍照回调处理
     *
     * @param uri         入参
     * @param requestCode 请求时Intent入参
     */
    void cameraIntent(Uri uri, int requestCode);

    /**
     * 安装成功
     */
    /**
     * 跳转到安装成功界面
     * WellInstall类的json格式参数
     */
    void installSuccessful(String key, String json);


    /**
     * 跳转到安装失败界面
     * WellInstall类的json格式参数
     */
    void installFailed(String key, String json);


    /**
     * 继续安装弹框
     */
    void showExportPopup(String title, String resource);

}
