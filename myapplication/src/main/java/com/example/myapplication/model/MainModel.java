package com.example.myapplication.model;

import com.example.myapplication.callBack.MainCallBack;
import com.example.myapplication.dao.Account;

import java.util.Random;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 30日 23时 57分
 * @Data： 业务逻辑，实现查询
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class MainModel {

    //查询用户数据
    public void getAccountData(String accountName, MainCallBack mainCallBack){

        Random random = new Random();
        boolean bool = random.nextBoolean();

        //成功
        if(bool){
            Account account = new Account();
            account.setName(accountName);
            account.setLevel(100);
            mainCallBack.Success(account);
        }

        else {
            mainCallBack.Failure();
        }

    }

}
