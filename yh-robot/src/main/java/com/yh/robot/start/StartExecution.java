package com.yh.robot.start;

import com.yh.robot.service.RobotHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 16:13:00
 */
@Component
@Slf4j
public class StartExecution implements ApplicationRunner {

    @Resource
    private RobotHandlerService robotHandlerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        robotHandlerService.sync();
    }
}