package com.breez.shorturl.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 通用会员等级枚举
 * @author BreezAm
 */
@Getter
public enum LevelEnum {
    ORDINARY(1, "普通会员"),
    PRIMARY(2, "初级会员"),
    SENIOR(3, "高级会员");

    LevelEnum(int level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    @EnumValue
    private final int level;
    @JsonValue
    private final String desc;
}
