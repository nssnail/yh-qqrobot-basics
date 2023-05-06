package com.yh.robot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yh.robot.domain.RobotKeyWord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName RobotKeyWordMapper
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 22:30
 * @Version 1.0
 */
@Mapper
public interface RobotKeyWordMapper extends BaseMapper<RobotKeyWord> {

    /**
     * 查询关键字管理
     *
     * @param id 关键字管理主键
     * @return 关键字管理
     */
    public RobotKeyWord selectRobotKeyWordById(Long id);

    /**
     * 查询关键字管理列表
     *
     * @param robotKeyWord 关键字管理
     * @return 关键字管理集合
     */
    public List<RobotKeyWord> selectRobotKeyWordList(RobotKeyWord robotKeyWord);

    /**
     * 新增关键字管理
     *
     * @param robotKeyWord 关键字管理
     * @return 结果
     */
    public int insertRobotKeyWord(RobotKeyWord robotKeyWord);

    /**
     * 修改关键字管理
     *
     * @param robotKeyWord 关键字管理
     * @return 结果
     */
    public int updateRobotKeyWord(RobotKeyWord robotKeyWord);

    /**
     * 删除关键字管理
     *
     * @param id 关键字管理主键
     * @return 结果
     */
    public int deleteRobotKeyWordById(Long id);

    /**
     * 批量删除关键字管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotKeyWordByIds(Long[] ids);
}
