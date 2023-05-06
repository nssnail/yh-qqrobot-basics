package com.yh.robot.params;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 10:54:00
 */
@Data
public class GroupRelParams {

    @NotNull(message = "群号不能为空")
    private Long groupId;

    private List<Long> relIds;

    private Integer relType;
}