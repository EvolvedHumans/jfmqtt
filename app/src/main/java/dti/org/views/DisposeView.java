package dti.org.views;

import dti.org.adapter.DisposeAdapter;

import dti.org.base.BaseView;
import dti.org.item.DisposeItemDecoration;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 20日 12时 50分
 * @Data： 设备信息填写
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface DisposeView extends BaseView {
    /**
     * 刷新按钮组
     *
     * @param disposeAdapter 适配器
     * @param disposeItemDecoration item位置布局
     */
    void setButtonGroup(DisposeAdapter disposeAdapter, DisposeItemDecoration disposeItemDecoration);

    /**
     * 设置按钮可选
     */
    void startOptional();

}
