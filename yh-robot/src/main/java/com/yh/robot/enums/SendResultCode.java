package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 10:55:00
 */
@Getter
@AllArgsConstructor
public enum SendResultCode {

    SUCCESS(0,"成功"),

    FAIL(1,"失败");

    private final int type;

    private final String name;
}