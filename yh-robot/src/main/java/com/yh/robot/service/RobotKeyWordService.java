package com.yh.robot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.params.KeyWorldParams;
import com.yh.robot.result.KeyWordResult;

import java.util.List;

/**
 * @ClassName RobotKeyWordService
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 22:31
 * @Version 1.0
 */
public interface RobotKeyWordService extends IService<RobotKeyWord> {

    RobotKeyWord getRobotKeyWord(String keyWord);

    List<RobotKeyWord> getKeyWordByHandleType(HandleType handleType);


    /**
     * 查询关键字管理
     *
     * @param id 关键字管理主键
     * @return 关键字管理
     */
    public KeyWordResult selectRobotKeyWordById(Long id);

    /**
     * 查询关键字管理列表
     *
     * @param robotKeyWord 关键字管理
     * @return 关键字管理集合
     */
    public List<RobotKeyWord> selectRobotKeyWordList(RobotKeyWord robotKeyWord);

    /**
     * 新增关键字管理
     *
     * @param keyWorldParams 关键字管理
     * @return 结果
     */
    public int insertRobotKeyWord(KeyWorldParams keyWorldParams);

    /**
     * 修改关键字管理
     *
     * @param keyWorldParams 关键字管理
     * @return 结果
     */
    public int updateRobotKeyWord(KeyWorldParams keyWorldParams);

    /**
     * 批量删除关键字管理
     *
     * @param ids 需要删除的关键字管理主键集合
     * @return 结果
     */
    public int deleteRobotKeyWordByIds(Long[] ids);

    /**
     * 删除关键字管理信息
     *
     * @param id 关键字管理主键
     * @return 结果
     */
    public int deleteRobotKeyWordById(Long id);
}
