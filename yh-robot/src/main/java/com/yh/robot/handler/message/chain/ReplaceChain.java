package com.yh.robot.handler.message.chain;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.HandleType;
import com.yh.robot.handler.business.vo.MessageVo;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 11:37:00
 */
public class ReplaceChain extends MessageChain {

    @Override
    public void handle(MessageVo messageVo) {
        String msg = messageVo.getMsg();
        for (RobotKeyWord robotKeyWord : getKeyWords(HandleType.SUB)) {
            String keyWord = robotKeyWord.getKeyWord();
            if (msg.startsWith(keyWord)) {
                if (isRel(messageVo, robotKeyWord.getId())) {
                    String data = StrUtil.trim(StrUtil.removePrefix(msg, keyWord));
                    messageVo.setKeyWord(keyWord).setMsg(data);
                    return;
                }
            }

        }
        this.nextChain.handle(messageVo);
    }
}