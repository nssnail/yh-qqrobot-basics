package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 14:51:00
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "成功"),

    ERROR(500, "失败");

    private final int type;

    private final String name;

}