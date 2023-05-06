package com.yh.robot.handler.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName MessageVo
 * @Description TODO
 * @Author Y
 * @Date 2023/3/24 22:24
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MessageVo {

    private String keyWord;

    private String msg;

    private Long groupId;

    private Long userId;

    public MessageVo(String msg, Long groupId, Long userId) {
        this.msg = msg;
        this.groupId = groupId;
        this.userId = userId;
    }

    public MessageVo(String keyWord, String msg) {
        this.keyWord = keyWord;
        this.msg = msg;
    }
}
