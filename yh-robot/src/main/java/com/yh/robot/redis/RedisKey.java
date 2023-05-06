package com.yh.robot.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName RedisKey
 * @Description TODO
 * @Author Y
 * @Date 2023/3/19 17:15
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum  RedisKey {

    MESSAGE("messageUnionKey","消息幂等唯一键"),
    CHAT_SESSION_ID("chatSessionId","机器人session"),
    CHAT_REAL_ID("chatRealId","机器人真实session"),
    JU_TOU_IMG("juTouImgKey","举头图片key"),
    JU_PAI("juPaiImageKey","举牌图片key"),
    DIU_TOU("piuTouImgKey","丢头图,片key"),
    PAO_TOU("paoTouImgKey","跑头图片key"),
    XIE_TOU("xieTouImgKey","谢谢头像图片key"),
    BI_XIN("biXinImgKey","比心图片key"),
    QIAN_XIN("qianTouImgKey","牵头图片key"),
    ;

    private final String key;

    private final String name;

    public String getKey(String value){
        return this.getKey()+":"+value;
    }

}
