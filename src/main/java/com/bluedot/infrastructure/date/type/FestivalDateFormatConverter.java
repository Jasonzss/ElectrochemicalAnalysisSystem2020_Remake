package com.bluedot.infrastructure.date.type;

import cn.hutool.core.date.DateUtil;
import com.bluedot.infrastructure.date.DateFormatConverter;

import java.util.Date;

/**
 * @author Jason
 * @since 2023/07/31 - 21:15
 *
 * 这里所指的节日类可以替换成JDK的Calendar类，两者差不多意思
 */
public class FestivalDateFormatConverter implements DateFormatConverter {
    @Override
    public String convert(Date date) {
        date = getClosestFestival(date);

        if (DateUtil.isSameDay(date, new Date())){
            return "今天";
        }
        if (DateUtil.isSameDay(date, DateUtil.tomorrow())){
            return "明天";
        }
        if (DateUtil.isSameDay(date, DateUtil.offsetDay(new Date(), 2))){
            return "后天";
        }
        if (DateUtil.isSameDay(date, DateUtil.yesterday())){
            return "昨天";
        }
        if (DateUtil.isSameDay(date, DateUtil.offsetDay(new Date(), -2))){
            return "前天";
        }

        for(int i = 3; i <= 7; i++){
            if (DateUtil.isSameDay(date, DateUtil.offsetDay(new Date(), i))){
                return i+"天后";
            }

            if (DateUtil.isSameDay(date, DateUtil.offsetDay(new Date(), -i))){
                return i+"天前";
            }
        }

        return DateUtil.month(date)+"月"+DateUtil.dayOfMonth(date)+"日";
    }

    private Date getClosestFestival(Date date){
        Date now = new Date();

        int year = DateUtil.year(now);

        Date festivalLastYear = new Date(year, DateUtil.month(date), DateUtil.dayOfMonth(date));
        Date festivalThisYear = new Date(year, DateUtil.month(date), DateUtil.dayOfMonth(date));
        Date festivalNextYear = new Date(year, DateUtil.month(date), DateUtil.dayOfMonth(date));

        long lastBt = Math.abs(festivalLastYear.getTime()-now.getTime());
        long thisBt = Math.abs(festivalThisYear.getTime()-now.getTime());
        long nextBt = Math.abs(festivalNextYear.getTime()-now.getTime());

        if (lastBt < thisBt && lastBt < nextBt){
            return festivalLastYear;
        }else if (thisBt < lastBt && thisBt < nextBt){
            return festivalThisYear;
        }else {
            return festivalNextYear;
        }
    }
}
