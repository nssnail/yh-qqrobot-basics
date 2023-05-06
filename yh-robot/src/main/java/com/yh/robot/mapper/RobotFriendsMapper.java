package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotFriends;

/**
 * 好友列Mapper接口
 * 
 * @author nssnail
 * @date 2023-03-29
 */
@Mapper
public interface RobotFriendsMapper extends BaseMapper<RobotFriends> {

    /**
     * 查询好友列
     * 
     * @param id 好友列主键
     * @return 好友列
     */
    public RobotFriends selectRobotFriendsById(Long id);

    /**
     * 查询好友列列表
     * 
     * @param robotFriends 好友列
     * @return 好友列集合
     */
    public List<RobotFriends> selectRobotFriendsList(RobotFriends robotFriends);

    /**
     * 新增好友列
     * 
     * @param robotFriends 好友列
     * @return 结果
     */
    public int insertRobotFriends(RobotFriends robotFriends);

    /**
     * 修改好友列
     * 
     * @param robotFriends 好友列
     * @return 结果
     */
    public int updateRobotFriends(RobotFriends robotFriends);

    /**
     * 删除好友列
     * 
     * @param id 好友列主键
     * @return 结果
     */
    public int deleteRobotFriendsById(Long id);

    /**
     * 批量删除好友列
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotFriendsByIds(Long[] ids);
}
