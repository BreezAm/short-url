package com.breez.shorturl.entity.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class Mail implements Serializable {
    private String from;
    private String to;
    private String text;
    private String subject;
}
