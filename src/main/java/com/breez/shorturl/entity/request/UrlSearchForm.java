package com.breez.shorturl.entity.request;

import lombok.Data;

import java.io.Serializable;
/**
 * 短网址搜索请求
 * @author BreezAm
 */
@Data
public class UrlSearchForm implements Serializable {
    /**
     * 短网址标题
     */
    private String title;
    /**
     * 分组
     */
    private String groupId;
}
