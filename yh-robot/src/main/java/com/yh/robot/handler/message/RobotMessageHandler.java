package com.yh.robot.handler.message;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.yh.robot.client.RobotClient;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.handler.business.context.RobotBusinessContext;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.handler.message.context.MessageChainContext;
import com.yh.robot.message.receive.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 09:25:00
 */
public abstract class RobotMessageHandler {

    @Autowired
    protected RobotBusinessContext robotBusinessContext;
    @Autowired
    protected RobotClient robotClient;

    public abstract void handler(Message message);

    protected BusinessInvokeVo getData(MessageVo msg) {
        MessageChainContext.getInstance().getMessage(msg);
        Assert.isTrue(StrUtil.isNotEmpty(msg.getKeyWord()), () -> BotException.error("关键字输入有误，请查看帮助列表",msg.getUserId(),msg.getGroupId()));
        return robotBusinessContext.getData(msg);
    }

}