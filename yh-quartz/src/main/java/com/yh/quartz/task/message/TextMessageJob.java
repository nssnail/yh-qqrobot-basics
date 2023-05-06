package com.yh.quartz.task.message;

import com.alibaba.fastjson.JSONObject;
import com.yh.quartz.annotation.MessageJob;
import com.yh.robot.domain.RobotMessageTemplate;
import com.yh.robot.enums.MessageTemplateType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.enums.YesNoEnum;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.params.MessageTemplateConfig;
import com.yh.robot.third.holidayapi.HolidayApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@MessageJob(value = "TextMessageJob", type = MessageTemplateType.TEXT)
public class TextMessageJob extends AbstractMessageJob {
    @Resource
    private HolidayApi holidayApi;

    @Override
    public List<BusinessInvokeVo> getData(Long jobId) {
        List<BusinessInvokeVo> msg=new ArrayList<>();
        RobotMessageTemplate template = getTemplate(jobId);
        String config = template.getConfig();
        MessageTemplateConfig templateConfig= JSONObject.parseObject(config,MessageTemplateConfig.class);
        if (templateConfig.getIsHoliday()==YesNoEnum.YES.getType()) {
            if(!holidayApi.isHoliday(new Date())){
                msg.add(buildInvokeVo(Collections.singletonList(template.getContent()),SendMsgType.TEXT));
            }
        }else {
            msg.add(buildInvokeVo(Collections.singletonList(template.getContent()),SendMsgType.TEXT));
        }
        return msg;
    }
}