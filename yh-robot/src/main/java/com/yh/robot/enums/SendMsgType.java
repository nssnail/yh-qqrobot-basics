package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 16:02:00
 */
@Getter
@AllArgsConstructor
public enum SendMsgType {

    TEXT(0, "文本"),

    IMAGE(1, "图片"),

    VIDEO(2, "视频");

    private final int type;

    private final String name;

    public static SendMsgType getMessageType(int type) {
        return Arrays.stream(values()).filter(p -> p.type == type).
                findFirst().
                orElse(null);
    }
}