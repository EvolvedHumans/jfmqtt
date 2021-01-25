package dti.org.dao;

import android.graphics.Bitmap;
import android.net.Uri;

import com.yangf.pub_libs.Date;

import java.io.Serializable;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 17时 11分
 * @Data： 相机拍照后存储的图片组,当点击照片时，需要放大查看，因此需要intent来实现跳转，并将这些类序列化。
 * 特性：
 * 1、能够区别标号顺序（标号顺序）type，集合下标+1作为参数标号
 * 2、生成随机事件戳作为图片名，保存图片不唯一
 * 3、存储源文件Bitmap
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class CameraGroup {
    private int type;
    private Bitmap bitmap;
    private String uri;
   // private String file = Date.sTimestamp();
}
