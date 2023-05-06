package com.yh.robot.handler.message.context;

import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.handler.message.chain.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 11:42:00
 */
@Slf4j
public class MessageChainContext {

    private final List<MessageChain> chains;


    private MessageChainContext() {
        chains = new ArrayList<>();
        chains.add(new EnglishChain());
        chains.add(new RemoveChain());
        chains.add(new ReplaceChain());
        chains.add(new NothingChain());
        chains.add(new HelpChain());
        chains.add(new ChatGPTChain());
        for (int i = 0; i < chains.size() - 1; i++) {
            chains.get(i).setNextChain(chains.get(i + 1));
        }
    }

    private static class Instance {
        private static final MessageChainContext INSTANCE = new MessageChainContext();
    }

    public static MessageChainContext getInstance() {
        return Instance.INSTANCE;
    }

    public void getMessage(MessageVo messageVo) {
        chains.get(0).handle(messageVo);
    }
}