package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月17日 11:37:00
 */
@AllArgsConstructor
@Getter
public enum StateEnum {

    DISABLE(0, "禁用"),

    NORMAL(1, "正常");

    private final int type;

    private final String name;

}