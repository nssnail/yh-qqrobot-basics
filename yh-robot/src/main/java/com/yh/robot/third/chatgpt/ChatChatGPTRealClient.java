package com.yh.robot.third.chatgpt;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.yh.common.exception.ServiceException;
import com.yh.common.exception.robot.BotException;
import com.yh.common.utils.StringUtils;
import com.yh.robot.redis.RedisKey;
import com.yh.robot.third.chatgpt.param.ChatGPTParam;
import com.yh.robot.third.chatgpt.result.GPTResult;
import com.yh.robot.third.chatgpt.result.GPTRole;
import com.yh.robot.util.CommonUtil;
import com.yh.robot.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 11:31:00
 */
@Service("ChatGPTRealClient")
@Slf4j
@Primary
public class ChatChatGPTRealClient extends ChatGPTClient {


    @Override
    public String getText(String msg, String sessionId,String secretKey) {
        Assert.isTrue(StringUtils.isNoneBlank(sessionId), () -> new ServiceException("sessionid不能为空"));
        String redisKey = RedisKey.CHAT_REAL_ID.getKey(sessionId);
        String list = client.get(redisKey);
        List<GPTRole> gptRoles = new ArrayList<>();
        if (StringUtil.isNotEmpty(list)) {
            gptRoles = JSONArray.parseArray(list, GPTRole.class);
        }
        gptRoles.add(CommonUtil.build(msg));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Bearer %s", secretKey));
        ChatGPTParam gptParam = new ChatGPTParam("gpt-3.5-turbo", 0.7, gptRoles);
        GPTResult result = null;
        try {
            result = HttpUtil.post(chatGPTConfig.getRealUrl(), JSONObject.toJSONString(gptParam), headers, GPTResult.class);
        }catch (Exception e){
            log.error("chatgpt接口访问异常->", e);
            throw BotException.error("chatgpt接口访问异常，可能人数访问较多或者超出当前会话上上限，请稍后重试或者尝试输入关闭会话");
        }
        log.info("chatgpt原始接口返回：{}", JSONObject.toJSONString(result));
        GPTRole message = result.getChoices().get(0).getMessage();
        String content = message.getContent();
        gptRoles.add(message);
        client.set(redisKey, JSONArray.toJSONString(gptRoles));
        return content;
    }
}