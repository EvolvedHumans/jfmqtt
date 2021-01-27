package dti.org.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.databinding.ActivityLoginoutBinding;
import dti.org.presenter.LoginoutPresenter;
import dti.org.views.LoginoutView;

/**
 * 退出跳转跳转到登录界面
 */
public class LoginOutActivity extends BaseActivity implements LoginoutView {

    ActivityLoginoutBinding activityLoginoutBinding;

    //todo presenter层，API接口
    LoginoutPresenter loginOutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        activityLoginoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_loginout);
        loginOutPresenter = new LoginoutPresenter();
        loginOutPresenter.attachView(this);
        //todo 绘制界面 drawLoginout
        loginOutPresenter.drawLoginout();
        activityLoginoutBinding.button.setOnClickListener(v -> {
            //todo 登出接口
            loginOutPresenter.loginout();
        });
        activityLoginoutBinding.imageIconReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this,ChoiceActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginOutPresenter.detachView();
    }


    /**
     *
     * 物理按键点击回调
     *
     * @param keyCode 按键编码
     * @param event 按键事件
     * @return 返回true表示不再响应系统动作，返回false表示继续响应系统动作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,ChoiceActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 继承loginView，改写为User文本框中数据
     *
     * @param userTips 设置用户区域提示内容
     */
    @Override
    public void setUserTips(String userTips) {
        activityLoginoutBinding.user.setText(userTips);
    }

    /**
     * 继承loginView，改写为Company文本框中数据
     *
     * @param passwordTips 设置密码区域提示内容
     */
    @Override
    public void setPasswordTips(String passwordTips) {
        activityLoginoutBinding.company.setText(passwordTips);
    }

    /**
     * 界面跳转
     */
    @Override
    public void jump() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getUserName() {
        return activityLoginoutBinding.user.getText().toString();
    }

    @Override
    public String getPassword() {
        return activityLoginoutBinding.company.getText().toString();
    }

}