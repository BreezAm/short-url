package com.breez.shorturl.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 通用状态枚举类
 * @author BreezAm
 */
@Getter
public enum StatusEnum {
    NORMAL(1, "正常"),
    DISABLE(0, "禁用")
    ;

    StatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    @EnumValue
    private final int status;
    @JsonValue
    private final String desc;
}
