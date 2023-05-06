package com.yh.robot.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yh.common.annotation.Excel;
import lombok.Data;
import java.util.Date;
import com.yh.common.core.domain.BaseEntity;

/**
 * 群列对象 robot_group
 * 
 * @author nssnail
 * @date 2023-03-30
 */
@Data
public class RobotGroup {
    private static final long serialVersionUID = 1L;

        /** id */
        @TableId(type = IdType.AUTO)
        private Long id;
        /** 群号 */
        @Excel(name = "群号")
        private Long groupId;
        /** 群名称 */
        @Excel(name = "群名称")
        private String groupName;
        /** 群备注 */
        @Excel(name = "群备注")
        private String groupMemo;
        /** 群创建时间 */
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Excel(name = "群创建时间", width = 30, dateFormat = "yyyy-MM-dd")
        private Date groupCreateTime;
        /** 机器人qq */
        @Excel(name = "机器人qq")
        private Long selfId;
        /** 状态 */
        @Excel(name = "状态")
        private Integer status;
        /** 创建时间 */
        private Date createTime;
        /** 修改时间 */
        private Date updateTime;
}
