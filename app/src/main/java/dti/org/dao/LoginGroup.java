package dti.org.dao;

import org.litepal.crud.LitePalSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 03日 14时 24分
 * @Data：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginGroup extends LitePalSupport {
    private Login user; //用户信息
    private String token; //登录令牌
}
