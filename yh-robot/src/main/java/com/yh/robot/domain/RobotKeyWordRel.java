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
 * 关键词关联对象 robot_key_word_rel
 * 
 * @author nssnail
 * @date 2023-03-31
 */
@Data
public class RobotKeyWordRel {
    private static final long serialVersionUID = 1L;

        /** id */
        @TableId(type = IdType.AUTO)
        private Long id;
        /** 关键词id */
        @Excel(name = "关键词id")
        private Long keyWordId;
        /** 内容 */
        @Excel(name = "内容")
        private String content;
        /** 关联类型 */
        @Excel(name = "关联类型")
        private Integer relType;
        /** 创建时间 */
        private Date createTime;
}
