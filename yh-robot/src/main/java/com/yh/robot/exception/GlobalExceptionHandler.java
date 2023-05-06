package com.yh.robot.exception;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.client.RobotClient;
import com.yh.robot.constant.RobotMessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;


/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月24日 14:51:00
 */
//@ControllerAdvice
//@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private RobotClient robotClient;

    @Order(1)
    @ExceptionHandler(BotException.class)
    public void handleBotException(BotException e, HttpServletRequest request) {
        JSONObject requestBody = getRequestBody(request);
        if (requestBody.get("message_type").equals(RobotMessageConstant.MessageType.GROUP.getName())) {
            Object groupId = requestBody.get("group_id");
            if (ObjectUtil.isNotEmpty(groupId)) {
                robotClient.sendGroupMessage(e.getMessage(), Long.parseLong(groupId.toString()));
            }
        } else if (requestBody.get("message_type").equals(RobotMessageConstant.MessageType.PRIVATE.getName())) {
            Object userId = requestBody.get("user_id");
            if (ObjectUtil.isNotEmpty(userId)) {
                robotClient.sendPrivateMessage(e.getMessage(), Long.parseLong(userId.toString()));
            }
        }
    }

    @ExceptionHandler(Exception.class)
    @Order(2)
    public void handleException(BotException e, HttpServletRequest request) {
        JSONObject requestBody = getRequestBody(request);
        Object groupId = requestBody.get("group_id");
        if (ObjectUtil.isNotEmpty(groupId)) {
            robotClient.sendGroupMessage("系统异常", Long.parseLong(groupId.toString()));
        }
    }

    public JSONObject getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        JSONObject jsonObject = null;
        try {
            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            jsonObject = JSONObject.parseObject(requestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}