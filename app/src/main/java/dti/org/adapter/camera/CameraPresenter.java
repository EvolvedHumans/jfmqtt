package dti.org.adapter.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yangf.pub_libs.DimensionImage;
import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.List;

import dti.org.activity.APhotoActivity;
import dti.org.activity.PhotoActivity;
import dti.org.config.CameraConfig;
import dti.org.dao.CameraGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 16时 33分
 * @Data： 相机业务层
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class CameraPresenter implements CameraPresenterView {

    private Context context;
    private List<CameraGroup> cameraGroups;

    public CameraPresenter(Context context, List<CameraGroup> cameraGroups) {
        this.context = context;
        this.cameraGroups = cameraGroups;
    }

    @Override
    public void onBindView(CameraHolder holder, int position) {
        int type = cameraGroups.get(position).getType();
        String uri = cameraGroups.get(position).getUri();
        Bitmap bitmap = cameraGroups.get(position).getBitmap();
        holder.imageButton.post(() -> {
            int width = holder.imageButton.getWidth();
            int height = 7 * width / 10;
            Log4j.d("width", String.valueOf(width));
            Log4j.d("height", String.valueOf(height));
            Log4j.d("bitmap", String.valueOf(bitmap));
            Log4j.d("uri", String.valueOf(uri));
            holder.setImage(bitmap, width, height);
            //holder.setImageBitmap();
        });
        //图片放大功能
        holder.imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, APhotoActivity.class);
            intent.putExtra(CameraConfig.PHOTO_Enlarge, GsonYang.JsonString(cameraGroups.get(position)));
            ((Activity) context).startActivityForResult(intent, CameraConfig.PHOTO_Activity);
        });
    }

    @Override
    public int getCount() {
        return cameraGroups.size();
    }
}
