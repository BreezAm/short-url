package com.breez.shorturl.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewVo implements Serializable {
    private String[] days;
    private Integer[] count;
}
