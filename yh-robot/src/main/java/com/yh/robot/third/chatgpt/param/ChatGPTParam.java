package com.yh.robot.third.chatgpt.param;

import com.yh.robot.third.chatgpt.result.GPTRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 17:39:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTParam {

    private String model;
    private Double temperature;
    private List<GPTRole> messages;
}