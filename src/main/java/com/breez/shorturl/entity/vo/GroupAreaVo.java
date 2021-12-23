package com.breez.shorturl.entity.vo;

import lombok.Data;

import java.io.Serializable;
/**
 * 组饼图视图
 * @author BreezAm
 */
@Data
public class GroupAreaVo implements Serializable {
    private String name;
    private Integer value;
}
