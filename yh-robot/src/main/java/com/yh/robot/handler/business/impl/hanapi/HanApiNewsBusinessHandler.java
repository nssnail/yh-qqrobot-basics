package com.yh.robot.handler.business.impl.hanapi;

import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.hanapi.HanApi;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月12日 09:20:00
 */
@HandleService(value = "HanApiNewsBusinessHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.IMAGE
        , handleName = "HanAPI 60s新闻")
public class HanApiNewsBusinessHandler implements RobotBusinessHandler {

    @Resource
    private HanApi hanApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(hanApi.getNews().getImgurl());
    }
}