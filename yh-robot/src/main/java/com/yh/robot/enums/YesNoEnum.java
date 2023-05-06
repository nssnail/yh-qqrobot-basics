package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月06日 10:17:00
 */
@AllArgsConstructor
@Getter
public enum YesNoEnum {

    NO(0, "否"),

    YES(1, "是");

    private final int type;

    private final String name;

}