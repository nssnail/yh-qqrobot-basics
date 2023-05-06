package com.yh.robot.service.impl;

import java.util.List;
import com.yh.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotKeyWordRelMapper;
import com.yh.robot.domain.RobotKeyWordRel;
import com.yh.robot.service.RobotKeyWordRelService;


/**
 * 关键词关联Service业务层处理
 * 
 * @author nssnail
 * @date 2023-03-31
 */
@Service
public class RobotKeyWordRelServiceImpl extends ServiceImpl<RobotKeyWordRelMapper, RobotKeyWordRel> implements RobotKeyWordRelService {

    @Autowired
    private RobotKeyWordRelMapper robotKeyWordRelMapper;

    /**
     * 查询关键词关联
     * 
     * @param id 关键词关联主键
     * @return 关键词关联
     */
    @Override
    public RobotKeyWordRel selectRobotKeyWordRelById(Long id)
    {
        return robotKeyWordRelMapper.selectRobotKeyWordRelById(id);
    }

    /**
     * 查询关键词关联列表
     * 
     * @param robotKeyWordRel 关键词关联
     * @return 关键词关联
     */
    @Override
    public List<RobotKeyWordRel> selectRobotKeyWordRelList(RobotKeyWordRel robotKeyWordRel)
    {
        return robotKeyWordRelMapper.selectRobotKeyWordRelList(robotKeyWordRel);
    }

    /**
     * 新增关键词关联
     * 
     * @param robotKeyWordRel 关键词关联
     * @return 结果
     */
    @Override
    public int insertRobotKeyWordRel(RobotKeyWordRel robotKeyWordRel)
    {
        robotKeyWordRel.setCreateTime(DateUtils.getNowDate());
        return robotKeyWordRelMapper.insertRobotKeyWordRel(robotKeyWordRel);
    }

    /**
     * 修改关键词关联
     * 
     * @param robotKeyWordRel 关键词关联
     * @return 结果
     */
    @Override
    public int updateRobotKeyWordRel(RobotKeyWordRel robotKeyWordRel)
    {
        return robotKeyWordRelMapper.updateRobotKeyWordRel(robotKeyWordRel);
    }

    /**
     * 批量删除关键词关联
     * 
     * @param ids 需要删除的关键词关联主键
     * @return 结果
     */
    @Override
    public int deleteRobotKeyWordRelByIds(Long[] ids)
    {
        return robotKeyWordRelMapper.deleteRobotKeyWordRelByIds(ids);
    }

    /**
     * 删除关键词关联信息
     * 
     * @param id 关键词关联主键
     * @return 结果
     */
    @Override
    public int deleteRobotKeyWordRelById(Long id)
    {
        return robotKeyWordRelMapper.deleteRobotKeyWordRelById(id);
    }
}
