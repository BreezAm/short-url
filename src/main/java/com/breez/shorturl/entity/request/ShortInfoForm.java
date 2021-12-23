package com.breez.shorturl.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class ShortInfoForm implements Serializable {
    @ApiModelProperty("短链接标题")
    @NotEmpty(message = "短链接标题不能为空")
    private String title;

    @ApiModelProperty("原网址链接")
    @NotEmpty(message = "原网址链接不能为空")
    private String url;

    @ApiModelProperty("分组")
    @NotEmpty(message = "分组ID不能为空")
    private String groupId;
}
