package com.yh.robot.third.chatgpt.result;

import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月03日 09:29:00
 */
@Data
public class Choices {
    private int index;
    private GPTRole message;
    private String finish_reason;
}