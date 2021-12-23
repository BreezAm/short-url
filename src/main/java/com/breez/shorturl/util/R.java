package com.breez.shorturl.util;


import com.breez.shorturl.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class R<T> implements Serializable {

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;
    @Getter
    @Setter
    private boolean success;
    @Getter
    @Setter
    private T data;


    /*---------------------------------------------------------------------*/
    public static <T> R<T> ok() {
        return result(null, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), true);
    }

    public static <T> R<T> ok(T data) {
        return result(data, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), true);
    }

    public static <T> R<T> ok(T data, int code) {
        return result(data, code, ResponseCode.SUCCESS.getMsg(), true);
    }

    public static <T> R<T> ok(Integer code, String msg) {
        return result(null, code, msg, true);
    }

    public static <T> R<T> ok(T data, int code, String msg) {
        return result(data, code, msg, true);
    }

    /*--------------------------------------------------------------------*/
    public static <T> R<T> error() {
        return result(null, ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg(), false);
    }

    public static <T> R<T> error(String msg) {
        return result(null, ResponseCode.FAIL.getCode(), msg, false);
    }

    public static <T> R<T> error(int code, String msg) {
        return result(null, code, msg, false);
    }

    public static <T> R<T> result(T data, int code, String msg, boolean success) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        r.setSuccess(success);
        return r;
    }


}
