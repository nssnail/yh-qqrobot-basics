package com.yh.robot.message.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 14:27:00
 */
@Data
public class RequestMessage extends Message{

    @JsonProperty("request_type")
    private String requestType;

    private String comment;

    private String flag;

    @JsonProperty("group_id")
    private Long groupId;
}