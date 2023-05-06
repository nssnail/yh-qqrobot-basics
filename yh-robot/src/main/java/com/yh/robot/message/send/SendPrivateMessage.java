package com.yh.robot.message.send;

import com.alibaba.fastjson.annotation.JSONField;
import com.yh.robot.constant.SendApiConstant;
import lombok.Data;

/**
 * @ClassName SendPrivateMessage
 * @Description TODO
 * @Author Y
 * @Date 2023/3/26 14:04
 * @Version 1.0
 */
@Data
public class SendPrivateMessage extends SendMessage {

    private final Param param = new Param();

    public SendPrivateMessage(Long groupId, String msg) {
        param.setUserId(groupId);
        param.setMessage(msg);
        param.setAutoEscape(false);
    }

    public SendPrivateMessage(Long groupId, String msg, boolean autoEscape) {
        param.setUserId(groupId);
        param.setMessage(msg);
        param.setAutoEscape(autoEscape);
    }


    @Override
    public String getAction() {
        return SendApiConstant.Private.SEND_PRIVATE_MSG;
    }

    @Override
    public Object getParams() {
        return getParam();
    }

    @Data
    public static class Param {
        @JSONField(name = "user_id")
        private Long userId;
        @JSONField(name = "message")
        private String message;
        @JSONField(name = "auto_escape")
        private boolean autoEscape;
    }
}