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
 * 英语长句难句对象 robot_sentence
 *
 * @author nssnail
 * @date 2023-03-29
 */
@Data
public class RobotSentence  {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 长句难句
     */
    @Excel(name = "长句难句")
    private String sentence;

    /**
     * 翻译
     */
    @Excel(name = "翻译")
    private String translation;

    /**
     * 年份
     */
    @Excel(name = "年份")
    private String year;

    /**
     * 解释
     */
    @Excel(name = "解释")
    private String sentenceExplain;

    private Date createTime;

}
