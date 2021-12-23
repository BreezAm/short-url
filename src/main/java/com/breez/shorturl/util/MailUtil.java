package com.breez.shorturl.util;

public class MailUtil {

    public static String getPrefix(String email) {
        int i = email.indexOf("@");
        return email.substring(0, i);
    }
}
