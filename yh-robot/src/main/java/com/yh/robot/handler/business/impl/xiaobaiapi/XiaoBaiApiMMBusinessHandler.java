package com.yh.robot.handler.business.impl.xiaobaiapi;

import cn.hutool.core.util.RandomUtil;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.hanapi.HanApi;
import com.yh.robot.third.xiaobaiapi.XiaoBaiApi;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 13:36:00
 */
@HandleService(value = "XiaoBaiApiMMBusinessHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.IMAGE
        , handleName = "美女图片")
public class XiaoBaiApiMMBusinessHandler implements RobotBusinessHandler {

    @Resource
    private XiaoBaiApi xiaoBaiApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(xiaoBaiApi.getMM());
    }
}