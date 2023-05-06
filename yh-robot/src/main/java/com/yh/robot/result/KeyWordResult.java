package com.yh.robot.result;

import com.yh.robot.domain.RobotKeyWord;
import lombok.Data;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月31日 18:10:00
 */
@Data
public class KeyWordResult extends RobotKeyWord {
    private List<String> contents;

    private String images;
}