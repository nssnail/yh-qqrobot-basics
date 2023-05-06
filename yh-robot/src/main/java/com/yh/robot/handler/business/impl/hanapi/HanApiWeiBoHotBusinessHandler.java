package com.yh.robot.handler.business.impl.hanapi;

import com.alibaba.fastjson.JSONArray;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.hanapi.HanApi;
import com.yh.robot.third.hanapi.HanApiArrayResult;
import com.yh.robot.third.hanapi.ResultVo;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 12:57:00
 */
@HandleService(value = "HanApiWeiBoHotBusinessHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.TEXT
        , handleName = "微博热搜")
public class HanApiWeiBoHotBusinessHandler implements RobotBusinessHandler {

    @Resource
    private HanApi hanApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        HanApiArrayResult weiBoHot = hanApi.getWeiBoHot();
        JSONArray data = weiBoHot.getData();
        List<ResultVo.WeiBo> weiBos = data.toJavaList(ResultVo.WeiBo.class);
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < weiBos.size(); i++) {
            int num=i+1;
            if(num>10){
                break;
            }
            ResultVo.WeiBo weiBo = weiBos.get(i);
            sb.append(num).append("、").append(weiBo.getTitle())
                    .append("热度：").append(weiBo.getHot()).append("\n")
                    .append("链接：").append(weiBo.getUrl()).append("\n");
        }

        return Collections.singletonList(sb.toString());
//        RobotSendUtil.sendGroupImageMessage(moyu,groupId);
    }

}