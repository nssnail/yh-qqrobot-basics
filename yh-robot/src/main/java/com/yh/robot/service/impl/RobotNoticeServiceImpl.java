package com.yh.robot.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.common.utils.DateUtils;
import com.yh.robot.client.RobotClient;
import com.yh.robot.domain.RobotFriends;
import com.yh.robot.domain.RobotGroup;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.service.RobotFriendsService;
import com.yh.robot.service.RobotGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotNoticeMapper;
import com.yh.robot.domain.RobotNotice;
import com.yh.robot.service.RobotNoticeService;

import javax.annotation.Resource;


/**
 * 通知公告Service业务层处理
 *
 * @author nssnail
 * @date 2023-03-30
 */
@Service
public class RobotNoticeServiceImpl extends ServiceImpl<RobotNoticeMapper, RobotNotice> implements RobotNoticeService {

    @Autowired
    private RobotNoticeMapper robotNoticeMapper;
    @Resource
    private RobotGroupService robotGroupService;
    @Resource
    private RobotFriendsService robotFriendsService;
    @Value("${qq}")
    private Long qq;
    @Resource
    private RobotClient robotClient;

    /**
     * 查询通知公告
     *
     * @param id 通知公告主键
     * @return 通知公告
     */
    @Override
    public RobotNotice selectRobotNoticeById(Long id) {
        return robotNoticeMapper.selectRobotNoticeById(id);
    }

    /**
     * 查询通知公告列表
     *
     * @param robotNotice 通知公告
     * @return 通知公告
     */
    @Override
    public List<RobotNotice> selectRobotNoticeList(RobotNotice robotNotice) {
        return robotNoticeMapper.selectRobotNoticeList(robotNotice);
    }

    /**
     * 新增通知公告
     *
     * @param robotNotice 通知公告
     * @return 结果
     */
    @Override
    public int insertRobotNotice(RobotNotice robotNotice) {
        robotNotice.setCreateTime(DateUtils.getNowDate());
        return robotNoticeMapper.insertRobotNotice(robotNotice);
    }

    /**
     * 修改通知公告
     *
     * @param robotNotice 通知公告
     * @return 结果
     */
    @Override
    public int updateRobotNotice(RobotNotice robotNotice) {
        robotNotice.setUpdateTime(DateUtils.getNowDate());
        return robotNoticeMapper.updateRobotNotice(robotNotice);
    }

    /**
     * 批量删除通知公告
     *
     * @param ids 需要删除的通知公告主键
     * @return 结果
     */
    @Override
    public int deleteRobotNoticeByIds(Long[] ids) {
        return robotNoticeMapper.deleteRobotNoticeByIds(ids);
    }

    /**
     * 删除通知公告信息
     *
     * @param id 通知公告主键
     * @return 结果
     */
    @Override
    public int deleteRobotNoticeById(Long id) {
        return robotNoticeMapper.deleteRobotNoticeById(id);
    }

    @Override
    public void notice(Long id) {
        RobotNotice robotNotice = robotNoticeMapper.selectById(id);
        List<RobotGroup> groups = robotGroupService.list(Wrappers.<RobotGroup>lambdaQuery()
                .eq(RobotGroup::getSelfId, qq)
                .eq(RobotGroup::getStatus, StateEnum.NORMAL.getType()));
        List<RobotFriends> friends = robotFriendsService.list(Wrappers.<RobotFriends>lambdaQuery()
                .eq(RobotFriends::getSelfId, qq)
                .eq(RobotFriends::getStatus, StateEnum.NORMAL.getType()));
        groups.forEach(e->{
            robotClient.sendGroupMessage(robotNotice.getNotice(),e.getGroupId());
        });
        friends.forEach(e->{
            robotClient.sendPrivateMessage(robotNotice.getNotice(),e.getQq());
        });
    }
}
