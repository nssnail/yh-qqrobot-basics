package com.yh.robot.third.youdao.result;

import lombok.Data;

import java.util.List;

/**
 * @ClassName YouDaoResult
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 10:44
 * @Version 1.0
 */
@Data
public class YouDaoResult {

    private List<String> returnPhrase;

    private String query;

    private String errorCode;

    private String l;

    private String tSpeakUrl;

    private List<Web> web;

    private String requestId;

    private List<String> translation;

    private Dict mTerminalDict;

    private Dict dict;

    private Dict webdict;

    private Basic basic;

    private boolean isWord;

    private String speakUrl;
}
