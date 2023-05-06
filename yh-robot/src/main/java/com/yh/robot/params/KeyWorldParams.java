package com.yh.robot.params;

import com.yh.robot.domain.RobotKeyWord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月31日 17:16:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeyWorldParams extends RobotKeyWord {
    private List<String> contents;

    private String images;
}