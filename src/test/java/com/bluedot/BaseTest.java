package com.bluedot;
import cn.hutool.core.io.IoUtil;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 18:35
 * @Description ：基础测试类，保存可以复用很多测试代码
 */
public class BaseTest {
    public static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    public static void printHttpResponse(CloseableHttpResponse response) throws ParseException {
        System.out.println(response.toString());
        System.out.println("-----------------------------");
        response.headerIterator().forEachRemaining(System.out::println);
        System.out.println("-----------------------------");
        System.out.println(response.getEntity());
        System.out.println("-----------------------------");
        try {
            System.out.println(response.getEntity().getContentType());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            log.info("http no entity content");
        }
    }

    public static void printHttpRequest(HttpUriRequestBase request) throws IOException {
        System.out.println(request);
        System.out.println("-----------------------------");
        request.headerIterator().forEachRemaining(System.out::println);
        System.out.println("-----------------------------");
        System.out.println(request.getEntity().getContentType());
//        System.out.println(IoUtil.readUtf8(request.getEntity().getContent()));
    }

    public static void printOptional(Optional<?> op){
        op.ifPresent(o -> System.out.println(o.toString()));
    }
}
