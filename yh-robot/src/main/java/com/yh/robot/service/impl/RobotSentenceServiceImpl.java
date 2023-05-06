package com.yh.robot.service.impl;

import java.util.List;

import com.yh.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotSentenceMapper;
import com.yh.robot.domain.RobotSentence;
import com.yh.robot.service.RobotSentenceService;


/**
 * 英语长句难句Service业务层处理
 *
 * @author nssnail
 * @date 2023-03-29
 */
@Service
public class RobotSentenceServiceImpl extends ServiceImpl<RobotSentenceMapper, RobotSentence> implements RobotSentenceService {
    @Autowired
    private RobotSentenceMapper robotSentenceMapper;

    /**
     * 查询英语长句难句
     *
     * @param id 英语长句难句主键
     * @return 英语长句难句
     */
    @Override
    public RobotSentence selectRobotSentenceById(Long id) {
        return robotSentenceMapper.selectRobotSentenceById(id);
    }

    /**
     * 查询英语长句难句列表
     *
     * @param robotSentence 英语长句难句
     * @return 英语长句难句
     */
    @Override
    public List<RobotSentence> selectRobotSentenceList(RobotSentence robotSentence) {
        return robotSentenceMapper.selectRobotSentenceList(robotSentence);
    }

    /**
     * 新增英语长句难句
     *
     * @param robotSentence 英语长句难句
     * @return 结果
     */
    @Override
    public int insertRobotSentence(RobotSentence robotSentence) {
        robotSentence.setCreateTime(DateUtils.getNowDate());
        return robotSentenceMapper.insertRobotSentence(robotSentence);
    }

    /**
     * 修改英语长句难句
     *
     * @param robotSentence 英语长句难句
     * @return 结果
     */
    @Override
    public int updateRobotSentence(RobotSentence robotSentence) {
        return robotSentenceMapper.updateRobotSentence(robotSentence);
    }

    /**
     * 批量删除英语长句难句
     *
     * @param ids 需要删除的英语长句难句主键
     * @return 结果
     */
    @Override
    public int deleteRobotSentenceByIds(Long[] ids) {
        return robotSentenceMapper.deleteRobotSentenceByIds(ids);
    }

    /**
     * 删除英语长句难句信息
     *
     * @param id 英语长句难句主键
     * @return 结果
     */
    @Override
    public int deleteRobotSentenceById(Long id) {
        return robotSentenceMapper.deleteRobotSentenceById(id);
    }
}
