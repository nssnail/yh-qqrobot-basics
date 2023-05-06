package com.yh.robot.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.utils.DateUtils;
import com.yh.robot.enums.BusinessRelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotFriendsRelMapper;
import com.yh.robot.domain.RobotFriendsRel;
import com.yh.robot.service.RobotFriendsRelService;


/**
 * 好友关联Service业务层处理
 *
 * @author nssnail
 * @date 2023-04-04
 */
@Service
public class RobotFriendsRelServiceImpl extends ServiceImpl<RobotFriendsRelMapper, RobotFriendsRel> implements RobotFriendsRelService {

    @Autowired
    private RobotFriendsRelMapper robotFriendsRelMapper;

    /**
     * 查询好友关联
     *
     * @param id 好友关联主键
     * @return 好友关联
     */
    @Override
    public RobotFriendsRel selectRobotFriendsRelById(Long id) {
        return robotFriendsRelMapper.selectRobotFriendsRelById(id);
    }

    /**
     * 查询好友关联列表
     *
     * @param robotFriendsRel 好友关联
     * @return 好友关联
     */
    @Override
    public List<RobotFriendsRel> selectRobotFriendsRelList(RobotFriendsRel robotFriendsRel) {
        return robotFriendsRelMapper.selectRobotFriendsRelList(robotFriendsRel);
    }

    /**
     * 新增好友关联
     *
     * @param robotFriendsRel 好友关联
     * @return 结果
     */
    @Override
    public int insertRobotFriendsRel(RobotFriendsRel robotFriendsRel) {
        robotFriendsRel.setCreateTime(DateUtils.getNowDate());
        return robotFriendsRelMapper.insertRobotFriendsRel(robotFriendsRel);
    }

    /**
     * 修改好友关联
     *
     * @param robotFriendsRel 好友关联
     * @return 结果
     */
    @Override
    public int updateRobotFriendsRel(RobotFriendsRel robotFriendsRel) {
        return robotFriendsRelMapper.updateRobotFriendsRel(robotFriendsRel);
    }

    /**
     * 批量删除好友关联
     *
     * @param ids 需要删除的好友关联主键
     * @return 结果
     */
    @Override
    public int deleteRobotFriendsRelByIds(Long[] ids) {
        return robotFriendsRelMapper.deleteRobotFriendsRelByIds(ids);
    }

    /**
     * 删除好友关联信息
     *
     * @param id 好友关联主键
     * @return 结果
     */
    @Override
    public int deleteRobotFriendsRelById(Long id) {
        return robotFriendsRelMapper.deleteRobotFriendsRelById(id);
    }


    @Override
    public List<RobotFriendsRel> getFriendsRelByType(BusinessRelType type, Long relId) {
        return this.list(Wrappers.<RobotFriendsRel>lambdaQuery()
                .eq(RobotFriendsRel::getRelType, type.getType())
                .eq(RobotFriendsRel::getRelId, relId));
    }
}
