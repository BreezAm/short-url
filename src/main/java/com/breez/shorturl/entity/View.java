package com.breez.shorturl.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class View implements Serializable {
    private String days;
    private Integer count;
}
