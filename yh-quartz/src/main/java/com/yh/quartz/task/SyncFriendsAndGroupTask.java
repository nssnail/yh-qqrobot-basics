package com.yh.quartz.task;

import cn.hutool.core.collection.CollUtil;
import com.yh.robot.client.RobotClient;
import com.yh.robot.domain.RobotFriends;
import com.yh.robot.domain.RobotGroup;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.message.result.GetFriendListResult;
import com.yh.robot.message.result.GetFriendListResult.Friends;
import com.yh.robot.message.result.GetGroupResult;
import com.yh.robot.message.result.GetGroupResult.Group;
import com.yh.robot.service.RobotFriendsService;
import com.yh.robot.service.RobotGroupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月29日 17:23:00
 */
@Component("SyncFriendsAndGroupTask")
public class SyncFriendsAndGroupTask {

    @Resource
    private RobotFriendsService robotFriendsService;
    @Resource
    private RobotGroupService robotGroupService;

    public void sync() {
        robotFriendsService.syncFriends();
        robotGroupService.syncGroups();
    }

}