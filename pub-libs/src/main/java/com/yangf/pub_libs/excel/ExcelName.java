package com.yangf.pub_libs.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EXCEL表注解
 *
 * 定义Excel表的表名、表头
 *
 * @Author : 杨帆
 * @Date : 2019年06月10日 11:37
 */
//用于描述类、接口（包括注解类型）或enum声明
@Target({ElementType.TYPE,ElementType.LOCAL_VARIABLE,ElementType.FIELD})
//定义该注解被保留时间长短（即生命周期）
@Retention(RetentionPolicy.RUNTIME)
//生成的javadoc文档时，注解是否被记录
@Documented
public @interface ExcelName {
    //Excel表，表名
    String value();
}
