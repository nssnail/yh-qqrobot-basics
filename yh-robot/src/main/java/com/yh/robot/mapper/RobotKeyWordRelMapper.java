package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotKeyWordRel;

/**
 * 关键词关联Mapper接口
 * 
 * @author nssnail
 * @date 2023-03-31
 */
@Mapper
public interface RobotKeyWordRelMapper extends BaseMapper<RobotKeyWordRel> {

    /**
     * 查询关键词关联
     * 
     * @param id 关键词关联主键
     * @return 关键词关联
     */
    public RobotKeyWordRel selectRobotKeyWordRelById(Long id);

    /**
     * 查询关键词关联列表
     * 
     * @param robotKeyWordRel 关键词关联
     * @return 关键词关联集合
     */
    public List<RobotKeyWordRel> selectRobotKeyWordRelList(RobotKeyWordRel robotKeyWordRel);

    /**
     * 新增关键词关联
     * 
     * @param robotKeyWordRel 关键词关联
     * @return 结果
     */
    public int insertRobotKeyWordRel(RobotKeyWordRel robotKeyWordRel);

    /**
     * 修改关键词关联
     * 
     * @param robotKeyWordRel 关键词关联
     * @return 结果
     */
    public int updateRobotKeyWordRel(RobotKeyWordRel robotKeyWordRel);

    /**
     * 删除关键词关联
     * 
     * @param id 关键词关联主键
     * @return 结果
     */
    public int deleteRobotKeyWordRelById(Long id);

    /**
     * 批量删除关键词关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotKeyWordRelByIds(Long[] ids);
}
