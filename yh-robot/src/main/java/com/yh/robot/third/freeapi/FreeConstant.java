package com.yh.robot.third.freeapi;

/**
 * @ClassName FreeConstant
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 23:19
 * @Version 1.0
 */
public class FreeConstant {

    /**
     * 每日一言
     */
    public static final String YIYAN = "https://v.api.aa1.cn/api/yiyan/index.php";

    /**
     * 天气
     */
    public static final String TIANQI = "https://www.mxnzp.com/api/weather/current/%s";

    public static String getTianqi(String city,String param){
        return String.format(TIANQI,city)+param;
    }

    /**
     * 抖音美女
     */
    public static final String DOUYIN_MM = "https://zj.v.api.aa1.cn/api/video_dyv2";
}
