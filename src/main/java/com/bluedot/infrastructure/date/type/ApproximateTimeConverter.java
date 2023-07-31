package com.bluedot.infrastructure.date.type;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.bluedot.infrastructure.date.DateFormatConverter;
import java.util.Date;

/**
 * 大概的日期格式，转换规则如下：<p>
 *
 * <ul>
 *     不在同一年
 *     <li>【yyyy年MM月dd日】：某年某月某日</li>
 * </ul>
 *
 * <ul>
 *     过去的时间
 *     <li>【刚刚】：过去的60秒内</li>
 *     <li>【n分钟前】：过去的 1分钟 ~ 60分钟</li>
 *     <li>【n小时前】：过去的 1小时 ~ 24小时</li>
 *     <li>【昨天 HH:mm】：日期上的今天的前一天 + hour + minute</li>
 *     <li>【前天 HH:mm】：日期上的今天的前两天 + hour + minute</li>
 *     <li>【n天前】：日期上的今天的前3 ~ 7天</li>
 *     <li>【M月dd日】：今年的某月某日</li>
 * </ul>
 *
 * <ul>
 *     将来的时间
 *     <li>【马上】：将来的60秒内</li>
 *     <li>【n分钟后】：将来的 1分钟 ~ 60分钟</li>
 *     <li>【n小时后】：将来的 1小时 ~ 24小时</li>
 *     <li>【明天 HH:mm】：日期上的今天的后一天 + hour + minute</li>
 *     <li>【后天 HH:mm】：日期上的今天的后两天 + hour + minute</li>
 *     <li>【n天后】：日期上的今天的后3 ~ 7天</li>
 *     <li>【M月dd日】：今年的某月某日</li>
 * </ul>
 *
 * @author Jason
 * @since 2023/07/31 - 16:46
 */
public class ApproximateTimeConverter implements DateFormatConverter {
    private static final Long ONE_MINUTE_TIMESTAMP = 60_000L;

    private static final Long ONE_HOUR_TIMESTAMP = 3_600_000L;

    private static final Long ONE_DAY_TIMESTAMP = 86_400_000L;


    @Override
    public String convert(Date date) {
        Date now = new Date();
        return convert(date, now);
    }

    public String convert(Date date, Date now) {
        boolean isPast = DateUtil.compare(now, date) > 0;

        Date max = isPast ? now : date;
        Date min = isPast ? date : now;

        //1. 判断是否同一年
        if(DateUtil.year(max) - DateUtil.year(min) != 0){
            //不在同一年
            return DateUtil.format(date, "yyyy年MM月dd日");
        }


        //2. 同一年内的开始从小到大开始算
        long timeStampBt = max.getTime() - min.getTime();

        if (timeStampBt <= ONE_MINUTE_TIMESTAMP) {
            return isPast ? "刚刚" : "马上";
        } else if (timeStampBt < ONE_HOUR_TIMESTAMP) {
            return Math.abs(DateUtil.minute(max) - DateUtil.minute(min)) + "分钟" + (isPast ? "前" : "后");
        } else if (timeStampBt < ONE_DAY_TIMESTAMP) {
            return DateUtil.hour(max, true) - DateUtil.hour(min, true) + "小时" + (isPast ? "前" : "后");
        }

        String s = DateUtil.formatBetween(min, max, BetweenFormatter.Level.DAY);
        int dayBt = Integer.parseInt(s.substring(0, s.length()-1));

        if (dayBt == 1) {
            return isPast ? "昨天" : "明天";
        } else if (dayBt == 2) {
            return isPast ? "前天" : "后天";
        } else if(2 < dayBt && dayBt <=7) {
            return dayBt+"天"+ (isPast ? "前" : "后");
        } else {
            return DateUtil.month(date)+"月"+DateUtil.dayOfMonth(date)+"日";
        }
    }
}
