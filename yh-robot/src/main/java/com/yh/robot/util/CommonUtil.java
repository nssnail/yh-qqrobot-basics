package com.yh.robot.util;


import cn.hutool.core.util.ObjectUtil;
import com.yh.robot.annotation.SendMsg;
import com.yh.robot.client.RobotClient;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.third.chatgpt.result.GPTRole;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 13:24:00
 */
@Slf4j
public class CommonUtil {

    /**
     * 判断是否是英文
     *
     * @param p
     * @return
     */
    public static boolean isEnglish(String p) {
        byte[] bytes = p.getBytes();
        int i = bytes.length;//i为字节长度
        int j = p.length();//j为字符长度
        return i == j;
    }


    public static GPTRole build(String content) {
        GPTRole role = new GPTRole();
        role.setRole("user");
        role.setContent(content);
        return role;
    }

    public static String replacePlaceholder(String targetStr, String placeholder, String replaceStr) {
        String pattern = "\\$\\{" + placeholder + "}";
        return targetStr.replaceAll(pattern, replaceStr);
    }


    public static Method getRobotClientMethod(BusinessInvokeVo invokeVo) {
        Class<? extends RobotClient> clazz = RobotClient.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            SendMsg annotation = method.getAnnotation(SendMsg.class);
            if (ObjectUtil.isNotEmpty(annotation)
                    && annotation.type().equals(invokeVo.getType())
                    && annotation.messageType().equals(invokeVo.getMessageType())) {
                return method;
            }
        }
        return null;
    }

    public static void invokeRobotSend(Method method,RobotClient robotClient,BusinessInvokeVo invoke,Long sendId){
        try {
            if (ObjectUtil.isNotEmpty(method)) {
                List<String> msg = invoke.getMsg();
                for (String s : msg) {
                    method.invoke(robotClient, s, sendId);
                }
            }
        } catch (Exception e) {
            log.error("方法执行异常", e);
            throw BotException.error("方法执行异常");
        }
    }
}