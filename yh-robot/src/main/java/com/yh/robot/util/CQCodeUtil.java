package com.yh.robot.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yh.robot.constant.CQCode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 16:27:00
 */
public class CQCodeUtil {


    public static final String PATTERN = "\\[(.*?)]";

    //[CQ:类型,参数=值,参数=值]
    public static String getAtMeMessage(String message,Long qq) {
        String line = StrUtil.trim(message);
        String code = buildAtCQCode(qq);
        line = line.replace(code, "");
        return StrUtil.trim(line);
    }


    public static boolean checkAt(String cqcodeStr,Long qq) {
        String code = buildAtCQCode(qq);
        List<String> cqList = findCQList(cqcodeStr);
        for (String cqcode : cqList) {
            if (cqcode.equals(code)) {
                return true;
            }
        }
        return false;
    }

    private static List<String> findCQList(String line) {
        if (line == null) {
            return new ArrayList<>();
        }
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(line);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }

    public static String buildCQCode(String code, LinkedHashMap<String, String> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("[CQ:").append(code);
        if (CollectionUtils.isNotEmpty(data)) {
            data.forEach((k, v) -> {
                sb.append(",").append(k).append("=").append(v);
            });
        }
        sb.append("]");
        return sb.toString();
    }

    public static String buildAtCQCode(Long qq) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(CQCode.CQProperty.QQ, qq.toString());
        return buildCQCode(CQCode.CQtType.AT, map);
    }

    public static String buildImageCQCode(String file,String cover) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(CQCode.CQProperty.FILE, file);
        map.put(CQCode.CQProperty.COVER, cover);
        return buildCQCode(CQCode.CQtType.IMAGE, map);
    }

    public static String buildVideoCQCode(String file,String cover) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(CQCode.CQProperty.FILE, file);
        map.put(CQCode.CQProperty.COVER, cover);
        return buildCQCode(CQCode.CQtType.VIDEO, map);
    }

    /**
     * 不起作用
     * @param file
     * @return
     */
    @Deprecated
    public static String buildRecordCQCode(String file) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(CQCode.CQProperty.FILE, file);
        return buildCQCode(CQCode.CQtType.RECORD, map);
    }

    /**
     * 不起作用
     * @param audio
     * @return
     */
    @Deprecated
    public static String buildMusicCQCode(String audio) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(CQCode.CQProperty.TYPE, "custom");
        map.put(CQCode.CQProperty.URL, "https://www.baidu.com");
        map.put(CQCode.CQProperty.AUDIO, audio);
        map.put(CQCode.CQProperty.TITLE, "晴天");
        return buildCQCode(CQCode.CQtType.MUSIC, map);
    }

    /**
     * 不起作用
     * @param data
     * @return
     */
    @Deprecated
    public static String buildJSONCQCode(String data) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(CQCode.CQProperty.DATA, data);
        return buildCQCode(CQCode.CQtType.JSON, map);
    }
}