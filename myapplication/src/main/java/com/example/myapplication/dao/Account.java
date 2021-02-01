package com.example.myapplication.dao;

import java.io.Serializable;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 30日 23时 49分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */

public class Account implements Serializable {

    private String name;

    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
