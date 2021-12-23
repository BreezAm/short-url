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
 *
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-19
 */
@Data
@TableName("su_analysis")
@ApiModel(value = "Analysis对象", description = "")
public class Analysis implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("访问的短链接ID")
    private String urlId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("次数")
    private Long frequency;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除：1为删除，0为正常")
    @TableLogic
    private Integer isDeleted;


}
