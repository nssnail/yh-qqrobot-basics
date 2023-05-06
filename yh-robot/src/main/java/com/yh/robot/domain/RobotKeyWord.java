package com.yh.robot.domain;

import com.yh.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName RobotKeyWord
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 22:29
 * @Version 1.0
 */
@Data
public class RobotKeyWord {

    private Integer id;

    private String keyWord;

    private Integer sortNums;

    private Integer isRandom;

    private String remake;

    private String beanName;

    private String method;

    private Long HandleId;

    private Integer HandleType;

    private Integer state;

    private Integer sendMsgType;

    private Date createTime;

    private Date updateTime;
}
