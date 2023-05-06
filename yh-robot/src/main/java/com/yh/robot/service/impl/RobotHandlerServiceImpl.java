package com.yh.robot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yh.common.utils.DateUtils;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.enums.YesNoEnum;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.message.RobotMessageHandler;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.robot.mapper.RobotHandlerMapper;
import com.yh.robot.domain.RobotHandler;
import com.yh.robot.service.RobotHandlerService;


/**
 * 处理器Service业务层处理
 *
 * @author nssnail
 * @date 2023-03-31
 */
@Service
public class RobotHandlerServiceImpl extends ServiceImpl<RobotHandlerMapper, RobotHandler> implements RobotHandlerService {

    @Autowired
    private RobotHandlerMapper robotHandlerMapper;

    @Autowired
    private List<RobotBusinessHandler> handlers;

    /**
     * 查询处理器
     *
     * @param id 处理器主键
     * @return 处理器
     */
    @Override
    public RobotHandler selectRobotHandlerById(Long id) {
        return robotHandlerMapper.selectRobotHandlerById(id);
    }

    /**
     * 查询处理器列表
     *
     * @param robotHandler 处理器
     * @return 处理器
     */
    @Override
    public List<RobotHandler> selectRobotHandlerList(RobotHandler robotHandler) {
        return robotHandlerMapper.selectRobotHandlerList(robotHandler);
    }

    /**
     * 新增处理器
     *
     * @param robotHandler 处理器
     * @return 结果
     */
    @Override
    public int insertRobotHandler(RobotHandler robotHandler) {
        robotHandler.setCreateTime(DateUtils.getNowDate());
        return robotHandlerMapper.insertRobotHandler(robotHandler);
    }

    /**
     * 修改处理器
     *
     * @param robotHandler 处理器
     * @return 结果
     */
    @Override
    public int updateRobotHandler(RobotHandler robotHandler) {
        robotHandler.setUpdateTime(DateUtils.getNowDate());
        return robotHandlerMapper.updateRobotHandler(robotHandler);
    }

    /**
     * 批量删除处理器
     *
     * @param ids 需要删除的处理器主键
     * @return 结果
     */
    @Override
    public int deleteRobotHandlerByIds(Long[] ids) {
        return robotHandlerMapper.deleteRobotHandlerByIds(ids);
    }

    /**
     * 删除处理器信息
     *
     * @param id 处理器主键
     * @return 结果
     */
    @Override
    public int deleteRobotHandlerById(Long id) {
        return robotHandlerMapper.deleteRobotHandlerById(id);
    }

    @Override
    public void sync() {
        List<RobotHandler> data = handlers.stream().map(bean -> {
            RobotHandler robotHandler = new RobotHandler();
            HandleService annotation = AopUtils.getTargetClass(bean).getAnnotation(HandleService.class);
            if (annotation == null) {
                return robotHandler;
            }
            robotHandler.setBeanName(annotation.value());
            robotHandler.setHandleName(annotation.handleName());
            robotHandler.setHandleType(annotation.handleType().getType());
            robotHandler.setSendMsgType(annotation.sendType().getType());
            robotHandler.setStatus(StateEnum.NORMAL.getType());
            robotHandler.setSortNum(1);
            robotHandler.setIsApi(annotation.isApi() ? YesNoEnum.YES.getType() : YesNoEnum.NO.getType());
            robotHandler.setCreateTime(new Date());
            robotHandler.setUpdateTime(new Date());
            return robotHandler;
        }).collect(Collectors.toList());

        List<String> names = this.list().stream().map(RobotHandler::getHandleName).collect(Collectors.toList());
        List<String> dataNames = data.stream().map(RobotHandler::getHandleName).collect(Collectors.toList());

        List<RobotHandler> handlers = this.list();
        List<RobotHandler> insert = data.stream()
                .filter(e -> !names.contains(e.getHandleName()) && StrUtil.isNotBlank(e.getHandleName()))
                .collect(Collectors.toList());

        List<RobotHandler> update = handlers.stream().filter(e -> dataNames.contains(e.getHandleName())).peek(e -> {
            RobotHandler robotHandler = data.stream().filter(item -> e.getHandleName().equals(item.getHandleName())).findFirst().orElse(null);
            if (robotHandler != null) {
                e.setBeanName(robotHandler.getBeanName());
                e.setHandleType(robotHandler.getHandleType());
                e.setSendMsgType(robotHandler.getSendMsgType());
                e.setStatus(StateEnum.NORMAL.getType());
                e.setIsApi(robotHandler.getIsApi());
            }
        }).collect(Collectors.toList());

        List<RobotHandler> delete = handlers.stream().filter(e -> !dataNames.contains(e.getHandleName())).collect(Collectors.toList());


        if (CollUtil.isNotEmpty(insert)) {
            this.saveBatch(insert);
        }
        if (CollUtil.isNotEmpty(update)) {
            update.forEach(this::updateById);
        }
        if (CollUtil.isNotEmpty(delete)) {
            this.removeByIds(delete.stream().map(RobotHandler::getId).collect(Collectors.toList()));
        }


    }
}
