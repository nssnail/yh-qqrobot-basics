package com.yh.robot.handler.message.chain;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.HandleType;
import com.yh.robot.handler.business.vo.MessageVo;


/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 11:00:00
 */
public class RemoveChain extends MessageChain {

    @Override
    public void handle(MessageVo messageVo) {
        String msg = messageVo.getMsg();
        for (RobotKeyWord robotKeyWord : getKeyWords(HandleType.REMOVE)) {
            String keyWord = robotKeyWord.getKeyWord();
            if (msg.contains(keyWord)) {
                if (isRel(messageVo, robotKeyWord.getId())) {
                    String data = StrUtil.trim(msg.replaceAll(keyWord, ""));
                    messageVo.setKeyWord(keyWord).setMsg(data);
                    return;
                }
            }
        }
        this.nextChain.handle(messageVo);
    }
}