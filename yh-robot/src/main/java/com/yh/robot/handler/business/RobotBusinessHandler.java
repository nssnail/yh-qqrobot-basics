package com.yh.robot.handler.business;


import com.yh.robot.handler.business.vo.MessageVo;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月17日 12:02:00
 */
public interface RobotBusinessHandler {

     List<String> handle(MessageVo messageVo);

}