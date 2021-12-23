package com.breez.shorturl.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 短链分组实体
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Data
@ApiModel(value = "Group对象", description = "")
@TableName("su_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("所属用户ID")
    private String userId;
    @NotEmpty(message = "组名称不能为空")
    @ApiModelProperty("分组名字")
    private String name;

    @ApiModelProperty("逻辑删除：1已删除；0未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
