package com.yh.robot.filter;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.message.receive.Message;
import com.yh.robot.request.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author nssnail
 * @Description 过滤心跳消息
 * @createTime 2023年03月16日 14:53:00
 */
@Slf4j
public class MessageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest  httpServletRequest = (HttpServletRequest) servletRequest;
        if(httpServletRequest.getRequestURI().equals("/getMessage")){
            RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            String bodyString = requestWrapper.getBodyString();
            Message message = JSONObject.parseObject(bodyString, Message.class);
            if(ObjectUtil.isNotEmpty(message)){
                if (StringUtils.isNoneBlank(message.getPostType()) && !message.getPostType().equals(RobotMessageConstant.PostType.META_EVENT)) {
                    filterChain.doFilter(requestWrapper, servletResponse);
                }
            }
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}