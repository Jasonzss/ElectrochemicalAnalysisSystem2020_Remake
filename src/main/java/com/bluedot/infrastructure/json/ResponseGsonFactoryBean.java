package com.bluedot.infrastructure.json;

import com.bluedot.domain.process.model.Point;
import com.bluedot.infrastructure.json.adapter.ApproximateTimeTypeAdapter;
import com.bluedot.infrastructure.json.adapter.DateInChineseUnitTypeAdapter;
import com.bluedot.infrastructure.json.adapter.FestivalTypeAdapter;
import com.bluedot.infrastructure.repository.enumeration.UserStatus;
import com.bluedot.infrastructure.utils.Quantity;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author Jason
 * @since 2023/07/26 - 21:25
 *
 * 对响应返回的对象进行json化的FactoryBean
 */
@Component
public class ResponseGsonFactoryBean implements FactoryBean<Gson> {
    /**
     * 添加有Quantity、Point、UsesStatus重写逻辑等其他逻辑的gson
     */
    @Override
    public Gson getObject() {
        return new GsonBuilder()
               .registerTypeAdapter(Quantity.class, new QuantityTypeAdapter())
               .registerTypeAdapter(Point.class, new PointTypeAdapter())
               .registerTypeAdapter(UserStatus.class, new UserStatusTypeAdapter())
               .registerTypeAdapter(Date.class, new ApproximateTimeTypeAdapter())
               .registerTypeAdapter(Date.class, new DateInChineseUnitTypeAdapter())
               .registerTypeAdapter(Date.class, new FestivalTypeAdapter())
               .setExclusionStrategies(new ResponseIgnoreExclusionStrategies())
               .create();
    }

    @Override
    public Class<?> getObjectType() {
        return Gson.class;
    }



    private static class QuantityTypeAdapter extends TypeAdapter<Quantity> {
        /**
         * 两Quantity类输出为Json格式
         * @param out Json输出
         * @param value 需要输出的Quantity
         * @throws IOException 输出IO异常
         */
        @Override
        public void write(JsonWriter out, Quantity value) throws IOException {
            if(value != null){
                out.value(value.toString());
            }else {
                //没有值的时候不输出为json
                out.nullValue();
            }
        }

        @Override
        public Quantity read(JsonReader in) throws IOException {
            String s = in.nextString();
            if(StringUtils.isEmpty(s)){
                return new Quantity(null , null);
            }else {
                return new Quantity(s);
            }
        }
    }


    private static class PointTypeAdapter extends TypeAdapter<Point> {
        @Override
        public void write(JsonWriter out, Point value) throws IOException {
            if(value != null){
                String s = value.getX().toString()+","+value.getY().toString();
                out.value(s);
            }else {
                //没有值的时候不输出为json
                out.nullValue();
            }
        }

        @Override
        public Point read(JsonReader in) throws IOException {
            String s = in.nextString();
            if(StringUtils.isEmpty(s)){
                return new Point(new Quantity(null, null), new Quantity(null, null));
            }else {
                String[] split = s.split(",");
                return new Point(new Quantity(split[0]), new Quantity(split[1]));
            }
        }
    }

    private static class UserStatusTypeAdapter extends TypeAdapter<UserStatus>{
        @Override
        public void write(JsonWriter out, UserStatus value) throws IOException {
            if(value != null){
                String s = value.getStatus();
                out.value(s);
            }else {
                //没有值的时候不输出为json
                out.nullValue();
            }
        }

        @Override
        public UserStatus read(JsonReader in) throws IOException {
            String s = in.nextString();
            if(StringUtils.isEmpty(s)){
                return null;
            }else {
                return UserStatus.valueOf(s);
            }
        }
    }

    /**
     * 带有 {@link ResponseIgnore} 注解的字段将会被忽略
     */
    private static class ResponseIgnoreExclusionStrategies implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(ResponseIgnore.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
