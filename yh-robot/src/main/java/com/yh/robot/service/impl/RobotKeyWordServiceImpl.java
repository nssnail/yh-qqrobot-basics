package com.yh.robot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.common.exception.ServiceException;
import com.yh.common.utils.DateUtils;
import com.yh.common.utils.StringUtils;
import com.yh.robot.domain.RobotHandler;
import com.yh.robot.domain.RobotKeyWordRel;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.RelType;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.mapper.RobotKeyWordMapper;
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.params.KeyWorldParams;
import com.yh.robot.result.KeyWordResult;
import com.yh.robot.service.RobotHandlerService;
import com.yh.robot.service.RobotKeyWordRelService;
import com.yh.robot.service.RobotKeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RobotKeyWordServiceImpl
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 22:32
 * @Version 1.0
 */
@Service
public class RobotKeyWordServiceImpl extends ServiceImpl<RobotKeyWordMapper, RobotKeyWord> implements RobotKeyWordService {

    @Resource
    private RobotKeyWordMapper robotKeyWordMapper;
    @Resource
    private RobotKeyWordRelService robotKeyWordRelService;
    @Resource
    private RobotHandlerService robotHandlerService;

    @Override
    public RobotKeyWord getRobotKeyWord(String keyWord) {
        List<RobotKeyWord> robotKeyWords = robotKeyWordMapper.selectList(Wrappers.<RobotKeyWord>lambdaQuery()
                .eq(RobotKeyWord::getKeyWord, keyWord)
                .eq(RobotKeyWord::getState, StateEnum.NORMAL.getType()));
        return CollectionUtils.isNotEmpty(robotKeyWords) ? robotKeyWords.get(0) : null;
    }

    @Override
    public List<RobotKeyWord> getKeyWordByHandleType(HandleType handleType) {
        return robotKeyWordMapper.selectList(Wrappers.<RobotKeyWord>lambdaQuery()
                .eq(RobotKeyWord::getState, StateEnum.NORMAL.getType())
                .eq(handleType != null, RobotKeyWord::getHandleType, handleType.getType()));
    }


    /**
     * 查询关键字管理
     *
     * @param id 关键字管理主键
     * @return 关键字管理
     */
    @Override
    public KeyWordResult selectRobotKeyWordById(Long id) {
        RobotKeyWord robotKeyWord = robotKeyWordMapper.selectRobotKeyWordById(id);
        KeyWordResult keyWordResult = new KeyWordResult();
        BeanUtil.copyProperties(robotKeyWord, keyWordResult);
        List<RobotKeyWordRel> list = robotKeyWordRelService.list(Wrappers.<RobotKeyWordRel>lambdaQuery().eq(RobotKeyWordRel::getKeyWordId, robotKeyWord.getId()));
        keyWordResult.setContents(list.stream().filter(item->item.getRelType().equals(RelType.TEXT.getType())).map(RobotKeyWordRel::getContent).collect(Collectors.toList()));
        keyWordResult.setImages(list.stream().filter(item->item.getRelType().equals(RelType.IMAGE.getType())).map(RobotKeyWordRel::getContent).collect(Collectors.joining(",")));
        return keyWordResult;
    }

    /**
     * 查询关键字管理列表
     *
     * @param robotKeyWord 关键字管理
     * @return 关键字管理
     */
    @Override
    public List<RobotKeyWord> selectRobotKeyWordList(RobotKeyWord robotKeyWord) {
        return robotKeyWordMapper.selectRobotKeyWordList(robotKeyWord);
    }

