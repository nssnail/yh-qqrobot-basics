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
 * 处理器对象 robot_handler
 *
 * @author nssnail
 * @date 2023-04-10
 */
@Data
public class RobotHandler {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 处理器beanName */
    @Excel(name = "处理器beanName")
    private String beanName;
    /** 处理器名称 */
    @Excel(name = "处理器名称")
    private String handleName;
    /** 处理器类型 */
    @Excel(name = "处理器类型")
    private Integer handleType;
    /** 返回值类型 */
    @Excel(name = "返回值类型")
    private Integer sendMsgType;
    /** 状态 */
    @Excel(name = "状态")
    private Integer status;
    /** 是否是外部api */
    @Excel(name = "是否是外部api")
    private Integer isApi;
    /** 排序 */
    @Excel(name = "排序")
    private Integer sortNum;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
}
