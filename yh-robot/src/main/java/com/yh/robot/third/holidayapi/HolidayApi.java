package com.yh.robot.third.holidayapi;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.yh.robot.enums.HolidayEnum;
import com.yh.robot.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月20日 11:40:00
 */
@Component
@Slf4j
public class HolidayApi {

    private static final String URL = "http://tool.bitefu.net/jiari/?d=%s";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public boolean isHoliday(Date date) {
        return isHoliday(DateUtil.format(date, DATE_FORMAT));
    }

    public boolean isHoliday(String date) {
        log.info("isHoliday_param={}", date);
        //示例：http://tool.bitefu.net/jiari/?d=2021-09-19
        //注意：第三方api，该链接有可能失效
        //返回值：0 上班 1周末 2节假日
        try {
            String result = HttpUtil.get(String.format(URL, date));
            log.info("isHoliday_result={}", JSONUtil.toJsonStr(result));
            return NumberUtil.isInteger(result)
                    && !HolidayEnum.getType(Integer.parseInt(result)).equals(HolidayEnum.DAY);
        } catch (Exception e) {
            log.error("isHoliday_error!", e);
        }

        return false;
    }

}