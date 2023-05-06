package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotFriendsRel;

/**
 * 好友关联Mapper接口
 * 
 * @author nssnail
 * @date 2023-04-04
 */
@Mapper
public interface RobotFriendsRelMapper extends BaseMapper<RobotFriendsRel> {

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
     * 删除好友关联
     * 
     * @param id 好友关联主键
     * @return 结果
     */
    public int deleteRobotFriendsRelById(Long id);

    /**
     * 批量删除好友关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotFriendsRelByIds(Long[] ids);
}
