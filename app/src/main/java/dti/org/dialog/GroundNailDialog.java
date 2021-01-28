package dti.org.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yangf.pub_libs.DensityUtil;

import dti.org.R;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.dialog
 * 描述：      TODO
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 28日 14时 54分
 */
public class GroundNailDialog extends Dialog {

    //标题
    TextView title;

    //内容栏
    TextView content;

    //继续安装
    Button buttonProceed;

    //取消安装
    Button buttonClose;

    //横向宽度
    int width = 100;

    public GroundNailDialog(@NonNull Context context) {
        super(context);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ground_nail_dialog, null);
        setCancelable(false); //设置点击失效，
        setContentView(view);

        Window window = getWindow();
        assert window != null;
        window.setLayout(DensityUtil.getScreenWidth() - width, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.touming);
        title = window.findViewById(R.id.ground_nail_title);
        content = window.findViewById(R.id.ground_nail_content);
        buttonProceed = window.findViewById(R.id.proceed_button);
        buttonClose = window.findViewById(R.id.close_button);
    }

    /**
     * @param title Dialog 标题
     */
    public void setTitle(@NonNull String title) {
        this.title.setText(title);
    }

    /**
     * @param content Dialog 内容
     */
    public void setContent(@NonNull String content) {
        this.content.setText(content);
    }

    /**
     * @param listener 按钮
     */
    public void setOnClickProceedListener(View.OnClickListener listener) {
        buttonProceed.setOnClickListener(listener);
    }

    public void setOnClickCloseListener(View.OnClickListener listener){
        buttonClose.setOnClickListener(listener);
    }

}
