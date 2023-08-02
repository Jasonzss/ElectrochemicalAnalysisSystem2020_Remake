package com.bluedot.infrastructure.json.adapter;

import com.bluedot.infrastructure.date.type.FestivalDateFormatConverter;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * @author Jason
 * @since 2023/07/31 - 21:52
 */
public class FestivalTypeAdapter  extends TypeAdapter<Date> {
    private final DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private final FestivalDateFormatConverter converter = new FestivalDateFormatConverter();

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if(value != null){
            String s = converter.convert(value);
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
