package com.yh.robot.message.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMessage extends RealMessage{

    @JsonProperty("group_id")
    private Long groupId;
    @JsonProperty("sender")
    private GroupSender sender;

    @Data
    public static class GroupSender{
        private int age;
        private String area;
        private String card;
        private String level;
        private String nickname;
        private String role;
        private String sex;
        private String title;
        @JsonProperty("user_id")
        private long userId;
    }
}