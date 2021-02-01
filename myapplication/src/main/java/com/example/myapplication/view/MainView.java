package com.example.myapplication.view;

import com.example.myapplication.dao.Account;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 30日 23时 48分
 * @Data： MainActvity中的UI操作
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface MainView {

    //文本框内容查询
    String getUserInput();

    //文本框内容查询成功
    void showSuccessPage(Account account);

    //文本框内容查询失败
    void showFailedPage();

}
