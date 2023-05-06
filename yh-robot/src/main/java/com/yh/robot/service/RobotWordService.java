package com.yh.robot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.domain.RobotWord;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 16:13:00
 */
public interface RobotWordService extends IService<RobotWord> {

    List<RobotWord> selectRandom(Integer nums);

    String getWord(String word);

    String buildWordStr(RobotWord word);

    String randomWord(Integer nums);
}