package com.yh.robot.handler.business.impl.wold;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.youdao.YouDaoApi;
import com.yh.robot.third.youdao.result.Basic;
import com.yh.robot.third.youdao.result.Web;
import com.yh.robot.third.youdao.result.YouDaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description 单词翻译
 * @createTime 2023年03月17日 13:08:00
 */
@HandleService(value = "RobotWordTranslateHandler"
        , handleType = HandleType.REMOVE
        , sendType = SendMsgType.TEXT
        , handleName = "翻译处理器")
@Slf4j
public class RobotWordTranslateBusinessHandler implements RobotBusinessHandler {

    @Resource
    private YouDaoApi youDaoApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        YouDaoResult youDaoResult = youDaoApi.postApi(messageVo.getMsg());
        return Collections.singletonList(buildStr(youDaoResult));
//        RobotSendUtil.sendGroupMessage(data, groupId);
    }

    private String buildStr(YouDaoResult youDaoResult) {
        String query = "单词：" + youDaoResult.getQuery() + "\n";
        Basic basic = youDaoResult.getBasic();
        List<String> phonetics = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(basic)) {
            if (StringUtils.isNotBlank(basic.getPhonetic())) {
                phonetics.add("默认音标：" + basic.getPhonetic());
            }
            if (StringUtils.isNotBlank(basic.getUsPhonetic())) {
                phonetics.add("美式音标：" + basic.getUsPhonetic());
            }
            if (StringUtils.isNotBlank(basic.getUkPhonetic())) {
                phonetics.add("英式音标：" + basic.getUkPhonetic());
            }
        }
        String phonetic = "";
        if (CollectionUtils.isNotEmpty(phonetics)) {
            phonetic = String.join(",", phonetics) + "\n";
        }
        StringBuilder translation = new StringBuilder("翻译：" + String.join(",", youDaoResult.getTranslation()) + "\n");
        if (ObjectUtils.isNotEmpty(basic)) {
            for (String explain : basic.getExplains()) {
                translation.append(explain).append("\n");
            }
        }
        StringBuilder web = new StringBuilder();
        if (CollectionUtils.isNotEmpty(youDaoResult.getWeb())) {
            web = new StringBuilder("扩展：\n");
            for (Web data : youDaoResult.getWeb()) {
                String value = String.join(",", data.getValue());
                web.append(data.getKey()).append(":").append(value).append("\n");
            }
        }
        return query + phonetic + translation + web;
    }
}