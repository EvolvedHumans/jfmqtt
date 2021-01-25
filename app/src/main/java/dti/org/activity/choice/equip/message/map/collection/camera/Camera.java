package dti.org.activity.choice.equip.message.map.collection.camera;

import android.graphics.Bitmap;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 15日 14时 50分
 * @Data： 拍照参数
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
@AllArgsConstructor
public class Camera {

    //文件命名
    private String fileName;

    //图片文件路径存储,当判断需要添加图片时，才调用这个带入
    private Bitmap bitmap;

}
