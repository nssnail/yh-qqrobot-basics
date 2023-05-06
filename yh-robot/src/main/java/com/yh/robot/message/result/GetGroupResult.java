package com.yh.robot.message.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yh.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月30日 10:12:00
 */
@Data
public class GetGroupResult {

    private Integer retcode;

    private String message;

    private String status;

    private List<GetGroupResult.Group> data;

    @Data
    public static class Group{
        @JsonProperty("group_id")
        private Long groupId;
        /** 群名称 */
        @JsonProperty("group_name")
        private String groupName;
        /** 群备注 */
        @JsonProperty("group_memo")
        private String groupMemo;
        /** 群创建时间 */
        @JsonProperty("group_create_time")
        private Date groupCreateTime;
        @JsonProperty("group_level")
        private String groupLevel;
        @JsonProperty("member_count")
        private Integer memberCount;
        @JsonProperty("max_member_count")
        private Integer maxMemberCount;
    }
}