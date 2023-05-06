package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description 处理类型
 * @createTime 2023年03月22日 11:14:00
 */
@Getter
@AllArgsConstructor
public enum HandleType {

    NOTHING(0, "不做操作"),

    REMOVE(1, "删除关键字"),

    SUB(2, "获取关键字后面的信息"),

    ENGLISH(3, "判断是否是英语");

    private final int type;

    private final String name;

}