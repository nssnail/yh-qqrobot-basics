package com.yh.robot.handler.business.context;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.yh.robot.enums.SendMsgType;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.service.RobotKeyWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月17日 10:36:00
 */
@Component
@Slf4j
public class RobotBusinessContext {

    @Resource
    private RobotKeyWordService robotKeyWordService;
    @Resource
    private ApplicationContext applicationContext;

    public BusinessInvokeVo getData(MessageVo messageVo) {
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord(messageVo.getKeyWord());
        RobotBusinessHandler bean = (RobotBusinessHandler) applicationContext.getBean(robotKeyWord.getBeanName());
        Assert.isTrue(ObjectUtil.isNotEmpty(bean), () -> BotException.error("系统异常，找不到对应的bean",messageVo.getUserId(),messageVo.getGroupId()));
        List<String> handle;
        try {
            handle = bean.handle(messageVo);
        }catch (BotException e){
            throw BotException.error(e.getMessage(),messageVo.getUserId(),messageVo.getGroupId());
        }
        log.info("处理请求文本返回->{}", handle);
        return new BusinessInvokeVo(handle, SendMsgType.getMessageType(robotKeyWord.getSendMsgType()));
    }


}