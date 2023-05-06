package com.yh.robot.third.chatgpt.result;

import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月24日 10:27:00
 */
@Data
public class ChatGPTResult {

    private Integer code;
    private String message;
    private Long timestamp;
    private String data;
}