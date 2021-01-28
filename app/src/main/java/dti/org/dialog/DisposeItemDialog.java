package dti.org.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

import dti.org.R;
import dti.org.adapter.WheelAdapter;

import dti.org.dao.Dispose;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 15日 14时 58分
 * @Data： Dispose信息选择界面，滚动选择器弹窗
 * 继承BottomSheetDialog实现完全沉入地底部
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class DisposeItemDialog extends BottomSheetDialog {

    Button button;
    TextView textView;
    WheelView<Dispose> wheelView;

    /**
     * 获取控件实例
     *
     * @param context Activity对象
     */
    @SuppressLint("SetTextI18n")
    public DisposeItemDialog(Context context) {
        super(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dispose_dialog, null);
        setCancelable(false); //设置点击失效，
        setContentView(view);

        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button = window.findViewById(R.id.dialog_button);
        textView = window.findViewById(R.id.dialog_text);
        wheelView = window.findViewById(R.id.withinBounds);
        wheelView.setWheelClickable(false);//设置滚轮选中项是否可点击
        wheelView.setWheelAdapter(new WheelAdapter(context));
        wheelView.setSkin(WheelView.Skin.Holo);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        wheelView.setStyle(style);
    }

    /**
     * 设置wheelView数据源
     *
     * @param list 数据源(Dispose)
     */
    public void setWheelView(List<Dispose> list) {
        wheelView.setWheelData(list);
    }

    /**
     * 获取当前滚轮所处位置的数据源
     *
     * @return 数据源
     */
    public Dispose getWheelView() {
        return wheelView.getSelectionItem();
    }

    /**
     * 获取当前所处位置Position
     *
     * @return position
     */
    public int getPosition() {
        return wheelView.getCurrentPosition();
    }


    /**
     * 设置按钮的点击事件
     *
     * @param listener 按钮点击事件
     */
    public void setOnClickListener(View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }
}
