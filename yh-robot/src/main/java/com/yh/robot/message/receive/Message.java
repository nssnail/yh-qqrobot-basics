package com.yh.robot.message.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月21日 17:21:00
 */
@Data
public class Message {

    @JsonProperty("self_id")
    private Long selfId;
    @JsonProperty("message_type")
    private String messageType;
    @JsonProperty("sub_type")
    private String subType;
    @JsonProperty("message_id")
    private Integer messageId;
    @JsonProperty("group_id")
    private Long groupId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("post_type")
    private String postType;
    @JsonProperty("time")
    private Long time;

}