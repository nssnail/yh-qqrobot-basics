package com.yh.quartz.task;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.domain.RobotFriends;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.BusinessRelType;
import com.yh.robot.service.RobotFriendsRelService;
import com.yh.robot.service.RobotFriendsService;
import com.yh.robot.service.RobotKeyWordRelService;
import com.yh.robot.service.RobotKeyWordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 16:42:00
 */
@Component("SyncFriendsRel")
public class SyncFriendsRel {
    @Resource
    private RobotFriendsService robotFriendsService;
    @Resource
    private RobotFriendsRelService robotFriendsRelService;
    @Resource
    private RobotKeyWordService robotKeyWordService;

    public void sync() {
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord("chatgpt");
        if (ObjectUtil.isEmpty(robotKeyWord)) {
            return;
        }
        List<RobotFriends> list = robotFriendsService.list();
        list.forEach(e -> {
            RobotFriendsRel rel = robotFriendsRelService.getOne(Wrappers.<RobotFriendsRel>lambdaQuery()
                    .eq(RobotFriendsRel::getQq,e.getQq())
                    .eq(RobotFriendsRel::getRelId, robotKeyWord.getId())
                    .eq(RobotFriendsRel::getRelType, BusinessRelType.KEY_WORD.getType()));
            if (ObjectUtil.isEmpty(rel)) {
                RobotFriendsRel robotFriendsRel = new RobotFriendsRel();
                robotFriendsRel.setQq(e.getQq());
                robotFriendsRel.setRelId(robotKeyWord.getId().longValue());
                robotFriendsRel.setRelType(BusinessRelType.KEY_WORD.getType());
                robotFriendsRel.setCreateTime(new Date());
                robotFriendsRelService.save(robotFriendsRel);
            }
        });

    }
}