package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotGroupRel;

/**
 * 群关键字关联Mapper接口
 * 
 * @author nssnail
 * @date 2023-04-04
 */
@Mapper
public interface RobotGroupRelMapper extends BaseMapper<RobotGroupRel> {

    /**
     * 查询群关键字关联
     * 
     * @param id 群关键字关联主键
     * @return 群关键字关联
     */
    public RobotGroupRel selectRobotGroupRelById(Long id);

    /**
     * 查询群关键字关联列表
     * 
     * @param robotGroupRel 群关键字关联
     * @return 群关键字关联集合
     */
    public List<RobotGroupRel> selectRobotGroupRelList(RobotGroupRel robotGroupRel);

    /**
     * 新增群关键字关联
     * 
     * @param robotGroupRel 群关键字关联
     * @return 结果
     */
    public int insertRobotGroupRel(RobotGroupRel robotGroupRel);

    /**
     * 修改群关键字关联
     * 
     * @param robotGroupRel 群关键字关联
     * @return 结果
     */
    public int updateRobotGroupRel(RobotGroupRel robotGroupRel);

    /**
     * 删除群关键字关联
     * 
     * @param id 群关键字关联主键
     * @return 结果
     */
    public int deleteRobotGroupRelById(Long id);

    /**
     * 批量删除群关键字关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotGroupRelByIds(Long[] ids);
}
