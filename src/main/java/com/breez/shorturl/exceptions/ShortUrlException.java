package com.breez.shorturl.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlException extends RuntimeException {
    private Integer code;
    private String msg;
}
