package dti.org.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.yangf.pub_libs.DimensionImage;
import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.config.CameraConfig;
import dti.org.dao.CameraGroup;
import dti.org.databinding.ActivityPhotoBinding;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.activity.choice.equip.message.map.collection
 * 描述：      删除、查看照片
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 24日 22时 18分
 */
public class PhotoActivity extends BaseActivity {

    ActivityPhotoBinding activityPhotoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPhotoBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
        setTranSlucent();
        Intent intent = getIntent();
        String json = intent.getStringExtra(CameraConfig.PHOTO_Enlarge);
        CameraGroup cameraGroup = GsonYang.JsonObject(json,CameraGroup.class);
        Log4j.d("----",cameraGroup.toString());
        activityPhotoBinding.photo.text.setText("照片"+cameraGroup.getType());
        activityPhotoBinding.photo.imageIcon.post(() -> {
            int width = activityPhotoBinding.photo.imageIcon.getWidth();
            int height = activityPhotoBinding.photo.imageIcon.getHeight();
            activityPhotoBinding.photo.imageIcon.setImageBitmap
                    (DimensionImage.zoomBitmap(cameraGroup.getBitmap(), width, height));
        });
        activityPhotoBinding.photo.imageIconReturn.setOnClickListener(v -> {
            finish();
        });
        activityPhotoBinding.photo.imageIconDelete.setOnClickListener(v -> {
            intent.putExtra(CameraConfig.PHOTO_DELETE,cameraGroup.getType());
            setResult(CameraConfig.RESULT_Well_OK,intent);
            finish();
        });
    }
}
