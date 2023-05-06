package com.yh.robot.message.send;


import com.alibaba.fastjson.annotation.JSONField;
import com.yh.robot.constant.SendApiConstant;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 09:33:00
 */
@Data
public class SendGroupMessage extends SendMessage {

    private final Param param = new Param();

    public SendGroupMessage(Long groupId, String msg) {
        param.setGroupId(groupId);
        param.setMessage(msg);
        param.setAutoEscape(false);
    }

    public SendGroupMessage(Long groupId, String msg, boolean autoEscape) {
        param.setGroupId(groupId);
        param.setMessage(msg);
        param.setAutoEscape(autoEscape);
    }


    @Override
    public String getAction() {
        return SendApiConstant.Group.SEND_GROUP_MSG;
    }

    @Override
    public Object getParams() {
        return getParam();
    }

    @Data
    public static class Param {
        @JSONField(name = "group_id")
        private Long groupId;
        @JSONField(name = "message")
        private String message;
        @JSONField(name = "auto_escape")
        private boolean autoEscape;
    }

}