package com.bluedot.infrastructure.http;

import org.apache.hc.client5.http.impl.classic.AbstractHttpClientResponseHandler;
import org.apache.hc.core5.http.HttpEntity;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 21:15
 */
public class InputStreamResponseHandler extends AbstractHttpClientResponseHandler<InputStream> {
    @Override
    public InputStream handleEntity(HttpEntity entity) throws IOException {
        return entity.isStreaming() ? entity.getContent() : null;
    }
}
