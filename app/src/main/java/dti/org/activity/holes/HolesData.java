package dti.org.activity.holes;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 18日 11时 31分
 * @Data： 孔洞适配器所需序列号
 * @TechnicalPoints：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
@AllArgsConstructor
public class HolesData {
    //孔洞序列号
    private int number;

    //孔洞背景色
    private int backgroundColor;
}
