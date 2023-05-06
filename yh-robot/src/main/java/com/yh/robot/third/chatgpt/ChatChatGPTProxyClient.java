package com.yh.robot.third.chatgpt;

import com.yh.common.exception.robot.BotException;
import com.yh.robot.third.chatgpt.result.ChatGPTResult;
import com.yh.robot.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 11:29:00
 */
@Service("ChatGPTProxyClient")
@Slf4j
//@Primary
public class ChatChatGPTProxyClient extends ChatGPTClient {


    @Override
    public String getText(String msg, String sessionId,String secretKey) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("apiKey", secretKey);
            map.put("sessionId", sessionId);
            map.put("content", msg);
            ChatGPTResult result = HttpUtil.post(chatGPTConfig.getProxyUrl(), map, ChatGPTResult.class);
            if (result.getCode() != 200) {
                log.error("chatgpt接口访问异常->{}", result.getMessage());
                throw BotException.error("chatgpt接口访问异常，可能人数访问较多，请稍后重试");
            }
            return result.getData();
        } catch (ResourceAccessException e) {
            log.error("请求ChatGPT接口超时接口异常：", e);
            throw BotException.error("请求ChatGPT接口超时");
        }
    }
}