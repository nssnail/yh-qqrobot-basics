package com.yh.robot.handler.business.vo;

import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.enums.SendMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 16:01:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInvokeVo {

    private List<String> msg;

    private SendMsgType type;

    private RobotMessageConstant.MessageType messageType;

    public BusinessInvokeVo(List<String> msg, SendMsgType type) {
        this.msg = msg;
        this.type = type;
    }
}