package com.yangf.pub_libs.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EXCEL表头注解
 *
 * 这里需要获取自定义注解的顺序，因此还需要添加一个id字段
 *
 * @Author : 杨帆
 * @Date : 2019年06月10日 11:37
 */
//用于类和成员变量
@Target({ElementType.TYPE,ElementType.LOCAL_VARIABLE,ElementType.FIELD})
//定义该注解被保留时间长短（即生命周期）
@Retention(RetentionPolicy.RUNTIME)
//生成javadoc文档
@Documented
public @interface ExcelHeaderName {
    int id();
    //Excel表，表头字段
    String name();
}
