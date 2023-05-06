package com.yh.robot.constant;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月20日 14:55:00
 */
public interface BeanNameConstant {

    String PREFIX="postType";



    interface PostTypeBean{
        String MESSAGE = PREFIX+RobotMessageConstant.PostType.MESSAGE;
        String NOTICE = PREFIX+RobotMessageConstant.PostType.NOTICE;
        String REQUEST = PREFIX+RobotMessageConstant.PostType.REQUEST;
    }

}