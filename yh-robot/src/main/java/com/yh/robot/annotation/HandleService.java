package com.yh.robot.annotation;

import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月31日 14:48:00
 */
@Service
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HandleService {

    @AliasFor(annotation = Service.class)
    String value() default "";

    String handleName();

    HandleType handleType();

    SendMsgType sendType();

    boolean isApi() default true;

}