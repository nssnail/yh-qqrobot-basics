package com.yh.robot.third.youdao.result;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Basic
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 10:48
 * @Version 1.0
 */
@Data
public class Basic {

    private List<String> exam_type;

    private String usPhonetic;

    private String phonetic;

    private String ukPhonetic;

    private String ukSpeech;

    private List<String> explains;

    private String usSpeech;
}
