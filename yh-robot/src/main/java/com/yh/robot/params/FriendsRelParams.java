package com.yh.robot.params;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月04日 14:49:00
 */
@Data
public class FriendsRelParams {
    @NotNull(message = "qq号不能为空")
    private Long qq;

    private List<Long> relIds;

    private Integer relType;
}