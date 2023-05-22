package com.yh.robot.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyh
 * @Description TODO
 * @createTime 2023年05月22日 11:23:00
 */
public class LengthUtil {

    private static final Integer SPLIT_LENGTH = 3000;

    private static final Integer MAX_LENGTH = 5000;

    public static boolean isOutOfTextLength(String msg) {
        return msg.getBytes().length > MAX_LENGTH;
    }

    public static List<String> split(String text) {
        List<String> result = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + SPLIT_LENGTH, text.length());
            result.add(text.substring(start, end));
            start = end;
        }
        return result;
    }

}