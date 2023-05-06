package com.yh.robot.handler.business.impl.system;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.BusinessRelType;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.service.RobotFriendsRelService;
import com.yh.robot.service.RobotGroupRelService;
import com.yh.robot.service.RobotKeyWordService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 15:52:00
 */
@HandleService(value = "HelpHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.TEXT
        , handleName = "帮助"
        , isApi = false)
public class HelpHandler implements RobotBusinessHandler {

    @Resource
    private RobotFriendsRelService robotFriendsRelService;
    @Resource
    private RobotGroupRelService robotGroupRelService;
    @Resource
    private RobotKeyWordService robotKeyWordService;

    @Override
    public List<String> handle(MessageVo messageVo) {
        List<Long> relIds;
        if (messageVo.getGroupId() != null) {
            List<RobotGroupRel> list = robotGroupRelService.list(Wrappers.<RobotGroupRel>lambdaQuery()
                    .eq(RobotGroupRel::getGroupId, messageVo.getGroupId())
                    .eq(RobotGroupRel::getRelType, BusinessRelType.KEY_WORD.getType()));
            relIds = list.stream().map(RobotGroupRel::getRelId).collect(Collectors.toList());
        } else {
            List<RobotFriendsRel> list = robotFriendsRelService.list(Wrappers.<RobotFriendsRel>lambdaQuery()
                    .eq(RobotFriendsRel::getQq, messageVo.getUserId())
                    .eq(RobotFriendsRel::getRelType, BusinessRelType.KEY_WORD.getType()));
            relIds = list.stream().map(RobotFriendsRel::getRelId).collect(Collectors.toList());
        }
        List<RobotKeyWord> keyWords = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(relIds)) {
            keyWords = robotKeyWordService.list(Wrappers.<RobotKeyWord>lambdaQuery()
                    .in(RobotKeyWord::getId, relIds).eq(RobotKeyWord::getState, StateEnum.NORMAL.getType()));
        }
        if (CollectionUtils.isEmpty(keyWords)) {
            throw BotException.error("对不起，该账号(群)没有配置任何关键字，请联系管理员");
        }
        return Collections.singletonList(formatKeyWord(keyWords));
    }

    private String formatKeyWord(List<RobotKeyWord> keyWords) {
        StringBuilder sb = new StringBuilder();
        sb.append("该账号(群)可用关键字如下：\n");
        for (int i = 0; i < keyWords.size(); i++) {
            RobotKeyWord robotKeyWord = keyWords.get(i);
            sb.append(i+1).append("、").append(robotKeyWord.getKeyWord());
            if(StringUtils.isNotBlank(robotKeyWord.getRemake())){
                sb.append("(").append(robotKeyWord.getRemake()).append(")");
            }
            sb.append("\n");

        }
        return sb.toString();
    }
}