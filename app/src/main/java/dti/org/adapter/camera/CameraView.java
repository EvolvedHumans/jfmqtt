package dti.org.adapter.camera;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 16时 39分
 * @Data： 相机视图层抽象接口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface CameraView {
    void setImage(Bitmap bitmap,int height,int width); //设置图片和宽、高，图片的宽、高等于控件的宽、高
    void setOnClickListener(View.OnClickListener listener); //设置图片点击事件
}
