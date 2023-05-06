package com.yh.robot.third.xiaobaiapi.result;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月11日 14:44:00
 */
@Data
public class DeliveryResult {

    private RouteInfo routeInfo;
    private String message;
    private String nu;
    private String ischeck;
    private String com;
    private String status;
    private List<Info> data;
    private String state;
    private String condition;
    private String predict;
    private boolean isInternation;
    private boolean noBindMobile;
    private boolean dailyLimit;

    @Data
    public static class RouteInfo {
        private Route from;
        private Route cur;
        private Route to;
    }

    @Data
    public static class Route{
        private String number;
        private String name;
    }

    @Data
    public static class Info{
        private String time;
        private String context;
        private String ftime;
        private String areaCode;
        private String areaName;
        private String status;
    }
}