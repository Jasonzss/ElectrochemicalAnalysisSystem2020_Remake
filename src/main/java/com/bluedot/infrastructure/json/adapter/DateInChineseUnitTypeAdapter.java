package com.bluedot.infrastructure.json.adapter;

import cn.hutool.core.date.DateUtil;
import com.bluedot.infrastructure.date.type.DateInChineseUnitConverter;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Jason
 * @since 2023/07/31 - 21:53
 */
public class DateInChineseUnitTypeAdapter extends TypeAdapter<Date> {
    private final DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private final DateInChineseUnitConverter converter = new DateInChineseUnitConverter();
    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if(value != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(value);
            String s = DateUtil.formatChineseDate(calendar, false);
            out.value(s);
        }else {
            //没有值的时候不输出为json
            out.nullValue();
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        return dateTypeAdapter.read(in);
    }
}
