package com.yh.robot.message.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author nssnail
 * @Description 获取好友列表返回数据
 * @createTime 2023年03月29日 16:16:00
 */
@Data
public class GetFriendListResult {

    private Integer retcode;

    private String message;

    private String status;

    private List<Friends> data;

    @Data
    public static class Friends{
        @JsonProperty("user_id")
        private Long userId;

        private String nickname;

        private String remark;
    }
}