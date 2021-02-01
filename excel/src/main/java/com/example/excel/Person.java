package com.example.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用于描述类、接口（包括注解类型）或enum声明
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
//定义该注解被保留时间长短（即生命周期）
@Retention(RetentionPolicy.RUNTIME)
//生成的javadoc文档时，注解是否被记录
@Documented
public @interface Person {
    //姓名
    String name();

    //年龄
    int age();

    //性别
    boolean sex();

}
