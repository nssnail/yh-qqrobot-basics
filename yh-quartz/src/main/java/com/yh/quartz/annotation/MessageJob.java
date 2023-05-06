package com.yh.quartz.annotation;

import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.MessageTemplateType;
import com.yh.robot.enums.SendMsgType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月07日 09:56:00
 */
@Service
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageJob {

    @AliasFor(annotation = Service.class)
    String value() default "";

    MessageTemplateType type();

}
