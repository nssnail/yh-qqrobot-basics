package com.yh.robot.handler.message.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.config.VerifyProperties;
import com.yh.robot.constant.BeanNameConstant;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.handler.message.RobotMessageDispatcher;
import com.yh.robot.message.receive.RequestMessage;
import com.yh.robot.message.result.RobotResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月20日 14:54:00
 */
@Service(BeanNameConstant.PostTypeBean.REQUEST)
@Slf4j
public class RequestDispatcher implements RobotMessageDispatcher {

    @Resource
    private VerifyProperties verifyProperties;

    @Override
    public RobotResult dispatcher(JSONObject jsonObject) {
        RequestMessage requestMessage = JSONObject.parseObject(jsonObject.toJSONString(), RequestMessage.class);
        if (requestMessage.getRequestType().equals(RobotMessageConstant.RequestType.FRIEND)) {
            List<String> words= verifyProperties.getWords();
            for (String word : words) {
                if(requestMessage.getComment().contains(word)){
                    return RobotResult.pass();
                }
            }
            log.info("好友请求->{}", jsonObject.toJSONString());
            return RobotResult.refuse("请联系管理员获取正确渠道");
        }
        return RobotResult.success();
    }
}