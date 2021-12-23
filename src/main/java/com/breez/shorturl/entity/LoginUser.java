package com.breez.shorturl.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 令牌
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 登录时间
     */
    private Long loginTime;
}
