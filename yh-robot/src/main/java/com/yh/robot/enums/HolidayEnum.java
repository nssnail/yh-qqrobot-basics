package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月20日 11:44:00
 */
@AllArgsConstructor
@Getter
public enum HolidayEnum {

    DAY(0, "工作日"),

    WEEKEND(1, "周末"),

    HOLIDAY(2, "节假日");


    private final int type;

    private final String name;

    public static HolidayEnum getType(Integer type) {
        return Arrays.stream(values()).filter(p -> p.type == type).
        findFirst().
                orElse(null);
    }

}