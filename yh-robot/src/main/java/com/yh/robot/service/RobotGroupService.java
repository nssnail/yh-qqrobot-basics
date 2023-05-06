package com.yh.robot.service;

import java.util.List;
import com.yh.robot.domain.RobotGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.params.GroupRelParams;

/**
 * 群列Service接口
 * 
 * @author nssnail
 * @date 2023-03-30
 */
public interface RobotGroupService extends IService<RobotGroup> {

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
     * 批量删除群列
     * 
     * @param ids 需要删除的群列主键集合
     * @return 结果
     */
    public int deleteRobotGroupByIds(Long[] ids);

    /**
     * 删除群列信息
     * 
     * @param id 群列主键
     * @return 结果
     */
    public int deleteRobotGroupById(Long id);

    void toggle(Long id);

    void insertOrUpdateGroupRel(GroupRelParams groupRelParams);

    List<RobotGroupRel> getRel(GroupRelParams groupRelParams);

    void syncGroups();
}
