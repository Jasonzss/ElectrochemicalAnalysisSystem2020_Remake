package com.bluedot.infrastructure.date.type;

import cn.hutool.core.date.DateUtil;
import com.bluedot.infrastructure.date.DateFormatConverter;

import java.util.Date;

/**
 * @author Jason
 * @since 2023/07/31 - 22:08
 */
public class DateInChineseUnitConverter implements DateFormatConverter {
    @Override
    public String convert(Date date) {
        return DateUtil.year(date)+"年"+DateUtil.month(date)+"月"+DateUtil.dayOfMonth(date)+"日";
    }
}
