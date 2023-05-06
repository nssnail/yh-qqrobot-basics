package com.yh.robot.message.receive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 09:30:00
 */
@Data
public class PrivateMessage  extends RealMessage{

    @JsonProperty("sender")
    private PrivateSender sender;

    @Data
    public static class PrivateSender{
        private int age;
        private String nickname;
        private String sex;
        @JsonProperty("user_id")
        private long userId;
    }

}