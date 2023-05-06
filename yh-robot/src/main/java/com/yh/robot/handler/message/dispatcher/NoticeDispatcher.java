package com.yh.robot.handler.message.dispatcher;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.client.RobotClient;
import com.yh.robot.constant.BeanNameConstant;
import com.yh.robot.constant.NoticeConstant;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.enums.BusinessRelType;
import com.yh.robot.handler.message.RobotMessageDispatcher;
import com.yh.robot.message.receive.NoticeMessage;
import com.yh.robot.message.result.RobotResult;
import com.yh.robot.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月20日 14:53:00
 */
@Service(BeanNameConstant.PostTypeBean.NOTICE)
public class NoticeDispatcher implements RobotMessageDispatcher {
    @Resource
    private RobotClient robotClient;
    @Resource
    private RobotKeyWordService robotKeyWordService;
    @Resource
    private RobotFriendsRelService robotFriendsRelService;
    @Resource
    private RobotGroupRelService robotGroupRelService;
    @Resource
    private RobotFriendsService robotFriendsService;
    @Resource
    private RobotGroupService robotGroupService;
    @Value("${qq}")
    private Long qq;

    @Override
    public RobotResult dispatcher(JSONObject jsonObject) {
        NoticeMessage noticeMessage = JSONObject.parseObject(jsonObject.toJSONString(), NoticeMessage.class);
        if (noticeMessage.getNoticeType().equals(RobotMessageConstant.NoticeType.FRIEND_ADD)) {
            saveFriendsRel(noticeMessage,"chatgpt");
            saveFriendsRel(noticeMessage,"关闭会话");

            robotFriendsService.syncFriends();
            robotClient.sendPrivateMessage(NoticeConstant.FRIEND_ADD_NOTICE, noticeMessage.getUserId());
        }else if(noticeMessage.getNoticeType().equals(RobotMessageConstant.NoticeType.GROUP_INCREASE)){
            if(noticeMessage.getUserId().equals(qq)){
                saveGroupRel(noticeMessage,"chatgpt");
                saveGroupRel(noticeMessage,"关闭会话");
                robotGroupService.syncGroups();
            }
        }
        return RobotResult.success();
    }

    private void saveGroupRel(NoticeMessage noticeMessage, String keyWord) {
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord(keyWord);
        if (ObjectUtil.isNotEmpty(robotKeyWord)) {
            RobotGroupRel rel = robotGroupRelService.getOne(Wrappers.<RobotGroupRel>lambdaQuery()
                    .eq(RobotGroupRel::getGroupId, noticeMessage.getGroupId())
                    .eq(RobotGroupRel::getRelId, robotKeyWord.getId())
                    .eq(RobotGroupRel::getRelType, BusinessRelType.KEY_WORD.getType()));
            if (ObjectUtil.isEmpty(rel)) {
                RobotGroupRel robotGroupRel = new RobotGroupRel();
                robotGroupRel.setGroupId(noticeMessage.getGroupId());
                robotGroupRel.setRelId(robotKeyWord.getId().longValue());
                robotGroupRel.setRelType(BusinessRelType.KEY_WORD.getType());
                robotGroupRel.setCreateTime(new Date());
                robotGroupRelService.save(robotGroupRel);
            }
        }
    }

    private void saveFriendsRel(NoticeMessage noticeMessage,String keyWord){
        RobotKeyWord robotKeyWord = robotKeyWordService.getRobotKeyWord(keyWord);
        if (ObjectUtil.isNotEmpty(robotKeyWord)) {
            RobotFriendsRel rel = robotFriendsRelService.getOne(Wrappers.<RobotFriendsRel>lambdaQuery()
                    .eq(RobotFriendsRel::getQq, noticeMessage.getUserId())
                    .eq(RobotFriendsRel::getRelId, robotKeyWord.getId())
                    .eq(RobotFriendsRel::getRelType, BusinessRelType.KEY_WORD.getType()));
            if (ObjectUtil.isEmpty(rel)) {
                RobotFriendsRel robotFriendsRel = new RobotFriendsRel();
                robotFriendsRel.setQq(noticeMessage.getUserId());
                robotFriendsRel.setRelId(robotKeyWord.getId().longValue());
                robotFriendsRel.setRelType(BusinessRelType.KEY_WORD.getType());
                robotFriendsRel.setCreateTime(new Date());
                robotFriendsRelService.save(robotFriendsRel);
            }
        }
    }
}