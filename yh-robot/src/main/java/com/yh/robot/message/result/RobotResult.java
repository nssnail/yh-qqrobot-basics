package com.yh.robot.message.result;

import com.yh.robot.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月27日 14:50:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RobotResult {

    private int code;

    private String message;

    private Object data;

    private Boolean approve;

    private String reason;

    public static RobotResult success(){
        return new RobotResult(ResultCode.SUCCESS.getType(),"ok",null,null,null);
    }

    public static RobotResult success(Object data){
        return new RobotResult(ResultCode.SUCCESS.getType(),"ok",data,null,null);
    }

    public static RobotResult error(String message){
        return new RobotResult(ResultCode.ERROR.getType(),message,null,null,null);
    }

    public static RobotResult pass(){
        return new RobotResult(ResultCode.SUCCESS.getType(),"ok",null,true,null);
    }

    public static RobotResult refuse(){
        return new RobotResult(ResultCode.SUCCESS.getType(),"ok",null,false,"拒绝加入");
    }

    public static RobotResult refuse(String msg){
        return new RobotResult(ResultCode.SUCCESS.getType(),"ok",null,false,msg);
    }
}