package com.yh.robot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.exception.ServiceException;
import com.yh.common.utils.DateUtils;
import com.yh.robot.client.RobotClient;
import com.yh.robot.domain.RobotGroupRel;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.message.result.GetGroupResult;
import com.yh.robot.params.GroupRelParams;
import com.yh.robot.service.RobotGroupRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotGroupMapper;
import com.yh.robot.domain.RobotGroup;
import com.yh.robot.service.RobotGroupService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 群列Service业务层处理
 *
 * @author nssnail
 * @date 2023-03-30
 */
@Service
public class RobotGroupServiceImpl extends ServiceImpl<RobotGroupMapper, RobotGroup> implements RobotGroupService {

    @Autowired
    private RobotGroupMapper robotGroupMapper;
    @Resource
    private RobotGroupRelService robotGroupRelService;
    @Resource
    private RobotClient robotClient;
    @Value("${qq}")
    private Long qq;

    /**
     * 查询群列
     *
     * @param id 群列主键
     * @return 群列
     */
    @Override
    public RobotGroup selectRobotGroupById(Long id) {
        return robotGroupMapper.selectRobotGroupById(id);
    }

    /**
     * 查询群列列表
     *
     * @param robotGroup 群列
     * @return 群列
     */
    @Override
    public List<RobotGroup> selectRobotGroupList(RobotGroup robotGroup) {
        return robotGroupMapper.selectRobotGroupList(robotGroup);
    }

    /**
     * 新增群列
     *
     * @param robotGroup 群列
     * @return 结果
     */
    @Override
    public int insertRobotGroup(RobotGroup robotGroup) {
        robotGroup.setCreateTime(DateUtils.getNowDate());
        return robotGroupMapper.insertRobotGroup(robotGroup);
    }

    /**
     * 修改群列
     *
     * @param robotGroup 群列
     * @return 结果
     */
    @Override
    public int updateRobotGroup(RobotGroup robotGroup) {
        robotGroup.setUpdateTime(DateUtils.getNowDate());
        return robotGroupMapper.updateRobotGroup(robotGroup);
    }

    /**
     * 批量删除群列
     *
     * @param ids 需要删除的群列主键
     * @return 结果
     */
    @Override
    public int deleteRobotGroupByIds(Long[] ids) {
        return robotGroupMapper.deleteRobotGroupByIds(ids);
    }

    /**
     * 删除群列信息
     *
     * @param id 群列主键
     * @return 结果
     */
    @Override
    public int deleteRobotGroupById(Long id) {
        return robotGroupMapper.deleteRobotGroupById(id);
    }

    @Override
    public void toggle(Long id) {
        RobotGroup robotGroup = robotGroupMapper.selectById(id);
        Assert.isTrue(ObjectUtil.isNotEmpty(robotGroup), () -> new ServiceException("找不到对应的好友"));
        robotGroup.setStatus(robotGroup.getStatus() == StateEnum.NORMAL.getType() ? StateEnum.DISABLE.getType() : StateEnum.NORMAL.getType());
        robotGroupMapper.updateRobotGroup(robotGroup);
    }

    @Override
    @Transactional
    public void insertOrUpdateGroupRel(GroupRelParams groupRelParams) {
        List<Long> relIds = groupRelParams.getRelIds();
        robotGroupRelService.remove(Wrappers.<RobotGroupRel>lambdaQuery()
                .eq(RobotGroupRel::getGroupId, groupRelParams.getGroupId())
                .eq(RobotGroupRel::getRelType, groupRelParams.getRelType()));
        if (CollUtil.isNotEmpty(relIds)) {
            List<RobotGroupRel> robotGroupRels = relIds.stream().map(id -> {
                RobotGroupRel robotGroupRel = new RobotGroupRel();
                robotGroupRel.setGroupId(groupRelParams.getGroupId());
                robotGroupRel.setRelId(id);
                robotGroupRel.setRelType(groupRelParams.getRelType());
                return robotGroupRel;
            }).collect(Collectors.toList());
            robotGroupRelService.saveBatch(robotGroupRels);
        }
    }

    @Override
    public List<RobotGroupRel> getRel(GroupRelParams groupRelParams) {
        return robotGroupRelService.list(Wrappers.<RobotGroupRel>lambdaQuery()
                .eq(RobotGroupRel::getGroupId, groupRelParams.getGroupId())
                .eq(RobotGroupRel::getRelType, groupRelParams.getRelType()));
    }

    @Override
    public void syncGroups() {
        GetGroupResult getGroupResult = robotClient.getGroupList();
        List<GetGroupResult.Group> groupList = getGroupResult.getData();
        if (CollUtil.isNotEmpty(groupList)) {
            List<RobotGroup> list = this.list();
            List<GetGroupResult.Group> newList = groupList.stream().filter(e -> {
                for (RobotGroup robotGroup : list) {
                    if (robotGroup.getGroupId().equals(e.getGroupId())) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(newList)) {
                List<RobotGroup> inserts = newList.stream().map(item -> {
                    RobotGroup robotGroup = new RobotGroup();
                    robotGroup.setGroupId(item.getGroupId());
                    robotGroup.setGroupName(item.getGroupName());
                    robotGroup.setGroupMemo(item.getGroupMemo());
                    robotGroup.setGroupCreateTime(item.getGroupCreateTime());
                    robotGroup.setSelfId(qq);
                    robotGroup.setStatus(StateEnum.DISABLE.getType());
                    robotGroup.setCreateTime(new Date());
                    robotGroup.setCreateTime(new Date());
                    return robotGroup;
                }).collect(Collectors.toList());
                this.saveBatch(inserts);
            }
        }
    }
}
