package com.breez.shorturl.constants;

import io.jsonwebtoken.Claims;

/**
 * 短链服务常量
 *
 * @author BreezAm
 */
public class Constants {
    public static final String USER_TYPE_TEMP = "tempUser";
    /**
     * 默认用户头像
     */
    public static final String DEFAULT_AVATAR = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

    /**
     * 默认用户注册赠送积分
     */
    public static final Integer DEFAULT_INTEGRAL = 1000;
    /**
     * 默认分组，用户注册后会自动生成一个分组
     */
    public static final String DEFAULT_GROUP = "默认分组";
    /**
     * 用户被禁用
     */
    public static final String DISABLE = "DISABLE";
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USER_ID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;
}
