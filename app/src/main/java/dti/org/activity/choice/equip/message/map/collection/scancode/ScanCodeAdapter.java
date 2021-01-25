package dti.org.activity.choice.equip.message.map.collection.scancode;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import dti.org.R;
import lombok.AllArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 14日 10时 06分
 * @Data： 实现指定适配器，扫码布局放进入
 * @TechnicalPoints：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@AllArgsConstructor
public class ScanCodeAdapter extends RecyclerView.Adapter<ScanCodeAdapter.VH>{

    //定义一个内部类viewHolder，继承自RecyclerView.ViewHolde，用来缓存子项的各个实例，提高刷新效率
    public static class VH extends RecyclerView.ViewHolder{
        Button button;
        TextView textView;

        public VH(@NonNull View itemView) {
            super(itemView);
            //将内部布局的控件写入

            //按钮
            this.button = itemView.findViewById(R.id.button_scan_code);

            //textview
            this.textView = itemView.findViewById(R.id.text_scan_code);
        }
    }

    private List<ScanCode> list;

    private Context context;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scancode,parent,false);

        VH vh = new VH(view);

        return vh;
    }

    //onBindViewHolder，将list中的内容给到item上各个实例（主要用于适配渲染数组到View中）
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //获取ScanCode集合中对应的对象
        ScanCode scanCode = list.get(position);
        holder.button.setText(scanCode.getText());
        holder.textView.setText(scanCode.getCode());

        holder.button.setOnClickListener(v -> {
            //这里动态更改主函数中传入的List集合的参数,并调用notifyDataSetChanged();
            //业务扫码特效
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setDecodeBarCode(true);//是否扫描条形码 默认为true
            config.setReactColor(R.color.touming);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.touming);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(true);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描

            Intent intent = new Intent(context, CaptureActivity.class);
            intent.putExtra(Constant.INTENT_ZXING_CONFIG,config);
//          parent.getContext().startActivity(intent);
            //类型强转实现跳转,并将序列号存入返回
            //这里是需要下标+1
            ((AppCompatActivity) context).startActivityForResult(intent, position);
        });

    }

    //返回子项个数
    @Override
    public int getItemCount() {
        return list.size();
    }
}
