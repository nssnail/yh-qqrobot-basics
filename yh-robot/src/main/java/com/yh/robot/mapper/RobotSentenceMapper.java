package com.yh.robot.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotSentence;

/**
 * 英语长句难句Mapper接口
 *
 * @author nssnail
 * @date 2023-03-29
 */
@Mapper
public interface RobotSentenceMapper extends BaseMapper<RobotSentence> {
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
     * 删除英语长句难句
     *
     * @param id 英语长句难句主键
     * @return 结果
     */
    public int deleteRobotSentenceById(Long id);

    /**
     * 批量删除英语长句难句
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotSentenceByIds(Long[] ids);
}
