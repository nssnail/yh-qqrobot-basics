package com.yh.robot.service;

import java.util.List;

import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.domain.RobotGroupRel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.enums.BusinessRelType;

/**
 * 群关键字关联Service接口
 * 
 * @author nssnail
 * @date 2023-04-04
 */
public interface RobotGroupRelService extends IService<RobotGroupRel> {

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
     * 批量删除群关键字关联
     * 
     * @param ids 需要删除的群关键字关联主键集合
     * @return 结果
     */
    public int deleteRobotGroupRelByIds(Long[] ids);

    /**
     * 删除群关键字关联信息
     * 
     * @param id 群关键字关联主键
     * @return 结果
     */
    public int deleteRobotGroupRelById(Long id);

    List<RobotGroupRel> getGroupRelByType(BusinessRelType type,Long relId);
}
