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
 * @name： 杨帆
 * @Time： 2021年 01月 21日 13时 04分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class ExportDialog extends Dialog {
    //标题
    TextView titleTextView;
    //内容栏
    TextView contentTextView;
    //关闭按钮
    Button button;
    //横向宽度
    int width = 100;

    public ExportDialog(@NonNull Context context) {
        super(context);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.export_dialog, null);
        setCancelable(false); //设置点击失效，
        setContentView(view);

        Window window = getWindow();
        assert window != null;
        window.setLayout(DensityUtil.getScreenWidth() - width, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.touming);
        titleTextView = window.findViewById(R.id.nb_title);
        contentTextView = window.findViewById(R.id.nb_content);
        button = window.findViewById(R.id.nb_button);
    }

    /**
     * @param title Dialog 标题
     */
    public void setTitle(@NonNull String title) {
        this.titleTextView.setText(title);
    }

    /**
     * @param content Dialog 内容
     */
    public void setContent(@NonNull String content) {
        this.contentTextView.setText(content);
    }

    /**
     * @param listener 按钮
     */
    public void setOnClickListener(View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

}
