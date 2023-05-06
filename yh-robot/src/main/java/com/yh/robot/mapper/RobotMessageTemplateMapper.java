package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotMessageTemplate;

/**
 * 消息模板Mapper接口
 * 
 * @author nssnail
 * @date 2023-04-06
 */
@Mapper
public interface RobotMessageTemplateMapper extends BaseMapper<RobotMessageTemplate> {

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
    public int insertRobotMessageTemplate(RobotMessageTemplate robotMessageTemplate);

    /**
     * 修改消息模板
     * 
     * @param robotMessageTemplate 消息模板
     * @return 结果
     */
    public int updateRobotMessageTemplate(RobotMessageTemplate robotMessageTemplate);

    /**
     * 删除消息模板
     * 
     * @param id 消息模板主键
     * @return 结果
     */
    public int deleteRobotMessageTemplateById(Long id);

    /**
     * 批量删除消息模板
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotMessageTemplateByIds(Long[] ids);
}
