package com.breez.shorturl.entity.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class PassForm implements Serializable {
    //老密码
    @NotEmpty(message = "原密码不能为空")
    private String oldPass;
    //新密码
    @NotEmpty(message = "新密码不能为空")
    private String newPass;
}
