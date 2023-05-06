package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月31日 16:42:00
 */
@Getter
@AllArgsConstructor
public enum RelType {

    TEXT(0, "文本"),

    IMAGE(1, "图片");

    private final int type;

    private final String name;

}