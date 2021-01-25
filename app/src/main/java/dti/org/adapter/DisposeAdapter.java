package dti.org.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.Log4j;

import java.util.List;

import dti.org.R;
import dti.org.config.SharedPreferenceConfig;
import dti.org.dialog.DisposeItemDialog;
import dti.org.presenter.DisposePresenter;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 15日 11时 45分
 * @Data： Dispose按键刷新
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class DisposeAdapter extends RecyclerView.Adapter<DisposeAdapter.VH> {

    //创建ViewHolder静态方法，获取当前布局
    public static class VH extends RecyclerView.ViewHolder {
        public Button button;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.team);
        }
    }

    private List<String> list;

    private Context context;

    private DisposePresenter disposePresenter;

    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public DisposeAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
        disposePresenter = new DisposePresenter();
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 将View封装进VH中
     *
     * @param parent   ViewGroup所有View的父类
     * @param viewType //
     * @return View集合, VH
     */
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dispose, parent, false);
        return new VH(view);
    }

    /**
     * 将数据适配渲染到View中，设置holder中button点击事件
     *
     * @param holder   控件ID
     * @param position View下标
     */
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.button.setText(list.get(position));
        holder.button.setOnClickListener(v -> {
            DisposeItemDialog disposeItemDialog = new DisposeItemDialog(context);
            if (disposePresenter.itemDatabase(position) != null) {
                disposeItemDialog.setWheelView(disposePresenter.itemDatabase(position));
            }
            disposeItemDialog.show();
            disposeItemDialog.setOnClickListener(v1 -> {
                //todo 向中间层汇报当前点击item下标
                disposePresenter.itemOptional(position);
                holder.button.setText(disposeItemDialog.getWheelView().getName());
                //todo 当选中的是配置选项时,存储选中选择器对应下标的type值
                // 1.判断position按钮是否是type->2
                // 2.如果是，则点击后存储WheelView对应下标的值
                //todo 3.判断position按钮是否是type->  ,则为是否带有RFID模块
                //todo 4.判断RFID模块下的type->,为1时则有
                if(disposePresenter.itemConfig(position)) {
                    Log4j.d("选择NB模块配置项",String.valueOf(disposeItemDialog.getWheelView().getType()));
                    editor.putInt(SharedPreferenceConfig.NB_Type,disposeItemDialog.getWheelView().getType());
                    editor.commit();
                }
                disposeItemDialog.cancel();
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
