package com.yh.robot.params;

import lombok.Data;


/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月07日 10:45:00
 */
@Data
public class MessageTemplateConfig {

    private Integer isHoliday;

    private String time;

    private Long handleId;

    private String param;
}