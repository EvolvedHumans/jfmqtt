package dti.org.adapter.scancode;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 17时 36分
 * @Data： 扫码适配器视图层
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface ScanCodeView {

    /**
     * 文本框TextView
     */
    void setText(String text);

    /**
     * 文本框区域对应颜色
     */
    void setLinearLayoutColor(Drawable drawable);

    /**
     * Button按键对应Text
     */
    void setContent(String content);

    /**
     * 获取按键点击事件
     */
    void setOnClickListener(View.OnClickListener listener);
}
