package com.yh.robot.params;

import com.yh.robot.domain.RobotMessageTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月07日 10:46:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageTemplateParams extends RobotMessageTemplate {

    private MessageTemplateConfig templateConfig;

}