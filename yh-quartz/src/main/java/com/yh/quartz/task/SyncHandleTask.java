package com.yh.quartz.task;

import cn.hutool.core.util.ObjectUtil;
import com.yh.robot.domain.RobotHandler;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.service.RobotHandlerService;
import com.yh.robot.service.RobotKeyWordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月31日 15:15:00
 */
@Component("SyncHandleTask")
public class SyncHandleTask {
    @Resource
    private RobotHandlerService robotHandlerService;
    @Resource
    private RobotKeyWordService robotKeyWordService;

    public void sync(Boolean isSyncKeyWord) {
        robotHandlerService.sync();
        if (isSyncKeyWord) {
            List<RobotKeyWord> robotKeyWords = robotKeyWordService.list();
            List<RobotHandler> handlerList = robotHandlerService.list();
            robotKeyWords.forEach(e->{
                RobotHandler robotHandler = handlerList.stream().filter(handle -> handle.getBeanName().equals(e.getBeanName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(robotHandler)){
                    e.setHandleId(robotHandler.getId());
                    robotKeyWordService.updateById(e);
                }
            });
        }
    }
}