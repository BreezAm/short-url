package com.breez.shorturl.common.service;

public interface IFileService {
    /**
     * 上传短链接二维码
     * @param data

     * @param code
     */
    public String uploadQR(String data,  String code,String userType);
}
