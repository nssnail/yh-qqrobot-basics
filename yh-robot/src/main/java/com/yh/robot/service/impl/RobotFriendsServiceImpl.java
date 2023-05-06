package com.yh.robot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.exception.ServiceException;
import com.yh.common.utils.DateUtils;
import com.yh.robot.client.RobotClient;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.message.result.GetFriendListResult;
import com.yh.robot.params.FriendsRelParams;
import com.yh.robot.service.RobotFriendsRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotFriendsMapper;
import com.yh.robot.domain.RobotFriends;
import com.yh.robot.service.RobotFriendsService;

import javax.annotation.Resource;


/**
 * 好友列Service业务层处理
 *
 * @author nssnail
 * @date 2023-03-29
 */
@Service
public class RobotFriendsServiceImpl extends ServiceImpl<RobotFriendsMapper, RobotFriends> implements RobotFriendsService {

    @Autowired
    private RobotFriendsMapper robotFriendsMapper;
    @Autowired
    private RobotFriendsRelService robotFriendsRelService;
    @Resource
    private RobotClient robotClient;
    @Value("${qq}")
    private Long qq;

    /**
     * 查询好友列
     *
     * @param id 好友列主键
     * @return 好友列
     */
    @Override
    public RobotFriends selectRobotFriendsById(Long id) {
        return robotFriendsMapper.selectRobotFriendsById(id);
    }

    /**
     * 查询好友列列表
     *
     * @param robotFriends 好友列
     * @return 好友列
     */
    @Override
    public List<RobotFriends> selectRobotFriendsList(RobotFriends robotFriends) {
        return robotFriendsMapper.selectRobotFriendsList(robotFriends);
    }

    /**
     * 新增好友列
     *
     * @param robotFriends 好友列
     * @return 结果
     */
    @Override
    public int insertRobotFriends(RobotFriends robotFriends) {
        robotFriends.setCreateTime(DateUtils.getNowDate());
        return robotFriendsMapper.insertRobotFriends(robotFriends);
    }

    /**
     * 修改好友列
     *
     * @param robotFriends 好友列
     * @return 结果
     */
    @Override
    public int updateRobotFriends(RobotFriends robotFriends) {
        robotFriends.setUpdateTime(DateUtils.getNowDate());
        return robotFriendsMapper.updateRobotFriends(robotFriends);
    }

    /**
     * 批量删除好友列
     *
     * @param ids 需要删除的好友列主键
     * @return 结果
     */
    @Override
    public int deleteRobotFriendsByIds(Long[] ids) {
        return robotFriendsMapper.deleteRobotFriendsByIds(ids);
    }

    /**
     * 删除好友列信息
     *
     * @param id 好友列主键
     * @return 结果
     */
    @Override
    public int deleteRobotFriendsById(Long id) {
        return robotFriendsMapper.deleteRobotFriendsById(id);
    }

    @Override
    public void toggle(Long id) {
        RobotFriends robotFriends = robotFriendsMapper.selectById(id);
        Assert.isTrue(ObjectUtil.isNotEmpty(robotFriends), () -> new ServiceException("找不到对应的好友"));
        robotFriends.setStatus(robotFriends.getStatus() == StateEnum.NORMAL.getType() ? StateEnum.DISABLE.getType() : StateEnum.NORMAL.getType());
        robotFriendsMapper.updateRobotFriends(robotFriends);
    }

    @Override
    public void insertOrUpdateFriendsRel(FriendsRelParams friendsRelParams) {
        List<Long> relIds = friendsRelParams.getRelIds();
        robotFriendsRelService.remove(Wrappers.<RobotFriendsRel>lambdaQuery()
                .eq(RobotFriendsRel::getQq, friendsRelParams.getQq())
                .eq(RobotFriendsRel::getRelType, friendsRelParams.getRelType()));
        if (CollUtil.isNotEmpty(relIds)) {
            List<RobotFriendsRel> robotFriendsRels = relIds.stream().map(id -> {
                RobotFriendsRel robotFriendsRel = new RobotFriendsRel();
                robotFriendsRel.setQq(friendsRelParams.getQq());
                robotFriendsRel.setRelId(id);
                robotFriendsRel.setRelType(friendsRelParams.getRelType());
                return robotFriendsRel;
            }).collect(Collectors.toList());
            robotFriendsRelService.saveBatch(robotFriendsRels);
        }
    }

    @Override
    public List<RobotFriendsRel> getRel(FriendsRelParams friendsRelParams) {
        return robotFriendsRelService.list(Wrappers.<RobotFriendsRel>lambdaQuery()
                .eq(RobotFriendsRel::getQq, friendsRelParams.getQq())
                .eq(RobotFriendsRel::getRelType, friendsRelParams.getRelType()));
    }


    @Override
    public void syncFriends() {
        GetFriendListResult getFriendListResult = robotClient.getFriendList();
        List<GetFriendListResult.Friends> friendList = getFriendListResult.getData();
        if (CollUtil.isNotEmpty(friendList)) {
            List<RobotFriends> list = this.list();
            List<GetFriendListResult.Friends> newList = friendList.stream().filter(e -> {
                for (RobotFriends robotFriends : list) {
                    if (robotFriends.getQq().equals(e.getUserId())) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(newList)) {
                List<RobotFriends> inserts = newList.stream().map(item -> {
                    RobotFriends robotFriends = new RobotFriends();
                    robotFriends.setQq(item.getUserId());
                    robotFriends.setNickname(item.getNickname());
                    robotFriends.setRemake(item.getRemark());
                    robotFriends.setSelfId(qq);
                    robotFriends.setStatus(StateEnum.NORMAL.getType());
                    robotFriends.setCreateTime(new Date());
                    robotFriends.setUpdateTime(new Date());
                    return robotFriends;
                }).collect(Collectors.toList());
                this.saveBatch(inserts);
            }
        }
    }
}
