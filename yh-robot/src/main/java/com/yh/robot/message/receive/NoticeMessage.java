package com.yh.robot.message.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 15:06:00
 */
@Data
public class NoticeMessage extends Message{

    @JsonProperty("notice_type")
    private String noticeType;

}