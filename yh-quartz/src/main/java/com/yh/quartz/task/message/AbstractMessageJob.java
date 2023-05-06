package com.yh.quartz.task.message;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.quartz.domain.SysJob;
import com.yh.quartz.mapper.SysJobMapper;
import com.yh.robot.client.RobotClient;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.domain.RobotMessageTemplate;
import com.yh.robot.enums.BusinessRelType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.enums.YesNoEnum;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.params.MessageTemplateConfig;
import com.yh.robot.service.RobotFriendsRelService;
import com.yh.robot.service.RobotGroupRelService;
import com.yh.robot.service.RobotMessageTemplateService;
import com.yh.robot.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月06日 10:32:00
 */
@Slf4j
public abstract class AbstractMessageJob {
    @Resource
    private RobotFriendsRelService robotFriendsRelService;
    @Resource
    private RobotGroupRelService robotGroupRelService;
    @Resource
    private RobotClient robotClient;
    @Resource
    protected SysJobMapper sysJobMapper;
    @Resource
    protected RobotMessageTemplateService robotMessageTemplateService;

    public abstract List<BusinessInvokeVo> getData(Long jobId);

    public void execute(Long jobId) {
        List<RobotFriendsRel> robotFriendsRels = robotFriendsRelService.getFriendsRelByType(BusinessRelType.JOB,jobId);
        List<RobotGroupRel> groupRelByType = robotGroupRelService.getGroupRelByType(BusinessRelType.JOB,jobId);
        if(CollUtil.isEmpty(robotFriendsRels)&&CollUtil.isEmpty(groupRelByType)){
            log.info("没有关联任何群组和好友，不执行任务");
            return;
        }
        List<BusinessInvokeVo> data = getData(jobId);
        log.info("[定时任务执行]数据获取：{}",JSONObject.toJSONString(data));
        if(CollUtil.isEmpty(data))return;
        robotFriendsRels.forEach(e -> {
            data.forEach(m->{
                m.setMessageType(RobotMessageConstant.MessageType.PRIVATE);
                Method method = CommonUtil.getRobotClientMethod(m);
                CommonUtil.invokeRobotSend(method,robotClient,m,e.getQq());
            });
        });
        groupRelByType.forEach(e->{
            data.forEach(m->{
                m.setMessageType(RobotMessageConstant.MessageType.GROUP);
                Method method = CommonUtil.getRobotClientMethod(m);
                CommonUtil.invokeRobotSend(method,robotClient,m,e.getGroupId());
            });
        });
    }

    protected RobotMessageTemplate getTemplate(Long jobId){
        SysJob sysJob = sysJobMapper.selectJobById(jobId);
        Assert.isTrue(sysJob.getIsMessage().equals(YesNoEnum.YES.getType()), "此任务不是消息类型");
        RobotMessageTemplate template = robotMessageTemplateService.getById(sysJob.getTemplateId());
        Assert.isTrue(ObjectUtil.isNotEmpty(template), "模板不能为空");
        return template;
    }

    protected BusinessInvokeVo buildInvokeVo(List<String> msg, SendMsgType type) {
        return new BusinessInvokeVo(msg, type, null);
    }
}