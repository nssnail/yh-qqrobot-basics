package com.yh.robot.third.chatgpt;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.yh.common.exception.ServiceException;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.redis.RedisKey;
import com.yh.robot.third.chatgpt.param.ChatGPTParam;
import com.yh.robot.third.chatgpt.result.GPTResult;
import com.yh.robot.third.chatgpt.result.GPTRole;
import com.yh.robot.util.CommonUtil;
import com.yh.robot.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
public class ChatGPTRealClient extends ChatGPTClient {


    @Override
    public String getText(String msg, String sessionId,String secretKey) {
        Assert.isTrue(org.apache.commons.lang3.StringUtils.isNoneBlank(sessionId), () -> new ServiceException("sessionid不能为空"));
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
        }catch (HttpClientErrorException.BadRequest e) {
            log.error("chatgpt接口访问异常,400异常->", e);
            String message = e.getMessage();
            if (StringUtils.isNoneBlank(message) && message.contains("maximum")) {
                throw BotException.error("单个会话长度超出上限，请输入'关闭会话'后重试");
            }
        } catch (Exception e) {
            log.error("chatgpt接口访问异常->", e);
            throw BotException.error("chatgpt接口访问异常，可能人数访问较多,请稍后重试");
        }
        log.info("chatgpt原始接口返回：{}", JSONObject.toJSONString(result));
        GPTRole message = result.getChoices().get(0).getMessage();
        String content = message.getContent();
        gptRoles.add(message);
        client.set(redisKey, JSONArray.toJSONString(gptRoles));
        return content;
    }
}