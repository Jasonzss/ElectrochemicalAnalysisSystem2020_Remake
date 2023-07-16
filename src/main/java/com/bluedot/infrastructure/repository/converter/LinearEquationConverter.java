package com.bluedot.infrastructure.repository.converter;

import com.bluedot.infrastructure.utils.LinearEquation;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 22:27
 */
public class LinearEquationConverter implements AttributeConverter<LinearEquation, String> {
    private final Gson gson = new Gson();


    @Override
    public String convertToDatabaseColumn(LinearEquation attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public LinearEquation convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, LinearEquation.class);
    }
}
