package com.yh.framework.web.exception;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.robot.client.RobotClient;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.constant.RobotMessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.yh.common.constant.HttpStatus;
import com.yh.common.core.domain.AjaxResult;
import com.yh.common.exception.DemoModeException;
import com.yh.common.exception.ServiceException;
import com.yh.common.utils.StringUtils;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author yh
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }


    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e) {
        return AjaxResult.error("演示模式，不允许操作");
    }


    @Autowired
    private RobotClient robotClient;

    @Order(1)
    @ExceptionHandler(BotException.class)
    public void handleBotException(BotException e, HttpServletRequest request) {
        JSONObject requestBody = getRequestBody(request);
        if (requestBody.get("message_type").equals(RobotMessageConstant.MessageType.GROUP.getName())) {
            Object groupId = requestBody.get("group_id");
            if (ObjectUtil.isNotEmpty(groupId)) {
                robotClient.sendGroupMessage(e.getMessage(), Long.parseLong(groupId.toString()));
            }
        } else if (requestBody.get("message_type").equals(RobotMessageConstant.MessageType.PRIVATE.getName())) {
            Object userId = requestBody.get("user_id");
            if (ObjectUtil.isNotEmpty(userId)) {
                robotClient.sendPrivateMessage(e.getMessage(), Long.parseLong(userId.toString()));
            }
        }
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }


    public JSONObject getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        JSONObject jsonObject = null;
        try {
            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            jsonObject = JSONObject.parseObject(requestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
