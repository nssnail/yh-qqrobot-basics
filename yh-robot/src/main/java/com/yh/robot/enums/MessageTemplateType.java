package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月06日 16:29:00
 */
@Getter
@AllArgsConstructor
public enum  MessageTemplateType {

    CUSTOM(0,"自定义"),

    TEXT(1, "普通文本"),

    COUNTDOWN(2,"倒计时"),

    API(3,"外部api")

    ;
    private final int type;

    private final String name;

    public static MessageTemplateType getType(Integer type) {
        return Arrays.stream(values()).filter(p -> p.type == type).
                findFirst().
                orElse(null);
    }
}