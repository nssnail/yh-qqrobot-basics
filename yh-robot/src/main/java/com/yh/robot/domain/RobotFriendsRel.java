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
 * 好友关联对象 robot_friends_rel
 * 
 * @author nssnail
 * @date 2023-04-04
 */
@Data
public class RobotFriendsRel {
    private static final long serialVersionUID = 1L;

        /** 主键 */
        @TableId(type = IdType.AUTO)
        private Long id;
        /** qq号 */
        @Excel(name = "qq号")
        private Long qq;
        /** 关联id */
        @Excel(name = "关联id")
        private Long relId;
        /** 关联类型 */
        @Excel(name = "关联类型")
        private Integer relType;
        /** 创建时间 */
        private Date createTime;
}
