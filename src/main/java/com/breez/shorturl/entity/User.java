package com.breez.shorturl.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.*;
import com.breez.shorturl.entity.enums.LevelEnum;
import com.breez.shorturl.entity.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 会员实体
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Data
@ApiModel(value = "User对象", description = "")
@TableName("su_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("1:普通会员；2：初级会员；3：高级会员")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private LevelEnum level;

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("邮箱")
    @NotEmpty(message = "邮箱不能为空")
    @Email(regexp="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("积分（点数）")
    private Integer integral;

    @ApiModelProperty("账户余额")
    private BigDecimal balance;

    @ApiModelProperty("用户密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("1:可用；0：禁用")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    private StatusEnum status;

    @ApiModelProperty("逻辑删除：1已删除；0未删除")
    @TableLogic
    private Integer idDeleted;

    @ApiModelProperty("API请求密钥")
    private String apikey;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
