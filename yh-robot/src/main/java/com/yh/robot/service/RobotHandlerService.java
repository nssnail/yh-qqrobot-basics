package com.yh.robot.service;

import java.util.List;
import com.yh.robot.domain.RobotHandler;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 处理器Service接口
 * 
 * @author nssnail
 * @date 2023-03-31
 */
public interface RobotHandlerService extends IService<RobotHandler> {

    /**
     * 查询处理器
     * 
     * @param id 处理器主键
     * @return 处理器
     */
    public RobotHandler selectRobotHandlerById(Long id);

    /**
     * 查询处理器列表
     * 
     * @param robotHandler 处理器
     * @return 处理器集合
     */
    public List<RobotHandler> selectRobotHandlerList(RobotHandler robotHandler);

    /**
     * 新增处理器
     * 
     * @param robotHandler 处理器
     * @return 结果
     */
    public int insertRobotHandler(RobotHandler robotHandler);

    /**
     * 修改处理器
     * 
     * @param robotHandler 处理器
     * @return 结果
     */
    public int updateRobotHandler(RobotHandler robotHandler);

    /**
     * 批量删除处理器
     * 
     * @param ids 需要删除的处理器主键集合
     * @return 结果
     */
    public int deleteRobotHandlerByIds(Long[] ids);

    /**
     * 删除处理器信息
     * 
     * @param id 处理器主键
     * @return 结果
     */
    public int deleteRobotHandlerById(Long id);


    void sync();
}
