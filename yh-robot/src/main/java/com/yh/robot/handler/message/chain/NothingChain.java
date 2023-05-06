package com.yh.robot.handler.message.chain;

import cn.hutool.core.util.StrUtil;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.HandleType;
import com.yh.robot.handler.business.vo.MessageVo;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 11:54:00
 */
public class NothingChain extends MessageChain {

    @Override
    public void handle(MessageVo messageVo) {
        for (RobotKeyWord robotKeyWord : getKeyWords(HandleType.NOTHING)) {
            String keyWord = robotKeyWord.getKeyWord();
            if (messageVo.getMsg().equals(keyWord)) {
                if (isRel(messageVo, robotKeyWord.getId())) {
                    messageVo.setKeyWord(keyWord).setMsg(StrUtil.trim(keyWord));
                    return;
                }
            }
        }
        nextChain.handle(messageVo);
    }
}