package com.example.myapplication.presenter;

import com.example.myapplication.callBack.MainCallBack;
import com.example.myapplication.dao.Account;
import com.example.myapplication.model.MainModel;
import com.example.myapplication.view.MainView;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 30日 23时 57分
 * @Data： 中间层，可以与view、model进行交互
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class MainPresenter {

    MainView mainView;
    MainModel mainMode;

    public MainPresenter(MainView mainView){
        this.mainView = mainView;
        mainMode = new MainModel();
    }

    //点击按钮查询功能
    public void getData(final String data) {
        mainMode.getAccountData(data, new MainCallBack() {
            @Override
            public void Success(Account account) {
                mainView.showSuccessPage(account);
            }

            @Override
            public void Failure() {
                mainView.showFailedPage();
            }
        });
    }
}
