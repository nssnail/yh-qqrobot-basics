package com.yh.robot.handler.message.chain;

import com.yh.robot.handler.business.vo.MessageVo;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 16:17:00
 */
public class HelpChain extends MessageChain {
    @Override
    public void handle(MessageVo messageVo) {
        String keyWord = "帮助";
        if(messageVo.getMsg().equals(keyWord)){
            messageVo.setKeyWord(keyWord);
            return;
        }
        this.nextChain.handle(messageVo);
    }
}