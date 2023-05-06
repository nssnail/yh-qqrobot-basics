package com.yh.robot.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotWordMapper;
import com.yh.robot.domain.RobotWord;
import com.yh.robot.service.RobotWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 16:14:00
 */
@Service
public class RobotWordServiceImpl extends ServiceImpl<RobotWordMapper, RobotWord> implements RobotWordService {

    @Resource
    private RobotWordMapper robotWordMapper;

    @Override
    public List<RobotWord> selectRandom(Integer nums) {
        return robotWordMapper.selectRandom(nums);
    }

    @Override
    public String getWord(String word) {
        RobotWord robotWord = robotWordMapper.selectOne(Wrappers.<RobotWord>lambdaQuery().eq(RobotWord::getContent, word));
        if (ObjectUtils.isEmpty(robotWord)) {
            return null;
        }
        return buildWordStr(robotWord);
    }

    @Override
    public String buildWordStr(RobotWord word) {
        return String.format("单词：%s  %s\n%s,%s", word.getContent(), word.getPhonetic(), word.getWordtype(), word.getExplaination());
    }


    @Override
    public String randomWord(Integer nums) {
        if (nums >= 300) {
            return "数量不能超过300";
        }
        List<RobotWord> robotWords = selectRandom(nums);
        StringBuilder sb = new StringBuilder();
        robotWords.forEach(e -> {
            sb.append(String.format("%s，%s\n", e.getContent(), e.getExplaination()));
        });
        return sb.toString();
    }
}