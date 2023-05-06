package com.yh.robot.annotation;


import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.enums.SendMsgType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 16:31:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SendMsg {

    RobotMessageConstant.MessageType messageType();

    SendMsgType type();
}
