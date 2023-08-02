package com.bluedot.infrastructure.jax_rs.message_body_writer;

import cn.hutool.core.io.IoUtil;
import com.bluedot.infrastructure.json.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Jason
 * @since 2023/07/26 - 23:17
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Component
public class EntityWriter implements MessageBodyWriter<ResponseEntity> {

    @Inject
    private Gson gson;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return ResponseEntity.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(ResponseEntity responseEntity, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(ResponseEntity responseEntity,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        OutputStreamWriter writer = IoUtil.getUtf8Writer(entityStream);
        String s = gson.toJson(responseEntity);
        writer.write(s, 0, s.length());
        writer.flush();
    }
}
