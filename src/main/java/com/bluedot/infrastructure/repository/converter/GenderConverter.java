package com.bluedot.infrastructure.repository.converter;

import com.bluedot.domain.rbac.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @Author Jason
 * @CreationDate 2023/04/16 - 20:31
 * @Description ：
 */
@Converter
public class GenderConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String attribute) {
        if (User.GENDER_BOY.equals(attribute)) {
            return User.BOY_DB;
        } else if (User.GENDER_GIRL.equals(attribute)) {
            return User.GIRL_DB;
        }
        throw new RuntimeException("非法性别数据：" + attribute);
    }

    @Override
    public String convertToEntityAttribute(Integer dbData) {
        if (User.BOY_DB.equals(dbData)) {
            return User.GENDER_BOY;
        } else if (User.GIRL_DB.equals(dbData)) {
            return User.GENDER_GIRL;
        }
        throw new RuntimeException("非法性别数据库数据：" + dbData);
    }
}
