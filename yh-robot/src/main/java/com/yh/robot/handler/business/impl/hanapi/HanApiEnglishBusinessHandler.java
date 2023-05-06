package com.yh.robot.handler.business.impl.hanapi;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.hanapi.HanApi;
import com.yh.robot.third.hanapi.HanApiResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName HanApiEnglishHandler
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 23:43
 * @Version 1.0
 */
@HandleService(value = "HanApiEnglishHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.TEXT
        , handleName = "HanApi英语短句")
public class HanApiEnglishBusinessHandler implements RobotBusinessHandler {

    @Resource
    private HanApi hanApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        HanApiResult english = hanApi.getEnglish();
        if (ObjectUtil.isNotEmpty(english)) {
            return Collections.singletonList(buildEnglish(english.getData()));
        }
        throw  BotException.error("api调用结果异常");
//        RobotSendUtil.sendGroupMessage(english,groupId);
    }

    private String buildEnglish(JSONObject data) {
        return data.get("en") + "\n" + data.get("zh");
    }

}
