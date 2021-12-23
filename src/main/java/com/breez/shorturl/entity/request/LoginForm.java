package com.breez.shorturl.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class LoginForm implements Serializable {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
}
