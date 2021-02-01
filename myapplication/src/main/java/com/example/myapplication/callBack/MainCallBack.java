package com.example.myapplication.callBack;

import com.example.myapplication.dao.Account;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 30日 23时 58分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface MainCallBack {

    void Success(Account account);

    void Failure();

}
