package com.yh.robot.handler.message.chain;

import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.util.CommonUtil;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 10:49:00
 */
public class EnglishChain extends MessageChain {

    @Override
    public void handle(MessageVo messageVo) {
        if (CommonUtil.isEnglish(messageVo.getMsg())) {
            String keyWord = "翻译";
            if (isRel(messageVo, keyWord)) {
                messageVo.setKeyWord("翻译");
                return;
            }
        }
        this.nextChain.handle(messageVo);
    }
}