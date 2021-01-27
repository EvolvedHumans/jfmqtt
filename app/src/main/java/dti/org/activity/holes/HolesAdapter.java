package dti.org.activity.holes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import dti.org.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 18日 11时 01分
 * @Data： 孔洞布局适配器, 对应HolesRecyclerView类
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class HolesAdapter extends HolesRecyclerView.Adapter<HolesAdapter.VH> {

    //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
    public static class VH extends HolesRecyclerView.ViewHolder {

        //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
        Button button;

        public VH(@NonNull View itemView) {
            super(itemView);
            //按钮
            button = itemView.findViewById(R.id.holes_button);
            button.setHeight(HolesProperties.spToPx_BUTTON_ITEM());
            button.setWidth(HolesProperties.spToPx_BUTTON_ITEM());
        }
    }

    private List<HolesData> list;

    private Context context;

    /*
    创建ViewHolder
     */
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_holes, parent, false);

        VH vh = new VH(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {


        holder.button.setText(String.valueOf(list.get(position).getNumber()));
        holder.button.setBackgroundResource(list.get(position).getBackgroundColor());
        holder.button.setOnClickListener(v -> {
            //点击扫码功能跳转
            //业务扫码特效fg1o
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setDecodeBarCode(true);//是否扫描条形码 默认为true
            config.setReactColor(R.color.touming);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.touming);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(true);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描

            Intent intent = new Intent(context, CaptureActivity.class);

            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);

            //返回对应list下标position
            ((AppCompatActivity) context).startActivityForResult(intent, position);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
