package com.yh.robot.handler.business.impl.alapi;

import com.alibaba.fastjson.JSONObject;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.alapi.AlApi;
import com.yh.robot.third.alapi.AlApiConstant;
import com.yh.robot.third.alapi.AlApiResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月17日 14:33:00
 */
@HandleService(value = "AlApiNewspaperHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.IMAGE
        , handleName = "60s新闻")
public class AlApiNewspaperBusinessHandler implements RobotBusinessHandler {
    @Resource
    private AlApi alApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        AlApiResult result = alApi.send(AlApiConstant.ZAOBAO);
        if (result.getCode() == 200) {
            JSONObject data = result.getData();
            return Collections.singletonList(data.get("image").toString());
//            RobotSendUtil.sendGroupImageMessage(image, groupId);
        }
        throw BotException.error("找不到对应图片");
    }
}