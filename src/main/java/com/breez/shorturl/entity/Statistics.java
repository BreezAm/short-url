package com.breez.shorturl.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Statistics implements Serializable {
    /**
     * 组数
     */
    private Long groupCount;
    /**
     * 短链总数
     */
    private Long shortUrlCount;
    /**
     * 今日创建短链数
     */
    private Long shortUrlDayCount;
    /**
     * 总的访问量
     */
    private Long totalViewCount;

}
