package com.yh.robot.service;

import java.util.List;
import com.yh.robot.domain.RobotNotice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 通知公告Service接口
 * 
 * @author nssnail
 * @date 2023-03-30
 */
public interface RobotNoticeService extends IService<RobotNotice> {

    /**
     * 查询通知公告
     * 
     * @param id 通知公告主键
     * @return 通知公告
     */
    public RobotNotice selectRobotNoticeById(Long id);

    /**
     * 查询通知公告列表
     * 
     * @param robotNotice 通知公告
     * @return 通知公告集合
     */
    public List<RobotNotice> selectRobotNoticeList(RobotNotice robotNotice);

    /**
     * 新增通知公告
     * 
     * @param robotNotice 通知公告
     * @return 结果
     */
    public int insertRobotNotice(RobotNotice robotNotice);

    /**
     * 修改通知公告
     * 
     * @param robotNotice 通知公告
     * @return 结果
     */
    public int updateRobotNotice(RobotNotice robotNotice);

    /**
     * 批量删除通知公告
     * 
     * @param ids 需要删除的通知公告主键集合
     * @return 结果
     */
    public int deleteRobotNoticeByIds(Long[] ids);

    /**
     * 删除通知公告信息
     * 
     * @param id 通知公告主键
     * @return 结果
     */
    public int deleteRobotNoticeById(Long id);

    public void notice(Long id);
}
