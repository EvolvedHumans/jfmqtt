package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.myapplication.dao.Account;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.presenter.MainPresenter;
import com.example.myapplication.view.MainView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements MainView{

    //中间层
    MainPresenter mainPresenter;

    //在Activity中引入databinding布局
    ActivityMainBinding mainBinding;

    int i = 0;

    public void init(){
        //ActivityDemoBinding
        //当布局转化为databinding布局后，需要在activity中修改布局文件
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void onClick(View v) {
        Log.e("1","1");
        mainPresenter.getData(this.getUserInput());
    }

    //用户点击按钮时回调，用于获取输入框的值
    @Override
    public String getUserInput() {
        Log.e("1","1");
        return mainBinding.edit.getText().toString();
    }

    //展示成功
    @SuppressLint("SetTextI18n")
    @Override
    public void showSuccessPage(Account account) {
       account.setLevel(account.getLevel()+ ++i);
       mainBinding.setAccount(account);
    }

    //展示失败
    @Override
    public void showFailedPage() {
        mainBinding.text.setText("就这");
    }
}
