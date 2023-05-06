package com.yh.robot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yh.common.annotation.Excel;
import lombok.Data;
import java.util.Date;

/**
 * 通知公告对象 robot_notice
 * 
 * @author nssnail
 * @date 2023-03-30
 */
@Data
public class RobotNotice {
    private static final long serialVersionUID = 1L;

        /** id */
        @TableId(type = IdType.AUTO)
        private Long id;
        /** 公告 */
        @Excel(name = "公告")
        private String notice;
        /** 创建时间 */
        private Date createTime;
        /** 修改时间 */
        private Date updateTime;
}
