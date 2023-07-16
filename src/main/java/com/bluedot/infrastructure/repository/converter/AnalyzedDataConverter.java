package com.bluedot.infrastructure.repository.converter;

import com.bluedot.domain.analysis.model.AnalyzedData;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.exception.RepositoryException;
import com.google.gson.Gson;
import org.xerial.snappy.Snappy;

import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 22:35
 */
public class AnalyzedDataConverter implements AttributeConverter<AnalyzedData, byte[]> {
    private final Gson gson = new Gson();
    /**
     * 将点位实体队列转换为json格式再进行压缩，压缩前七千多长度，压缩后一千二
     */
    @Override
    public byte[] convertToDatabaseColumn(AnalyzedData attribute) {
        try {
            return Snappy.compress(gson.toJson(attribute));
        } catch (IOException e) {
            throw new RepositoryException(CommonErrorCode.E_5001);
        }
    }

    @Override
    public AnalyzedData convertToEntityAttribute(byte[] dbData) {
        try {
            return gson.fromJson(Snappy.uncompressString(dbData), AnalyzedData.class);
        } catch (IOException e) {
            throw new RepositoryException(CommonErrorCode.E_5001);
        }
    }
}
