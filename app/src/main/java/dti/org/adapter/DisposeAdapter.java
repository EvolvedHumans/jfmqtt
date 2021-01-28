package dti.org.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.List;

import dti.org.R;
import dti.org.config.DisposeConfig;
import dti.org.config.SetoutConfig;
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

    private final static String TAG = "dti.org.adapter.scancode.DisposeAdapter";
    private List<String> list;
    private Context context;
    private DisposePresenter disposePresenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public DisposeAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
        disposePresenter = new DisposePresenter();
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
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
    @SuppressLint("LongLogTag")
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

                //获取产品类型
                int baseType = disposePresenter.getType();

                //todo 向中间层汇报当前点击item下标,同时将该下标下的配置选项信息存储起来,
                int type = disposePresenter.getItemType(position);

                //todo 转化Json格式数据
                String content = GsonYang.JsonString(disposeItemDialog.getWheelView());

                Log.d(TAG,content);

                if(baseType == SetoutConfig.Well){
                    if (type == SetoutConfig.WellTeam) {
                        Log4j.d(TAG,"班组");
                        editor.putString(DisposeConfig.WellTeam, content);
                    }
                    else if (type == SetoutConfig.WellConfigure) {
                        Log4j.d(TAG,"配置");
                        editor.putString(DisposeConfig.WellConfigure, content);
                    }
                    else if (type == SetoutConfig.WellScene) {
                        Log4j.d(TAG,"场景");
                        editor.putString(DisposeConfig.WellScene, content);
                    }
                    else if (type == SetoutConfig.WellOutside) {
                        Log4j.d(TAG,"外井盖类型");
                        editor.putString(DisposeConfig.WellOutside, content);
                    }
                    else if (type == SetoutConfig.WellRfid) {
                        Log4j.d(TAG,"RFID");
                        editor.putString(DisposeConfig.WellRfid, content);
                    }
                    else if (type == SetoutConfig.WellPedestal) {
                        Log4j.d(TAG,"基座类型");
                        editor.putString(DisposeConfig.WellPedestal, content);
                    }
                }

                else if(baseType == SetoutConfig.GroundNail){
                    if(type == SetoutConfig.GroundNailTeam){
                        Log4j.d(TAG,"保存了班组信息");
                        editor.putString(DisposeConfig.GroundNailTeam,content);
                    }
                    else if (type == SetoutConfig.GroundNailScene){
                        editor.putString(DisposeConfig.GroundNailScene,content);
                    }
                }
                editor.commit();
                editor.apply();

                Log4j.d(TAG,sharedPreferences.getString(DisposeConfig.GroundNailTeam,DisposeConfig.GroundNailTeam));

                disposePresenter.itemOptional(position);

                holder.button.setText(disposeItemDialog.getWheelView().getName());
                //todo 当选中的是配置选项时,存储选中选择器对应下标的type值
                // 1.判断position按钮是否是type->2
                // 2.如果是，则点击后存储WheelView对应下标的值
                //todo 3.判断position按钮是否是type->  ,则为是否带有RFID模块
                //todo 4.判断RFID模块下的type->,为1时则有
                disposeItemDialog.cancel();
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
