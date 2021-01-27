package dti.org.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import dti.org.R;

import com.yangf.pub_libs.image.rotate.RotateImageView;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 11日 19时 25分
 * @Data： 加载视图窗口
 *
 * bug：需要创建
 *
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class LoadDialog extends Dialog {

    RotateImageView rotateImageView;

    public LoadDialog(Context context) {
        super(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.load, null);
        setCancelable(false); //设置点击失效，
        setContentView(view);

        Window window = getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.touming);
        rotateImageView = window.findViewById(R.id.rotateImageView);
        rotateImageView.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            dismiss();
        }
        return false;
    }


}
