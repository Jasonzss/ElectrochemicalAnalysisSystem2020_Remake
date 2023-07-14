package com.bluedot.infrastructure.repository.converter;

import com.bluedot.domain.process.model.Point;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 21:53
 */
public class PointsConverter implements AttributeConverter<List<Point>, String> {
    private final Gson gson = new Gson();

    @Override
    public String convertToDatabaseColumn(List<Point> attribute) {
        return gson.toJson(attribute);
    }

    @Override
    public List<Point> convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData, List.class);
    }
}
