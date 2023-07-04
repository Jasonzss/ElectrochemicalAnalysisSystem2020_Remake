package com.bluedot.hello;

import cn.hutool.core.io.IoUtil;
import com.bluedot.BaseTest;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jason
 * @creationDate 2023/06/02 - 16:05
 */
public class HelloHttpClient extends BaseTest {
    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void testGet() throws IOException {
        HttpGet httpGet = new HttpGet("http://127.0.0.1:5000/hello");
        CloseableHttpResponse response = httpclient.execute(httpGet);
    }

    @Test
    public void testReadResponse() throws IOException {
        HttpGet httpGet = new HttpGet("http://127.0.0.1:5000/hello");
        CloseableHttpResponse response = httpclient.execute(httpGet);

        System.out.println("--------------------------------------------");

        //HTTP/1.1 200 OK
        System.out.println(response.toString());

        //true
        System.out.println(response.containsHeader("Content-Length"));

        //Server: Werkzeug/2.3.4 Python/3.11.1
        //Date: Fri, 02 Jun 2023 08:30:28 GMT
        //Content-Type: text/html; charset=utf-8
        //Content-Length: 20
        //Connection: close
        response.headerIterator().forEachRemaining(System.out::println);

        //Wrapper [[Content-Type: text/html; charset=utf-8,Content-Encoding: null,Content-Length: 20,Chunked: false]]
        System.out.println(response.getEntity());

        //Content-Type: text/html; charset=utf-8
        System.out.println(response.getFirstHeader("Content-Type"));

        //Content-Type: text/html; charset=utf-8
        System.out.println(response.getLastHeader("Content-Type"));

        //zh_CN
        System.out.println(response.getLocale());

        //HTTP/1.1
        System.out.println(response.getVersion());

        //OK
        System.out.println(response.getReasonPhrase());

        //<p>Hello, World!</p>
        System.out.println(IoUtil.read(response.getEntity().getContent()));
    }

    @Test
    public void testPostEntity() throws IOException {
        Gson gson = new Gson();

        //初始化Post请求
        HttpPost post = new HttpPost("http://127.0.0.1:5000/test1");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(10000, TimeUnit.MILLISECONDS))
                .build();
        post.setConfig(requestConfig);
        //表示客户端发送给服务器端的数据格式
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        //httpPost.setHeader("Accept", "*/*");这样也ok,只不过服务端返回的数据不一定为json
        post.setHeader("Accept", "application/json");

        Map<String, Object> m = new HashMap<>();
        Map<String, Object> a = new HashMap<>();
        a.put("jason","241");
        a.put("add","222");
        m.put("params", Lists.newArrayList("Jason", "241897", "你好", 20010425, 4.25, true, false,
                new int[]{1,2},
                new String[]{"ad","bcc"},
                a
        ));
        StringEntity entity = new StringEntity(gson.toJson(m), ContentType.APPLICATION_JSON);

        post.setEntity(entity);

        httpclient.execute(post, new BasicHttpClientResponseHandler());
    }

    @Test
    public void testPostInputStream() throws IOException, ParseException {
        InputStream is = new FileInputStream("src\\main\\resources\\algo\\python\\python_test_hello\\python_algorithm.py");

        //初始化Post请求
        HttpPost post = new HttpPost("http://127.0.0.1:5000/test2");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(10000, TimeUnit.MILLISECONDS))
                .build();
        post.setConfig(requestConfig);
        //表示客户端发送给服务器端的数据格式
        //必须显式定义Boundary，不然Python端解析不了数据（真是见了鬼了
        post.setHeader("Content-Type", "multipart/form-data; charset=ISO-8859-1; boundary=mVeFwaGv8_V9nTHRREq6Aw0oksC66x");
        //httpPost.setHeader("Accept", "*/*");这样也ok,只不过服务端返回的数据不一定为json
        post.setHeader("Accept", "application/json");

        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("is", is, ContentType.APPLICATION_OCTET_STREAM, "is")
                .addTextBody("jason", "fuck", ContentType.TEXT_PLAIN)
                .setBoundary("mVeFwaGv8_V9nTHRREq6Aw0oksC66x")
                .build();

        post.setEntity(entity);

        httpclient.execute(post, new BasicHttpClientResponseHandler());
//        printHttpResponse(httpclient.execute(post));
    }

    @Test
    public void testPostForm() throws IOException {
        Gson gson = new Gson();
        //初始化Post请求
        HttpPost post = new HttpPost("http://127.0.0.1:5000/test3");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(10000, TimeUnit.MILLISECONDS))
                .build();
        post.setConfig(requestConfig);
        //表示客户端发送给服务器端的数据格式
        post.setHeader("Content-Type", "multipart/form-data; charset=UTF-8; boundary=mVeFwaGv8_V9nTHRREq6Aw0oksC66x");
        //httpPost.setHeader("Accept", "*/*");这样也ok,只不过服务端返回的数据不一定为json
        post.setHeader("Accept", "application/json");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setBoundary("mVeFwaGv8_V9nTHRREq6Aw0oksC66x");

        Map<String, Object> m = new HashMap<>();
        Map<String, Object> a = new HashMap<>();
        a.put("jason","241");
        a.put("add","222");
        m.put("params", Lists.newArrayList("Jason", "241897", "你好", 20010425, 4.25, true, false,
                new int[]{1,2},
                new String[]{"ad","bcc"},
                a
        ));

        m.forEach((k, v) -> {
            builder.addTextBody(k, gson.toJson(v), ContentType.APPLICATION_JSON);
        });

//        builder.addTextBody("params", "jason", ContentType.TEXT_PLAIN);

        post.setEntity(builder.build());

        System.out.println(IoUtil.read(httpclient.execute(post).getEntity().getContent(), StandardCharsets.UTF_8));
    }
}
