package com.yh.robot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 15:17:00
 */
@Getter
@AllArgsConstructor
public enum  BusinessHandleType {

    SIMPLE_TEXT(0,"简单文本"),
    ROW_TEXT(1,"多文本随机"),
    IMAGE(2,"图片文本"),
    API(3,"外部API"),
    ;

    private final int type;

    private final String name;

}