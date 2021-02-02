package dti.org.views;

import dti.org.base.BaseView;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.views
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 26日 09时 38分
 */
public interface GroundNailView extends WellView {
    /**
     * 显示安装弹出框
     */
    void showExportPopup(String tile, String resource);

    /**
     * 关闭去掉警告颜色
     */
    void scancodeNormal();

    /**
     * 跳转到安装成功界面
     * GroundNailInstall类的json格式参数
     */
    void installSuccessful(String key, String json);

    /**
     * 跳转到安装失败界面
     * GroundNailInstall类的json格式参数
     */
    void installFailed(String key, String json);


}
