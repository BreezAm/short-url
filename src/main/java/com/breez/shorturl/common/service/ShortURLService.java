package com.breez.shorturl.common.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * 短链生成服务
 *
 * @author BreezAm
 */
@Service
@SuppressWarnings("all")
public class ShortURLService {
    /**
     * 预计要放入的值数量
     */

    private Integer size=10_0000;
    /**
     * 期望的误判率
     */
    private Double error=0.01;
    /**
     * 实例化布隆过滤器
     */
    private BloomFilter<String> shortUrlCodeFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, error);

    /**
     * 放入短网址编码
     *
     * @param code
     */
    public void put(String code) {
        shortUrlCodeFilter.put(code);
    }

    /**
     * 判断布隆过滤器是否包含此元素
     *
     * @param code 短网址编码
     * @return
     */
    public boolean contain(String code) {
        return shortUrlCodeFilter.mightContain(code);
    }
}
