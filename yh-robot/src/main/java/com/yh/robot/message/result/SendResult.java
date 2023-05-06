package com.yh.robot.message.result;

import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 10:54:00
 */
@Data
public class SendResult {

    private String status;
    private Integer retcode;
    private String msg;
    private String wording;
}