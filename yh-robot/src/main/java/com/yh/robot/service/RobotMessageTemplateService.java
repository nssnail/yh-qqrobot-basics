package com.yh.robot.service;

import java.util.List;
import com.yh.robot.domain.RobotMessageTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.params.MessageTemplateParams;

/**
 * 消息模板Service接口
 * 
 * @author nssnail
 * @date 2023-04-06
 */
public interface RobotMessageTemplateService extends IService<RobotMessageTemplate> {

    /**
     * 查询消息模板
     * 
     * @param id 消息模板主键
     * @return 消息模板
     */
    public RobotMessageTemplate selectRobotMessageTemplateById(Long id);

    /**
     * 查询消息模板列表
     * 
     * @param robotMessageTemplate 消息模板
     * @return 消息模板集合
     */
    public List<RobotMessageTemplate> selectRobotMessageTemplateList(RobotMessageTemplate robotMessageTemplate);

    /**
     * 新增消息模板
     * 
     * @param robotMessageTemplate 消息模板
     * @return 结果
     */
    public int insertRobotMessageTemplate(MessageTemplateParams messageTemplateParams);

    /**
     * 修改消息模板
     * 
     * @param robotMessageTemplate 消息模板
     * @return 结果
     */
    public int updateRobotMessageTemplate(MessageTemplateParams messageTemplateParams);

    /**
     * 批量删除消息模板
     * 
     * @param ids 需要删除的消息模板主键集合
     * @return 结果
     */
    public int deleteRobotMessageTemplateByIds(Long[] ids);

    /**
     * 删除消息模板信息
     * 
     * @param id 消息模板主键
     * @return 结果
     */
    public int deleteRobotMessageTemplateById(Long id);
}
