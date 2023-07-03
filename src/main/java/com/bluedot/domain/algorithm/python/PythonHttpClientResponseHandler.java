package com.bluedot.domain.algorithm.python;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jason
 * @creationDate 2023/06/30 - 23:30
 */
public class PythonHttpClientResponseHandler extends BasicHttpClientResponseHandler {
    private static final Logger log = LoggerFactory.getLogger(PythonHttpClientResponseHandler.class);

    @Override
    public String handleEntity(HttpEntity entity) throws IOException {
        String e = IoUtil.read(entity.getContent(), StandardCharsets.UTF_8);
        log.info(e);
        return e;
    }

    @Override
    public String handleResponse(ClassicHttpResponse response) throws IOException {
        if(response.getCode() != 200){
            throw new PythonInvokeException(CommonErrorCode.E_3020);
        }else {
            // TODO 针对不同的HTTP异常返回还需要做封装处理
            return super.handleResponse(response);
        }
    }
}

