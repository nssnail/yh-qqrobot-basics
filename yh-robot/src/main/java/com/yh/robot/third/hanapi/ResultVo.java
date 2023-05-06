package com.yh.robot.third.hanapi;

import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 13:00:00
 */
public class ResultVo {

    @Data
    public static class WeiBo{
        private String title;
        private String url;
        private String hot;
    }
}