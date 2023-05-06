package com.yh.robot.service;

import java.util.List;
import com.yh.robot.domain.RobotSentence;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 英语长句难句Service接口
 * 
 * @author nssnail
 * @date 2023-03-29
 */
public interface RobotSentenceService extends IService<RobotSentence> {
    /**
     * 查询英语长句难句
     * 
     * @param id 英语长句难句主键
     * @return 英语长句难句
     */
    public RobotSentence selectRobotSentenceById(Long id);

    /**
     * 查询英语长句难句列表
     * 
     * @param robotSentence 英语长句难句
     * @return 英语长句难句集合
     */
    public List<RobotSentence> selectRobotSentenceList(RobotSentence robotSentence);

    /**
     * 新增英语长句难句
     * 
     * @param robotSentence 英语长句难句
     * @return 结果
     */
    public int insertRobotSentence(RobotSentence robotSentence);

    /**
     * 修改英语长句难句
     * 
     * @param robotSentence 英语长句难句
     * @return 结果
     */
    public int updateRobotSentence(RobotSentence robotSentence);

    /**
     * 批量删除英语长句难句
     * 
     * @param ids 需要删除的英语长句难句主键集合
     * @return 结果
     */
    public int deleteRobotSentenceByIds(Long[] ids);

    /**
     * 删除英语长句难句信息
     * 
     * @param id 英语长句难句主键
     * @return 结果
     */
    public int deleteRobotSentenceById(Long id);
}
