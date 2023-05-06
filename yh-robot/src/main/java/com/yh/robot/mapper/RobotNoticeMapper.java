package com.yh.robot.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.yh.robot.domain.RobotNotice;

/**
 * 通知公告Mapper接口
 * 
 * @author nssnail
 * @date 2023-03-30
 */
@Mapper
public interface RobotNoticeMapper extends BaseMapper<RobotNotice> {

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
     * 删除通知公告
     * 
     * @param id 通知公告主键
     * @return 结果
     */
    public int deleteRobotNoticeById(Long id);

    /**
     * 批量删除通知公告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRobotNoticeByIds(Long[] ids);
}
