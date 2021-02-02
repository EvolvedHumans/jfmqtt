package dti.org.adapter.exhibition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.Log4j;

import java.util.List;

import dti.org.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.adapter.exhibition
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 31日 20时 18分
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.VH> {

    private final static String TAG = "dti.org.adapter.exhibition";

    //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
    public static class VH extends RecyclerView.ViewHolder {

        //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
        TextView title;
        TextView resource;

        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            resource = itemView.findViewById(R.id.resource);
        }
    }

    List<Exhibition> list;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibition_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.resource.setText(list.get(position).getResource());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
