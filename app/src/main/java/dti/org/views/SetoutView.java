package dti.org.views;

import android.view.View;

import dti.org.adapter.SetoutAdapter;
import dti.org.base.BaseView;
import dti.org.dao.DisposeObtain;
import dti.org.listener.SetoutPageChange;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 20时 41分
 * @Data： 产品选择View接口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface SetoutView extends BaseView {
    /**
     * 将导入图片name和地址，转换成View
     *
     * @param name    图片名
     * @param picture 图片地址
     * @return View视图
     */
    View toPager(String name, String picture);

    /**
     * 刷新ViewPager控件，并将SetoutAdapter适配器配置到ViewPager上
     *
     * @param setoutAdapter 适配器
     */
    void setViewPager(SetoutAdapter setoutAdapter, SetoutPageChange setoutPageChange);

    /**
     * 界面跳转并将数据源序列化的序列化并传递给下个界面
     */
    void jump();
}
