package com.yh.robot.handler.business.impl.xiaobaiapi;

import com.github.pagehelper.util.StringUtil;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.xiaobaiapi.XiaoBaiApi;
import com.yh.robot.third.xiaobaiapi.result.DeliveryResult;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月11日 14:49:00
 */
@HandleService(value = "XiaoBaiApiSearshDeliveryBusinessHandler"
        , handleType = HandleType.SUB
        , sendType = SendMsgType.TEXT
        , handleName = "xiaobaiAPi 查快递")
public class XiaoBaiApiSearshDeliveryBusinessHandler implements RobotBusinessHandler {

    @Resource
    private XiaoBaiApi xiaoBaiApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        DeliveryResult searchDelivery = xiaoBaiApi.getSearchDelivery(messageVo.getMsg());
        return Collections.singletonList(buildStr(searchDelivery));
    }

    private String buildStr(DeliveryResult searchDelivery) {
        StringBuilder title = new StringBuilder();
        DeliveryResult.RouteInfo routeInfo = searchDelivery.getRouteInfo();
        title.append("始发地：").append(routeInfo.getFrom().getName()).append("\n");
        title.append("收件地址：").append(routeInfo.getTo().getName()).append("\n");
        String line = "---------\n";
        StringBuilder info = new StringBuilder();
        List<DeliveryResult.Info> data = searchDelivery.getData();
        data.forEach(e -> {
            String areaName = e.getAreaName();

            info.append(line)
                    .append("时间：").append(e.getTime()).append("\n");
            if (StringUtil.isNotEmpty(areaName)) {
                String[] split = areaName.split(",");
                info.append("[").append(split[split.length-1]).append("]");
            }
            info.append(e.getContext()).append("\n")
                    .append(e.getStatus()+"\n");
        });
        return title.append(info).toString();
    }
}