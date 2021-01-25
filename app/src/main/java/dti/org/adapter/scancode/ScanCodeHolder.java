package dti.org.adapter.scancode;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dti.org.R;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 17时 35分
 * @Data： 扫码按键Adapter所需的视图模型
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class ScanCodeHolder extends RecyclerView.ViewHolder implements ScanCodeView {

    Button button;
    TextView textView;
    LinearLayout linearLayout;

    public ScanCodeHolder(@NonNull View itemView) {
        super(itemView);
        this.button = itemView.findViewById(R.id.button_scan_code);
        this.textView = itemView.findViewById(R.id.text_scan_code);
        this.linearLayout = itemView.findViewById(R.id.list_item);
    }

    @Override
    public void setText(String text) {
        this.textView.setText(text);
    }

    @Override
    public void setLinearLayoutColor(Drawable drawable) {
        this.linearLayout.setBackground(drawable);
    }

    @Override
    public void setContent(String content) {
        this.button.setText(content);
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

}
