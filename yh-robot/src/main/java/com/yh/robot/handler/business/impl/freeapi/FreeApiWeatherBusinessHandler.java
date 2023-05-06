package com.yh.robot.handler.business.impl.freeapi;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.freeapi.FreeApi;
import com.yh.robot.third.freeapi.FreeApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月17日 17:31:00
 */
@HandleService(value = "FreeApiWeatherHandler"
        , handleType = HandleType.REMOVE
        , sendType = SendMsgType.TEXT
        , handleName = "FreeApi天气")
@Slf4j
public class FreeApiWeatherBusinessHandler implements RobotBusinessHandler {

    @Resource
    private FreeApi freeApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        FreeApiResult result = freeApi.getTianQi(messageVo.getMsg());
        log.info("天气接口查询结果=>{}", JSONObject.toJSONString(result));
        if (result.getCode() == 1) {
            return Collections.singletonList(buildTianQiStr(result.getData()));
        }
        return null;
    }


    public String buildTianQiStr(JSONObject object) {
        return object.get("address").toString() + "\n" +
                "温度：" + object.get("temp").toString() + "," + object.get("weather").toString() + "\n" +
                "湿度：" + object.get("humidity").toString() + "\n" +
                "风向：" + object.get("windDirection").toString() + "\n" +
                "空气指标：" + object.get("windPower").toString() + "\n" +
                object.get("reportTime").toString() + "\n";
    }
}