package com.breez.shorturl.util;

public class UUID {

    public static synchronized String fastUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static synchronized String uuid(int length){
        return java.util.UUID.randomUUID().toString().substring(0,length).replaceAll("-", "");
    }
}
