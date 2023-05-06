package com.yh.robot.params.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月14日 17:35:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeParam {

    private Date startTime;

    private Date endTime;
}