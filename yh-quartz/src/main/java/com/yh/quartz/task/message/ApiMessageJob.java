package com.yh.quartz.task.message;

import com.alibaba.fastjson.JSONObject;
import com.yh.quartz.annotation.MessageJob;
import com.yh.robot.domain.RobotHandler;
import com.yh.robot.domain.RobotMessageTemplate;
import com.yh.robot.enums.MessageTemplateType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.params.MessageTemplateConfig;
import com.yh.robot.service.RobotHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 10:04:00
 */
@MessageJob(value = "ApiMessageJob", type = MessageTemplateType.API)
@Slf4j
public class ApiMessageJob extends AbstractMessageJob {

    @Resource
    private RobotHandlerService robotHandlerService;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public List<BusinessInvokeVo> getData(Long jobId) {
        RobotMessageTemplate template = getTemplate(jobId);
        String config = template.getConfig();
        MessageTemplateConfig templateConfig = JSONObject.parseObject(config, MessageTemplateConfig.class);
        Long handleId = templateConfig.getHandleId();
        RobotHandler robotHandler = robotHandlerService.getById(handleId);
        RobotBusinessHandler bean = (RobotBusinessHandler) applicationContext.getBean(robotHandler.getBeanName());
        List<String> handle = bean.handle(new MessageVo(null, templateConfig.getParam(), null, null));
        return Collections.singletonList(buildInvokeVo(handle, SendMsgType.getMessageType(robotHandler.getSendMsgType())));
    }
}