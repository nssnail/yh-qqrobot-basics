package com.yh.robot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yh.robot.domain.RobotWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 16:07:00
 */
@Mapper
public interface RobotWordMapper extends BaseMapper<RobotWord> {

    List<RobotWord> selectRandom(@Param("nums") Integer nums);
}