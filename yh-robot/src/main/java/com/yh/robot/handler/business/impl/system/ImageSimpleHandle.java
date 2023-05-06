package com.yh.robot.handler.business.impl.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.domain.RobotKeyWordRel;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.enums.YesNoEnum;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.service.RobotKeyWordRelService;
import com.yh.robot.service.RobotKeyWordService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月14日 15:38:00
 */
@HandleService(value = "ImageSimpleHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.IMAGE
        , handleName = "简单图片处理器"
        , isApi = false)
public class ImageSimpleHandle implements RobotBusinessHandler {
    @Resource
    private RobotKeyWordService robotKeyWordService;
    @Resource
    private RobotKeyWordRelService robotKeyWordRelService;

    @Override
    public List<String> handle(MessageVo messageVo) {
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord(messageVo.getKeyWord());
        List<RobotKeyWordRel> list = robotKeyWordRelService.list(Wrappers.<RobotKeyWordRel>lambdaQuery()
                .eq(RobotKeyWordRel::getKeyWordId, robotKeyWord.getId()));
        if (CollUtil.isEmpty(list)) {
            throw BotException.error("关键字未配置信息", messageVo.getUserId(), messageVo.getGroupId());
        }
        return list.stream().map(RobotKeyWordRel::getContent).collect(Collectors.toList());
    }
}