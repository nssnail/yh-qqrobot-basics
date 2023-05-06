package com.yh.robot.domain;

import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 15:50:00
 */
@Data
public class RobotWord {

    private Integer id;

    private String content;

    private String phonetic;

    private String announce;

    private String wordtype;

    private String explaination;
}