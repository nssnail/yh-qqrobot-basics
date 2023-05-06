package com.yh.robot.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.utils.DateUtils;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.enums.BusinessRelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotGroupRelMapper;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.service.RobotGroupRelService;


/**
 * 群关键字关联Service业务层处理
 *
 * @author nssnail
 * @date 2023-04-04
 */
@Service
public class RobotGroupRelServiceImpl extends ServiceImpl<RobotGroupRelMapper, RobotGroupRel> implements RobotGroupRelService {

    @Autowired
    private RobotGroupRelMapper robotGroupRelMapper;

    /**
     * 查询群关键字关联
     *
     * @param id 群关键字关联主键
     * @return 群关键字关联
     */
    @Override
    public RobotGroupRel selectRobotGroupRelById(Long id) {
        return robotGroupRelMapper.selectRobotGroupRelById(id);
    }

    /**
     * 查询群关键字关联列表
     *
     * @param robotGroupRel 群关键字关联
     * @return 群关键字关联
     */
    @Override
    public List<RobotGroupRel> selectRobotGroupRelList(RobotGroupRel robotGroupRel) {
        return robotGroupRelMapper.selectRobotGroupRelList(robotGroupRel);
    }

    /**
     * 新增群关键字关联
     *
     * @param robotGroupRel 群关键字关联
     * @return 结果
     */
    @Override
    public int insertRobotGroupRel(RobotGroupRel robotGroupRel) {
        robotGroupRel.setCreateTime(DateUtils.getNowDate());
        return robotGroupRelMapper.insertRobotGroupRel(robotGroupRel);
    }

    /**
     * 修改群关键字关联
     *
     * @param robotGroupRel 群关键字关联
     * @return 结果
     */
    @Override
    public int updateRobotGroupRel(RobotGroupRel robotGroupRel) {
        return robotGroupRelMapper.updateRobotGroupRel(robotGroupRel);
    }

    /**
     * 批量删除群关键字关联
     *
     * @param ids 需要删除的群关键字关联主键
     * @return 结果
     */
    @Override
    public int deleteRobotGroupRelByIds(Long[] ids) {
        return robotGroupRelMapper.deleteRobotGroupRelByIds(ids);
    }

    /**
     * 删除群关键字关联信息
     *
     * @param id 群关键字关联主键
     * @return 结果
     */
    @Override
    public int deleteRobotGroupRelById(Long id) {
        return robotGroupRelMapper.deleteRobotGroupRelById(id);
    }

    @Override
    public List<RobotGroupRel> getGroupRelByType(BusinessRelType type,Long relId) {
       return this.list(Wrappers.<RobotGroupRel>lambdaQuery()
                .eq(RobotGroupRel::getRelType, type.getType())
               .eq(RobotGroupRel::getRelId, relId));
    }
}
