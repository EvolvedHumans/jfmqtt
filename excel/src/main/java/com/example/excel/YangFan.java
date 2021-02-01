package com.example.excel;

import androidx.annotation.NonNull;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 10日 12时 55分
 * @Data： 使用Person注解
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
//注解放在类上
@Person(name = "大佬救命", age = 11, sex = true)
public class YangFan {

    public String name;
    public Integer age;
    public Boolean sex;


    //放在方法上的注解
    @Person(name = "名字", age = 11, sex = true)
    public String getName() {
        return name;
    }

    @Person(name = "年龄", age = 11, sex = true)
    public Integer getAge() {
        return age;
    }

    @Person(name = "性别", age = 11, sex = true)
    public Boolean getSex() {
        return sex;
    }

    @NonNull
    @Override
    public String toString() {
        return name+":"+age+","+sex;
    }
}
