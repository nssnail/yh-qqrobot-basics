package com.yh.robot.service;

import java.util.List;

import com.yh.robot.domain.RobotFriends;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.params.FriendsRelParams;
import com.yh.robot.params.GroupRelParams;

/**
 * 好友列Service接口
 *
 * @author nssnail
 * @date 2023-03-29
 */
public interface RobotFriendsService extends IService<RobotFriends> {

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
     * 批量删除好友列
     *
     * @param ids 需要删除的好友列主键集合
     * @return 结果
     */
    public int deleteRobotFriendsByIds(Long[] ids);

    /**
     * 删除好友列信息
     *
     * @param id 好友列主键
     * @return 结果
     */
    public int deleteRobotFriendsById(Long id);

    void toggle(Long id);

    void insertOrUpdateFriendsRel(FriendsRelParams friendsRelParams);

    List<RobotFriendsRel> getRel(FriendsRelParams friendsRelParams);

    void syncFriends();
}
