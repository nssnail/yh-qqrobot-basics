package com.yh.common.exception.robot;


public class BotException extends RuntimeException {

    private Long groupId;
    private Long userId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BotException() {
    }

    public BotException(String message) {
        super(message);
    }

    public BotException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotException(Throwable cause) {
        super(cause);
    }

    public BotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static BotException error(String message) {
        return new BotException(message);
    }

    public static BotException groupError(String message, Long groupId) {
        BotException botException = new BotException(message);
        botException.setGroupId(groupId);
        return botException;
    }

    public static BotException privateError(String message, Long userId) {
        BotException botException = new BotException(message);
        botException.setUserId(userId);
        return botException;
    }

    public static BotException error(String message, Long userId,Long groupId) {
        BotException botException = new BotException(message);
        botException.setUserId(userId);
        botException.setGroupId(groupId);
        return botException;
    }
}