package com.yh.robot.handler.message.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.constant.BeanNameConstant;
import com.yh.robot.handler.message.RobotMessageDispatcher;
import com.yh.robot.message.result.RobotResult;
import com.yh.robot.service.RobotSendService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月20日 14:53:00
 */
@Service(BeanNameConstant.PostTypeBean.MESSAGE)
public class MessageDispatcher implements RobotMessageDispatcher {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public RobotResult dispatcher(JSONObject jsonObject) {
        //可以直接使用，采用mq的方式
        RobotSendService robotSendService = applicationContext.getBean("NormalSendService", RobotSendService.class);
        robotSendService.send(jsonObject);
        return RobotResult.success();
    }
}