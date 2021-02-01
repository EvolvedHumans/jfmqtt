package com.example.qqmenu;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 07日 15时 16分
 * @Data： 对象序列化
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
@NoArgsConstructor
public class Bean implements Serializable {

    //不让它序列化
    transient String name;

    Integer age;

}
