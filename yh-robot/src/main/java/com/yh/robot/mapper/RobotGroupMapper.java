package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotGroup;

/**
 * 群列Mapper接口
 * 
 * @author nssnail
 * @date 2023-03-30
 */
@Mapper
public interface RobotGroupMapper extends BaseMapper<RobotGroup> {

    /**
     * 查询群列
     * 
     * @param id 群列主键
     * @return 群列
     */
    public RobotGroup selectRobotGroupById(Long id);

    /**
     * 查询群列列表
     * 
     * @param robotGroup 群列
     * @return 群列集合
     */
    public List<RobotGroup> selectRobotGroupList(RobotGroup robotGroup);

    /**
     * 新增群列
     * 
     * @param robotGroup 群列
     * @return 结果
     */
    public int insertRobotGroup(RobotGroup robotGroup);

    /**
     * 修改群列
     * 
     * @param robotGroup 群列
     * @return 结果
     */
    public int updateRobotGroup(RobotGroup robotGroup);

    /**
     * 删除群列
     * 
     * @param id 群列主键
     * @return 结果
     */
    public int deleteRobotGroupById(Long id);

    /**
     * 批量删除群列
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotGroupByIds(Long[] ids);
}
