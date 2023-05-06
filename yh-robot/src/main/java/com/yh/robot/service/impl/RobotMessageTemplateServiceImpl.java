package com.yh.robot.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yh.common.utils.DateUtils;
import com.yh.robot.params.MessageTemplateConfig;
import com.yh.robot.params.MessageTemplateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotMessageTemplateMapper;
import com.yh.robot.domain.RobotMessageTemplate;
import com.yh.robot.service.RobotMessageTemplateService;


/**
 * 消息模板Service业务层处理
 *
 * @author nssnail
 * @date 2023-04-06
 */
@Service
public class RobotMessageTemplateServiceImpl extends ServiceImpl<RobotMessageTemplateMapper, RobotMessageTemplate> implements RobotMessageTemplateService {

    @Autowired
    private RobotMessageTemplateMapper robotMessageTemplateMapper;

    /**
     * 查询消息模板
     *
     * @param id 消息模板主键
     * @return 消息模板
     */
    @Override
    public RobotMessageTemplate selectRobotMessageTemplateById(Long id) {
        return robotMessageTemplateMapper.selectRobotMessageTemplateById(id);
    }

    /**
     * 查询消息模板列表
     *
     * @param robotMessageTemplate 消息模板
     * @return 消息模板
     */
    @Override
    public List<RobotMessageTemplate> selectRobotMessageTemplateList(RobotMessageTemplate robotMessageTemplate) {
        return robotMessageTemplateMapper.selectRobotMessageTemplateList(robotMessageTemplate);
    }

    /**
     * 新增消息模板
     *
     * @param messageTemplateParams 消息模板
     * @return 结果
     */
    @Override
    public int insertRobotMessageTemplate(MessageTemplateParams messageTemplateParams) {
        MessageTemplateConfig templateConfig = messageTemplateParams.getTemplateConfig();
        messageTemplateParams.setConfig(JSONObject.toJSONString(templateConfig));
        messageTemplateParams.setCreateTime(DateUtils.getNowDate());
        return robotMessageTemplateMapper.insertRobotMessageTemplate(messageTemplateParams);
    }

    /**
     * 修改消息模板
     *
     * @param messageTemplateParams 消息模板
     * @return 结果
     */
    @Override
    public int updateRobotMessageTemplate(MessageTemplateParams messageTemplateParams) {
        MessageTemplateConfig templateConfig = messageTemplateParams.getTemplateConfig();
        messageTemplateParams.setConfig(JSONObject.toJSONString(templateConfig));
        messageTemplateParams.setUpdateTime(DateUtils.getNowDate());
        return robotMessageTemplateMapper.updateRobotMessageTemplate(messageTemplateParams);
    }

    /**
     * 批量删除消息模板
     *
     * @param ids 需要删除的消息模板主键
     * @return 结果
     */
    @Override
    public int deleteRobotMessageTemplateByIds(Long[] ids) {
        return robotMessageTemplateMapper.deleteRobotMessageTemplateByIds(ids);
    }

    /**
     * 删除消息模板信息
     *
     * @param id 消息模板主键
     * @return 结果
     */
    @Override
    public int deleteRobotMessageTemplateById(Long id) {
        return robotMessageTemplateMapper.deleteRobotMessageTemplateById(id);
    }
}
