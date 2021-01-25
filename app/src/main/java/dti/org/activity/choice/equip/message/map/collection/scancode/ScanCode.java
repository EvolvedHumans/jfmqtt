package dti.org.activity.choice.equip.message.map.collection.scancode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 14日 10时 18分
 * @Data： 添加扫码参数
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanCode {

    //按钮中的内容
    private String text;

    //按钮下文本框中的内容，记录扫码ID
    private String code;
}
