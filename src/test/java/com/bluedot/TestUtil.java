package com.bluedot;

import cn.hutool.core.io.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Jason
 * @creationDate 2023/07/22 - 22:57
 */
public class TestUtil {
    private static final Logger log = LoggerFactory.getLogger(TestUtil.class);

    public static void printResponse(Response response){
        MultivaluedMap<String, Object> headers = response.getHeaders();
        headers.forEach((k, v)->{
            log.info(k+":"+v);
        });

        log.info("-------------------------------------------");
        log.info(IoUtil.read((InputStream) response.getEntity(), StandardCharsets.UTF_8));
    }
}
