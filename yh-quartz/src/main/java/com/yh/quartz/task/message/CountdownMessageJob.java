package com.yh.quartz.task.message;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.quartz.annotation.MessageJob;
import com.yh.robot.domain.RobotMessageTemplate;
import com.yh.robot.enums.MessageTemplateType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.params.MessageTemplateConfig;
import com.yh.robot.util.CommonUtil;
import com.yh.robot.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月07日 11:30:00
 */
@MessageJob(value = "CountdownMessageJob", type = MessageTemplateType.COUNTDOWN)
@Slf4j
public class CountdownMessageJob extends AbstractMessageJob {

    @Override
    public List<BusinessInvokeVo> getData(Long jobId) {
        List<BusinessInvokeVo> msg = new ArrayList<>();
        RobotMessageTemplate template = getTemplate(jobId);
        String config = template.getConfig();
        MessageTemplateConfig templateConfig = JSONObject.parseObject(config, MessageTemplateConfig.class);
        try {
            String now = cn.hutool.core.date.DateUtil.now();
            int day = DateUtils.daysBetween(now, templateConfig.getTime());
            String str = CommonUtil.replacePlaceholder(template.getContent(), "date", String.valueOf(day));
            str = CommonUtil.replacePlaceholder(str, "time", DateUtil.format(new Date(),"HH:mm:ss"));
            str = CommonUtil.replacePlaceholder(str, "datetime", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
            msg.add(buildInvokeVo(Collections.singletonList(str), SendMsgType.TEXT));
        } catch (Exception e) {
            log.error("日期解析异常", e);
        }
        return msg;
    }

}