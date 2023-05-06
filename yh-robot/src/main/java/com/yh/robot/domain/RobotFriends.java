package com.yh.robot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yh.common.annotation.Excel;
import lombok.Data;
import com.yh.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 好友列对象 robot_friends
 *
 * @author nssnail
 * @date 2023-03-29
 */
@Data
public class RobotFriends {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * qq
     */
    @Excel(name = "qq")
    private Long qq;
    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickname;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remake;
    /**
     * 机器人qq
     */
    @Excel(name = "机器人qq")
    private Long selfId;
    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
