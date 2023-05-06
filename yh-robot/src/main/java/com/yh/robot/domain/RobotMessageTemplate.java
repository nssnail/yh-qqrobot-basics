package com.yh.robot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yh.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yh.common.core.domain.BaseEntity;

/**
 * 消息模板对象 robot_message_template
 *
 * @author nssnail
 * @date 2023-04-06
 */
@Data
public class RobotMessageTemplate {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 模板名称
     */
    @Excel(name = "模板名称")
    private String name;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片
     */
    private String images;
    /**
     * 模板类型
     */
    @Excel(name = "模板类型")
    private Integer tempType;
    /**
     * 配置
     */
    private String config;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
