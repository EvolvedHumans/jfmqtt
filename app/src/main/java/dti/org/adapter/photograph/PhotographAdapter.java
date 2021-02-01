package dti.org.adapter.photograph;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dti.org.R;
import lombok.AllArgsConstructor;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.adapter.photograph
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 31日 21时 12分
 */
@AllArgsConstructor
public class PhotographAdapter extends RecyclerView.Adapter<PhotographAdapter.VH> {

    private final static String TAG = "dti.org.adapter.exhibition";

    //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
    public static class VH extends RecyclerView.ViewHolder {

        //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
        ImageView imageView;

        public VH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }

    List<Photograph> list;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.photo_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.imageView.setImageBitmap(list.get(position).getBitmap());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
