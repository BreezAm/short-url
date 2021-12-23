package com.breez.shorturl.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(20000, "成功"),
    FAIL(20001, "失败"),
    UNAUTHORIZED(40001, "未认证"),
    NOT_EXIST(20003, "用户不存在"),
    AUTH_FAILED(20004, "用户密码错误"),
    USER_DISABLE(20005, "用户被禁用"),
    GROUP_EXIST(20006, "组名称不可用"),
    EXIST(20007, "用户已经存在"),
    GROUP_LEAST_ONE(20008, "至少保留一个分组"),
    OLD_PASS_ERROR(20009, "原密码错误"),
    OLD_NEW_PASS_SAME(20010, "原密码和新密码相同"),
    GROUP_NAME_OVER(20011, "组名字超过最大长度"),
    EMAIL_SEND_ERROR(20012, "邮件验证码发送失败"),
    EMAIL_SEND_SUCCESS(20013, "邮件验证码发送成功"),
    EMAIL_CHECK_ERROR(20014, "邮件验证码不正确"),
    MINIO_UPLOAD_ERROR(20015, "上传文件到minio服务器失败");
    private Integer code;
    private String msg;

    private ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
