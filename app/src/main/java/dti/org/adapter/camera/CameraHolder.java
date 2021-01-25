package dti.org.adapter.camera;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.DimensionImage;

import dti.org.R;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 16时 33分
 * @Data： 相机所对应的视图模型
 * 该视图特性：
 * 1、设置Bitemap
 * 2、设置图片大小
 * 3、添加图片点击事件监听接口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class CameraHolder extends RecyclerView.ViewHolder implements CameraView{

    ImageButton imageButton;

    public CameraHolder(@NonNull View itemView) {
        super(itemView);
        this.imageButton = itemView.findViewById(R.id.image);
    }

    /**
     *
     * @param bitmap 图片
     * @param width 宽
     * @param height 高
     */
    @Override
    public void setImage(Bitmap bitmap,int width,int height) {
        imageButton.setImageBitmap(DimensionImage.zoomBitmap(bitmap,width,height));
    }

    /**
     *
     * @param listener 设置按钮监听事件
     */
    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        imageButton.setOnClickListener(listener);
    }
}
