package dti.org.dao;

import android.graphics.drawable.Drawable;

import dti.org.R;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 22时 35分
 * @Data： ScanCode扫码区域
 * code表示按钮位置的文本框内容
 * type表示type，即类型
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class ScanCode {

    //按钮栏目中的content内容
    String code;

    //文本框中的内容->默认为""
    String text = "";

    //文本框区域内的颜色填充
    Drawable drawable;

    //对应的类型
    Integer type;
}
