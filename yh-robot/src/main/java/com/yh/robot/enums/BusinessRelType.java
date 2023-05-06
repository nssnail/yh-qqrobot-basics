package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 11:38:00
 */
@Getter
@AllArgsConstructor
public enum BusinessRelType {

    KEY_WORD(1, "关键字"),

    JOB(2,"定时任务")

    ;
    private final int type;

    private final String name;

}