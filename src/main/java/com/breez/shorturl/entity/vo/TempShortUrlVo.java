package com.breez.shorturl.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TempShortUrlVo implements Serializable {
    /**
     * 断网链接
     */
    private String url;
    /**
     * 生成的二维码
     */
    private String qr;
    /**
     * 过期时间
     */
    private Long expireTime;
}
