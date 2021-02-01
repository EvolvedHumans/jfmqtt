package view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iosdialog.R;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 15日 22时 35分
 * @Data： 自定义确认退出对话栏
 * @TechnicalPoints： 对话栏中包含一个TextView文本框，两个按钮，一个退出，一个确认，资源名固定。
 *                    layout 布局: mdialog_layout
 *                    TextView文本框: mdialog_text
 *                    确认按钮: mdialog_confirm_bt
 *                    退出按钮: mdialog_exit_bt
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class Mdialog extends Dialog {

    //显示标题文本框
    TextView textView;

    //确认按钮
    Button button_confirm;

    //退出按钮
    Button button_exit;

    public Mdialog(Context context){
        super(context);
        //引用布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.mdialog_layout,null);

        //获取显示标题的文本框
        textView = view.findViewById(R.id.mdialog_text);

        //获取确认按钮
        button_confirm = view.findViewById(R.id.mdialog_confirm_bt);
        button_confirm.setText("确认");

        //获取退出按钮
        button_exit = view.findViewById(R.id.mdialog_exit_bt);
        button_exit.setText("退出");

        //显示Dialog
        setContentView(view);
    }

    //设置显示文字
    public void setTextView(String content){
        textView.setText(content);
    }

    //"取消"按钮的监听方法
    public void setOnCancalListener(View.OnClickListener listener){
        button_exit.setOnClickListener(listener);
    }

    //"确认"按钮的监听方法
    public void setOnExitListener(View.OnClickListener listener){
        button_confirm.setOnClickListener(listener);
    }

}


























