package com.example.excel;

import com.yangf.pub_libs.util.ExcelHeaderName;
import com.yangf.pub_libs.util.ExcelName;

import java.io.Serializable;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 11日 14时 42分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@ExcelName("表名")
public class Nihao implements Serializable {
    @ExcelHeaderName(id = 0,name = "你好")
    public String oknsd;

    @ExcelHeaderName(id = 1,name = "表头1")
    public String name;

    @ExcelHeaderName(id = 2,name = "表头2")
    public String age;
}
