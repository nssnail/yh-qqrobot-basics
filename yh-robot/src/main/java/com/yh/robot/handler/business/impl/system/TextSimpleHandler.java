package com.yh.robot.handler.business.impl.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.domain.RobotKeyWordRel;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.enums.YesNoEnum;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.service.RobotKeyWordRelService;
import com.yh.robot.service.RobotKeyWordService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月31日 16:25:00
 */
@HandleService(value = "TextSimpleHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.TEXT
        , handleName = "简单文本处理器"
        , isApi = false)
public class TextSimpleHandler implements RobotBusinessHandler {
    @Resource
    private RobotKeyWordService robotKeyWordService;
    @Resource
    private RobotKeyWordRelService robotKeyWordRelService;

    @Override
    public List<String> handle(MessageVo messageVo) {
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord(messageVo.getKeyWord());
        List<RobotKeyWordRel> list = robotKeyWordRelService.list(Wrappers.<RobotKeyWordRel>lambdaQuery()
                .eq(RobotKeyWordRel::getKeyWordId, robotKeyWord.getId()));
        if(CollUtil.isEmpty(list)){
            throw BotException.error("关键字未配置信息",messageVo.getUserId(),messageVo.getGroupId());
        }
        if(robotKeyWord.getIsRandom()==null||robotKeyWord.getIsRandom()==YesNoEnum.YES.getType()){
            int i = RandomUtil.randomInt(0, list.size());
            return Collections.singletonList(list.get(i).getContent());
        }else {
            StringBuilder sb=new StringBuilder();
            list.forEach(e->{
                sb.append(e.getContent()).append("\n");
            });
            return Collections.singletonList(sb.toString());
        }
    }
}