    /**
     * 新增关键字管理
     *
     * @param keyWorldParams 关键字管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRobotKeyWord(KeyWorldParams keyWorldParams) {
        Long count = robotKeyWordMapper.selectCount(Wrappers
                .<RobotKeyWord>lambdaQuery().eq(RobotKeyWord::getKeyWord, keyWorldParams.getKeyWord()));
        Assert.isTrue(count == 0, () -> new ServiceException("关键字不能重复"));
        RobotHandler robotHandler = robotHandlerService.getById(keyWorldParams.getHandleId());
        Assert.isTrue(ObjectUtil.isNotEmpty(robotHandler), () -> new ServiceException("找不到对应处理器"));
        keyWorldParams.setBeanName(robotHandler.getBeanName());
        keyWorldParams.setHandleType(robotHandler.getHandleType());
        keyWorldParams.setSendMsgType(robotHandler.getSendMsgType());
        keyWorldParams.setState(StateEnum.NORMAL.getType());
        keyWorldParams.setCreateTime(DateUtils.getNowDate());
        int i = robotKeyWordMapper.insertRobotKeyWord(keyWorldParams);
        insertRel(keyWorldParams);
        return i;
    }

    private void insertRel(KeyWorldParams keyWorldParams) {
        List<RobotKeyWordRel> robotKeyWordRels = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(keyWorldParams.getContents())) {
            robotKeyWordRels = keyWorldParams.getContents().stream().map(str -> {
                RobotKeyWordRel robotKeyWordRel = new RobotKeyWordRel();
                robotKeyWordRel.setKeyWordId(keyWorldParams.getId().longValue());
                robotKeyWordRel.setContent(str);
                robotKeyWordRel.setRelType(RelType.TEXT.getType());
                robotKeyWordRel.setCreateTime(new Date());
                return robotKeyWordRel;

            }).collect(Collectors.toList());
        } else if (StringUtils.isNoneBlank(keyWorldParams.getImages())) {
            String[] split = keyWorldParams.getImages().split(",");
            for (String s : split) {
                RobotKeyWordRel robotKeyWordRel = new RobotKeyWordRel();
                robotKeyWordRel.setKeyWordId(keyWorldParams.getId().longValue());
                robotKeyWordRel.setContent(s);
                robotKeyWordRel.setRelType(RelType.IMAGE.getType());
                robotKeyWordRel.setCreateTime(new Date());
                robotKeyWordRels.add(robotKeyWordRel);
            }
        }
        if (CollectionUtils.isNotEmpty(robotKeyWordRels)) {
            robotKeyWordRelService.saveBatch(robotKeyWordRels);
        }
    }

    /**
     * 修改关键字管理
     *
     * @param keyWorldParams 关键字管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRobotKeyWord(KeyWorldParams keyWorldParams) {
        Long count = robotKeyWordMapper.selectCount(Wrappers
                .<RobotKeyWord>lambdaQuery()
                .eq(RobotKeyWord::getKeyWord, keyWorldParams.getKeyWord())
                .ne(RobotKeyWord::getId, keyWorldParams.getId()));
        Assert.isTrue(count == 0, () -> new ServiceException("关键字不能重复"));
        RobotHandler robotHandler = robotHandlerService.getById(keyWorldParams.getHandleId());
        Assert.isTrue(ObjectUtil.isNotEmpty(robotHandler), () -> new ServiceException("找不到对应处理器"));
        keyWorldParams.setBeanName(robotHandler.getBeanName());
        keyWorldParams.setHandleType(robotHandler.getHandleType());
        keyWorldParams.setSendMsgType(robotHandler.getSendMsgType());
        keyWorldParams.setUpdateTime(DateUtils.getNowDate());

        int i = robotKeyWordMapper.updateRobotKeyWord(keyWorldParams);
        robotKeyWordRelService.remove(Wrappers.<RobotKeyWordRel>lambdaQuery()
                .eq(RobotKeyWordRel::getKeyWordId, keyWorldParams.getId()));
        insertRel(keyWorldParams);
        return i;
    }

    /**
     * 批量删除关键字管理
     *
     * @param ids 需要删除的关键字管理主键
     * @return 结果
     */
    @Override
    public int deleteRobotKeyWordByIds(Long[] ids) {
        return robotKeyWordMapper.deleteRobotKeyWordByIds(ids);
    }

    /**
     * 删除关键字管理信息
     *
     * @param id 关键字管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRobotKeyWordById(Long id) {
        int i = robotKeyWordMapper.deleteRobotKeyWordById(id);
        robotKeyWordRelService.remove(Wrappers.<RobotKeyWordRel>lambdaQuery()
                .eq(RobotKeyWordRel::getKeyWordId, id));
        return i;
    }
}
