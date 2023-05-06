package com.yh.robot.third.xiaobaiapi;

import cn.hutool.core.lang.Assert;
import com.github.pagehelper.util.StringUtil;
import com.yh.file.util.TxFileUtil;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.redis.RedisClient;
import com.yh.robot.redis.RedisKey;
import com.yh.robot.third.xiaobaiapi.result.DeliveryResult;
import com.yh.robot.util.HttpUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 13:23:00
 */
@Component
public class XiaoBaiApi {
    @Resource
    private TxFileUtil txFileUtil;
    @Resource
    private RedisClient redisClient;

    public String getMM() {
        return HttpUtil.get(XiaoBaiApiConstant.MM);
    }

    public String getBoy() {
        return HttpUtil.get(XiaoBaiApiConstant.BOY);
    }


    private String getByteUrl(String key, RedisKey redisKey, String api) {
        String url = redisClient.get(redisKey.getKey(key));
        if (StringUtil.isNotEmpty(url)) {
            return url;
        }
        byte[] bytes = HttpUtil.getByte(String.format(api, key));
        url = txFileUtil.uploadBase64(bytes);
        redisClient.set(redisKey.getKey(key), url, 60 * 60);
        return url;
    }

    public String getJuTou(String userId) {
        return getByteUrl(userId, RedisKey.JU_TOU_IMG, XiaoBaiApiConstant.JU_TOU);
    }

    public String getJuPai(String text) {
        Assert.isTrue(text.length() <= 10, () -> BotException.error("文本长度不能超过10"));
        return getByteUrl(text, RedisKey.JU_PAI, XiaoBaiApiConstant.JU_PAI);
    }

    public String getMMVideo() {
        return HttpUtil.get(XiaoBaiApiConstant.MM_VIDEO);
    }

    public String getDiuTou(String userId) {
        return getByteUrl(userId, RedisKey.DIU_TOU, XiaoBaiApiConstant.DIU_TOU);
    }

    public String getPaoTou(String userId) {
        return getByteUrl(userId, RedisKey.PAO_TOU, XiaoBaiApiConstant.PAO_TOU);
    }

    public String getXieTou(String userId) {
        return getByteUrl(userId, RedisKey.XIE_TOU, XiaoBaiApiConstant.XIE_TOU);
    }

    public String getBiXin(String userId) {
        return getByteUrl(userId, RedisKey.BI_XIN, XiaoBaiApiConstant.BI_XIN);
    }

    public String getQianTou(String qq1, String qq2) {
        String key = qq1 + ":" + qq2;
        String redisKey = RedisKey.QIAN_XIN.getKey(key);
        String url = redisClient.get(redisKey);
        if (StringUtil.isNotEmpty(url)) {
            return url;
        }
        byte[] bytes = HttpUtil.getByte(String.format(XiaoBaiApiConstant.QIAN_TOU, qq1, qq2));
        url = txFileUtil.uploadBase64(bytes);
        redisClient.set(redisKey, url, 60 * 60);
        return url;
    }

    public DeliveryResult getSearchDelivery(String code) {
        DeliveryResult deliveryResult = HttpUtil.get(String.format(XiaoBaiApiConstant.SEARCH_DELIVERY, code), DeliveryResult.class);
        Assert.isTrue(deliveryResult.getStatus().equals("200"), () -> BotException.error(deliveryResult.getMessage()));
        return deliveryResult;
    }
}