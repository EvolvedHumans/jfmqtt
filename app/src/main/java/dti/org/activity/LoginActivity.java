package dti.org.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.databinding.ActivityLoginBinding;
import dti.org.presenter.LoginPresenter;
import dti.org.views.LoginView;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 21时 38分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class LoginActivity extends BaseActivity implements LoginView {

    ActivityLoginBinding activityLoginBinding;

    //todo presenter层,API接口
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        loginPresenter.firstLogin();
        activityLoginBinding.button.setOnClickListener(v -> {
            //todo 登录接口
            loginPresenter.login();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public String getUserName() {
        return activityLoginBinding.user.getText().toString();
    }

    @Override
    public String getPassword() {
        return activityLoginBinding.passwordToggle.getText().toString();
    }

    @Override
    public void setUserTips(String userTips) {
        activityLoginBinding.userTips.setText(userTips);
    }

    @Override
    public void setPasswordTips(String passwordTips) {
        activityLoginBinding.passwordToggleTips.setText(passwordTips);
    }

    @Override
    public void jump() {
        Intent intent = new Intent(this,ChoiceActivity.class);
        startActivity(intent);
        finish();
    }
}
