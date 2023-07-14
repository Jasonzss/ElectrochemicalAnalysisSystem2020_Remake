package com.bluedot.infrastructure.repository.converter;

import com.bluedot.infrastructure.utils.Quantity;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 14:12
 */
public class QuantityConverter implements AttributeConverter<Quantity, String> {
    private final Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(Quantity attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public Quantity convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, Quantity.class);
    }
}
