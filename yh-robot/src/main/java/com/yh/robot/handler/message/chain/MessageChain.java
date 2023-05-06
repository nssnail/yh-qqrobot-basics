package com.yh.robot.handler.message.chain;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.BusinessRelType;
import com.yh.robot.enums.HandleType;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.service.RobotFriendsRelService;
import com.yh.robot.service.RobotGroupRelService;
import com.yh.robot.service.RobotKeyWordService;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 10:46:00
 */
public abstract class MessageChain {

    protected RobotKeyWordService robotKeyWordService = SpringUtil.getBean(RobotKeyWordService.class);
    protected RobotFriendsRelService robotFriendsRelService = SpringUtil.getBean(RobotFriendsRelService.class);
    protected RobotGroupRelService robotGroupRelService = SpringUtil.getBean(RobotGroupRelService.class);

    protected MessageChain nextChain;

    public abstract void handle(MessageVo messageVo);

    public void setNextChain(MessageChain nextChain) {
        this.nextChain = nextChain;
    }

    protected List<RobotKeyWord> getKeyWords(HandleType type) {
        return robotKeyWordService.getKeyWordByHandleType(type);
    }
    protected boolean isRel(MessageVo messageVo, Integer relId) {
        long count;
        if (messageVo.getGroupId() != null) {
            count = robotGroupRelService.count(Wrappers.<RobotGroupRel>lambdaQuery()
                    .eq(RobotGroupRel::getGroupId, messageVo.getGroupId())
                    .eq(RobotGroupRel::getRelType, BusinessRelType.KEY_WORD.getType())
                    .eq(RobotGroupRel::getRelId, relId));
        } else {
            count = robotFriendsRelService.count(Wrappers.<RobotFriendsRel>lambdaQuery()
                    .eq(RobotFriendsRel::getQq, messageVo.getUserId())
                    .eq(RobotFriendsRel::getRelType, BusinessRelType.KEY_WORD.getType())
                    .eq(RobotFriendsRel::getRelId, relId));
        }
        return count > 0;
    }


    protected boolean isRel(MessageVo messageVo, String keyWord) {
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord(keyWord);
        if (ObjectUtil.isEmpty(robotKeyWord)) {
            return false;
        }
        return isRel(messageVo,robotKeyWord.getId());
    }
}