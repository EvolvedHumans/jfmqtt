package dti.org.adapter.camera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.Log4j;

import dti.org.R;
import lombok.AllArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 16时 32分
 * @Data： 相机拍照适配器
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@AllArgsConstructor
public class CameraAdapter extends RecyclerView.Adapter<CameraHolder> {

    CameraPresenter cameraPresenter;

    @NonNull
    @Override
    public CameraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_image,parent,false);
        return new CameraHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CameraHolder holder, int position) {
        cameraPresenter.onBindView(holder,position);
    }

    @Override
    public int getItemCount() {
        return cameraPresenter.getCount();
    }
}
