package dti.org.views;

import dti.org.base.BaseView;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.views
 * 描述：      TODO 失败的跳转界面
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 28日 15时 24分
 */
public interface FailView extends BaseView {
    //获取跳转数据，数据为json格式数据
    String getStringIntent(String key);

    //获取安装完成后，产品类型的配置
    int getIntIntent(String key);

    //跳转到设备信息选择界面
    void jump();
}
