package com.yh.robot.third.chatgpt.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月03日 09:28:00
 */
@Data
public class GPTResult {

    private String id;
    private String object;
    private Long created;
    private List<Choices> choices;
    private Usage usage;
}