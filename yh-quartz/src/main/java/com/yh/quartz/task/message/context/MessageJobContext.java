package com.yh.quartz.task.message.context;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.yh.common.exception.ServiceException;
import com.yh.quartz.annotation.MessageJob;
import com.yh.quartz.task.message.AbstractMessageJob;
import com.yh.robot.enums.MessageTemplateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月07日 09:59:00
 */
@Component
@Slf4j
public class MessageJobContext {

    @Resource
    private List<AbstractMessageJob> jobs;

    private static final Map<MessageTemplateType, AbstractMessageJob> JOB_BANES = new HashMap<>();

    @PostConstruct
    public void init() {
        jobs.forEach(bean -> {
            MessageJob annotation = AopUtils.getTargetClass(bean).getAnnotation(MessageJob.class);
            if (annotation == null || annotation.type().equals(MessageTemplateType.CUSTOM)) {
                return;
            }
            JOB_BANES.put(annotation.type(), bean);
        });
    }

    public static AbstractMessageJob getMessageJob(MessageTemplateType templateType) {
        AbstractMessageJob messageJob = JOB_BANES.get(templateType);
        if(ObjectUtils.isEmpty(messageJob)){
            log.info("找不到对应的消息任务：{}",templateType);
            throw new ServiceException("系统异常");
        }
        return messageJob;
    }
}