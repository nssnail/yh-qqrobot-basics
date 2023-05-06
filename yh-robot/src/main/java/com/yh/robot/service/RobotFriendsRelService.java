package com.yh.robot.service;

import java.util.List;
import com.yh.robot.domain.RobotFriendsRel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.enums.BusinessRelType;

/**
 * 好友关联Service接口
 * 
 * @author nssnail
 * @date 2023-04-04
 */
public interface RobotFriendsRelService extends IService<RobotFriendsRel> {

    /**
     * 查询好友关联
     * 
     * @param id 好友关联主键
     * @return 好友关联
     */
    public RobotFriendsRel selectRobotFriendsRelById(Long id);

    /**
     * 查询好友关联列表
     * 
     * @param robotFriendsRel 好友关联
     * @return 好友关联集合
     */
    public List<RobotFriendsRel> selectRobotFriendsRelList(RobotFriendsRel robotFriendsRel);

    /**
     * 新增好友关联
     * 
     * @param robotFriendsRel 好友关联
     * @return 结果
     */
    public int insertRobotFriendsRel(RobotFriendsRel robotFriendsRel);

    /**
     * 修改好友关联
     * 
     * @param robotFriendsRel 好友关联
     * @return 结果
     */
    public int updateRobotFriendsRel(RobotFriendsRel robotFriendsRel);

    /**
     * 批量删除好友关联
     * 
     * @param ids 需要删除的好友关联主键集合
     * @return 结果
     */
    public int deleteRobotFriendsRelByIds(Long[] ids);

    /**
     * 删除好友关联信息
     * 
     * @param id 好友关联主键
     * @return 结果
     */
    public int deleteRobotFriendsRelById(Long id);

    List<RobotFriendsRel> getFriendsRelByType(BusinessRelType type,Long relId);
}
