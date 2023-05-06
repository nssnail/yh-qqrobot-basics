package com.yh.robot.handler.message.chain;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.service.RobotFriendsRelService;
import com.yh.robot.service.RobotGroupRelService;
import com.yh.robot.service.RobotKeyWordService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月24日 10:36:00
 */
@Slf4j
public class ChatGPTChain extends MessageChain {

    @Override
    public void handle(MessageVo messageVo) {
        String keyWord="chatgpt";
        if(isRel(messageVo,keyWord)){
            messageVo.setKeyWord(keyWord);
        }
    }
}