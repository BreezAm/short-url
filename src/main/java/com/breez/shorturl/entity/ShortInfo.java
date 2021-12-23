package com.breez.shorturl.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 短网址实体
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Data
@TableName("su_short_info")
@ApiModel(value = "ShortInfo对象", description = "")
public class ShortInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("短链接标题")
    private String title;

    @ApiModelProperty("原网址链接")
    private String original;

    @ApiModelProperty("短链接地址")
    private String shortUrl;


    @ApiModelProperty("短链接访问二维码")
    private String qrCode;
    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("状态：1可用；0不可用")
    private Integer status;

    @ApiModelProperty("逻辑删除：1删除；0未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("所属分组")
    private String groupId;
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
