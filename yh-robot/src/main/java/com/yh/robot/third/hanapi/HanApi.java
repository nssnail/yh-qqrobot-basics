package com.yh.robot.third.hanapi;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.util.HttpUtil;
import org.springframework.stereotype.Component;

/**
 * @ClassName HanApi
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 23:06
 * @Version 1.0
 */
@Component
public class HanApi {


    public String getMMImage() {
        String result = HttpUtil.get(HanApiConstant.MM_IMAGE_URL);
        HanApiResult hanApiResult = JSONObject.parseObject(result, HanApiResult.class);
        if (hanApiResult.getSuccess()) {
            return hanApiResult.getImgurl();
        }
        return "";
    }

    public HanApiResult getEnglish() {
        String result = HttpUtil.get(HanApiConstant.EN_URL);
        HanApiResult hanApiResult = JSONObject.parseObject(result, HanApiResult.class);
        if (hanApiResult.getSuccess()) {
            return hanApiResult;
        }
        return null;
    }


    public String getMOYUImage() {
        String result = HttpUtil.get(HanApiConstant.MOYU_API);
        HanApiResult hanApiResult = JSONObject.parseObject(result, HanApiResult.class);
        if (hanApiResult.getSuccess()) {
            return hanApiResult.getUrl();
        }
        return "";
    }


    public HanApiArrayResult getWeiBoHot() {
        String result = HttpUtil.get(HanApiConstant.WEIBO_HOT_API);
        HanApiArrayResult hanApiResult = JSONObject.parseObject(result, HanApiArrayResult.class);
        if (hanApiResult.getSuccess()) {
            return hanApiResult;
        }
        return null;
    }


    public HanApiArrayResult getNews() {
        String result = HttpUtil.get(HanApiConstant.NEWS);
        HanApiArrayResult hanApiResult = JSONObject.parseObject(result, HanApiArrayResult.class);
        if (hanApiResult.getSuccess()) {
            return hanApiResult;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(HttpUtil.get(HanApiConstant.NEWS,HanApiArrayResult.class));
    }

}
