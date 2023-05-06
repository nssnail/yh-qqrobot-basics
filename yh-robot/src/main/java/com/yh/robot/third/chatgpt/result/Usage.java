package com.yh.robot.third.chatgpt.result;

import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月03日 09:29:00
 */
@Data
public class Usage {

    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}