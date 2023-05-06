package com.yh.robot.message.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 10:09:00
 */
@Data
public class RealMessage extends Message{

    @JsonProperty("raw_message")
    private String rawMessage;
    @JsonProperty("message")
    private String message;
    @JsonProperty("message_seq")
    private Integer messageSeq;
    @JsonProperty("font")
    private Integer font;
}