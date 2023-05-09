package com.yh.robot.third.chatgpt;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.yh.common.exception.ServiceException;
import com.yh.robot.redis.RedisKey;
import com.yh.robot.third.chatgpt.result.GPTRole;
import com.yh.robot.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyh
 * @Description TODO
 * @createTime 2023年05月09日 09:23:00
 */
@Service("ChatGPTProxyClient")
@Slf4j
public class ChatGPTProxyClientV2 extends ChatGPTClient {


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
            result = HttpUtil.post(chatGPTConfig.getProxyUrl(), JSONObject.toJSONString(gptParam), headers, GPTResult.class);
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