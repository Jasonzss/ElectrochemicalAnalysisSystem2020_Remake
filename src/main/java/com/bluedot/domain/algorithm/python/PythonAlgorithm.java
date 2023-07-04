package com.bluedot.domain.algorithm.python;

import com.bluedot.domain.algorithm.AbstractAlgorithm;
import com.bluedot.domain.algorithm.exception.AlgoInvokeException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:12
 * @Description ：
 */
public class PythonAlgorithm extends AbstractAlgorithm {
    public static final String SUPPORT_LANGUAGE_PYTHON = "python";
    private final static String PYTHON_WEB_URL = "http://127.0.0.1:5000";

    private final CloseableHttpClient httpclient = HttpClients.createDefault();

    private InputStream inputStream;


    /**
     * 支持传入参数的类型
     */
    private static final Set<Class<?>> supportedClasses = new HashSet<>();

    static {
        supportedClasses.add(Integer.class);
        supportedClasses.add(Byte.class);
        supportedClasses.add(Short.class);
        supportedClasses.add(Long.class);
        supportedClasses.add(Double.class);
        supportedClasses.add(Float.class);
        supportedClasses.add(Boolean.class);
        supportedClasses.add(Character.class);
        supportedClasses.add(String.class);
    }

    @Override
    protected String getLanguage() {
        return SUPPORT_LANGUAGE_PYTHON;
    }

    @Override
    public Object execute(Object... input) throws AlgoInvokeException {
        Gson gson = new Gson();

        if (!support(input)) {
            throw new AlgoInvokeException(CommonErrorCode.E_3011);
        }

        Map<String, Object> form = new HashMap<>();
        form.put("params", gson.toJson(input));

        try {
            return sendPost("/invoke", form, inputStream);
        } catch (IOException e) {
            //TODO 两端之间的异常处理需要细化
            throw new AlgoInvokeException(e, CommonErrorCode.E_3012);
        }
    }

    /**
     * 不允许传入除了指定类型以外的参数，对输入的参数进行校验。因为有些对象在经过Http后可能会出现问题
     * @param input 算法的输入
     * @return 输入参数是否符合该算法的入参规定
     */
    private boolean support(Object... input) {
        for(Object o : input){
            if(o.getClass().isPrimitive()){
                // 跳过基类
                continue;
            }
            if(!isAllowed(o.getClass())){
                return false;
            }
        }

        //远程询问是否可以使用此参数
//        Map<String, Object> form = new HashMap<>();
//        form.put("algo_id", getAlgoId());
//        form.put("base_path", basePath);
//        form.put("params", input);
//
//        try {
//            return (boolean) sendPost("/support", form);
//        } catch (IOException e) {
//            throw new AlgoInvokeException("Http请求发送失败");
//        }

        return true;
    }

    /**
     * 判断调用算法的入参是否合法
     * @param clazz 入参的类型
     * @return 是否为合法入参
     */
    private boolean isAllowed(Class<?> clazz){
        for(Class<?> c : supportedClasses){
            if(c.isAssignableFrom(clazz)){
                return true;
            }
        }

        return false;
    }

    /**
     * 向pythonWeb端发送算法调用请求
     * @param uri pythonWeb的uri
     * @param args 调用参数
     * @return 方法调用结果
     * @throws IOException 调用失败
     */
    private Object sendPost(String uri, Map<String, Object> args, InputStream inputStream) throws IOException {
        //初始化Post请求
        HttpPost post = new HttpPost(PYTHON_WEB_URL+uri);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(10000, TimeUnit.MILLISECONDS))
                .build();
        post.setConfig(requestConfig);
        //表示客户端发送给服务器端的数据格式
        post.setHeader("Content-Type", "multipart/form-data; charset=UTF-8; boundary=mVeFwaGv8_V9nTHRREq6Aw0oksC66x");
        post.setHeader("Accept", "application/json;charset=UTF-8");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        args.forEach((k,v) -> {
            builder.addTextBody(k, v.toString(), ContentType.APPLICATION_JSON);
        });

        HttpEntity entity = builder.addBinaryBody("is", inputStream, ContentType.APPLICATION_OCTET_STREAM, "is")
                .setBoundary("mVeFwaGv8_V9nTHRREq6Aw0oksC66x")
                .build();

        builder.setCharset(StandardCharsets.UTF_8);

        post.setEntity(entity);

        return httpclient.execute(post, new PythonHttpClientResponseHandler());
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void addSupportedClass(Class<?> clazz){
        supportedClasses.add(clazz);
    }
}
