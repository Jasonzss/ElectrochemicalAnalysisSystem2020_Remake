package com.bluedot.domain.file.model;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import com.bluedot.domain.file.AbstractDomainFile;
import com.bluedot.domain.file.exception.JavaCodeException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 16:21
 * @Description ：
 * 约定：所有java文件放在\src\main\resources\algo\java目录下，每个算法一个文件夹，
 * 文件夹名称为 算法id：【java_12345678】，这同时也是领域模型文件id
 * 文件夹内的java文件名称统一命名为 JavaAlgorithm.java ，与类名相同
 */
public class JavaFile extends AbstractDomainFile {
    public static final String ALGORITHM_PACKAGE_NAME_PREFIX = "algo.java";
    public static final String ALGORITHM_CLASS_NAME = "JavaAlgorithm";

    private static final Logger log = LoggerFactory.getLogger(JavaFile.class);



    //现在改了逻辑，对于类名统一为JavaAlgorithm，因为用户对于类名叫什么知道也没什么用，所以使用了这一规定
//    private String fullClassName;

    public JavaFile(String fileId) {
        super(fileId);
    }


    @Override
    protected String generateName() {
        return "JavaAlgorithm.java";
    }


    @Override
    protected String generatePath() {
        return "src/main/resources/algo/java/"+getFileId()+"/";
    }

    /**
     * 检查是否存在包信息，不存在则加入
     * @param is 修改内容后的InputStream
     * @return 包含了包信息的InputStream
     */
    @Override
    protected InputStream doUpdate(InputStream is) {
        FastByteArrayOutputStream read = IoUtil.read(is);

        ByteArrayInputStream is1 = new ByteArrayInputStream(read.toByteArray());
        InputStream is2 = new ByteArrayInputStream(read.toByteArray());

        BufferedReader reader = IoUtil.getReader(is1, StandardCharsets.UTF_8);
        boolean hasPackageInfo = false;
        String packageInfo = getPackageInfo();
        while (!hasPackageInfo){
            try {
                String s = reader.readLine();
                if(s.contains(packageInfo)){
                    hasPackageInfo = true;
                }else if (s.contains(" class ")){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!hasPackageInfo){
            is2 = new SequenceInputStream(IoUtil.toStream(packageInfo+"\n", StandardCharsets.UTF_8), is2);
        }

        return is2;
    }

    /**
     * 检查自身源码是否正常可用
     */
    public void checkSourceCode() throws JavaCodeException{
        checkSyntax();
        checkSecurity();
    }

    /**
     * TODO 检查自身源码语法是否正常
     */
    private void checkSyntax(){
        try{
            log.warn("todo : java算法文件语法检查");
        }catch (Exception e){
            throw new JavaCodeException(e, CommonErrorCode.E_3002);
        }
    }

    /**
     * TODO 检查改源码内容是否安全符合安全要求
     */
    private void checkSecurity(){
        try {
            log.warn("todo : java算法文件安全检查");
        }catch (Exception e){
            throw new JavaCodeException(e, CommonErrorCode.E_3003);
        }
    }

    /**
     * 删除class文件
     */
    @Override
    protected void doDelete() {
        String javaPath = getFile().getAbsolutePath();
        new File(javaPath.substring(0, javaPath.length()-4).concat("class")).deleteOnExit();
    }

    /**
     * 在java文件头部加入package信息
     * @param Stream 客户端传入的输入流
     * @return 加入package信息的输入流
     */
    private InputStream overwriteContent(InputStream... Stream) {
        ByteArrayInputStream inputStream = IoUtil.toStream(getPackageInfo(), StandardCharsets.UTF_8);
        return new SequenceInputStream(Collections.enumeration(Lists.asList(inputStream, Stream)));
    }

    private String getPackageInfo(){
        return "package " + ALGORITHM_PACKAGE_NAME_PREFIX + "." + getFileId() + ";";
    }

    public static String getFullClassName(String algoId){
        return ALGORITHM_PACKAGE_NAME_PREFIX+"."+algoId+"."+ALGORITHM_CLASS_NAME;
    }

    public ClassFile compile(){
        return new ClassFile(getFileId(), getFile());
    }
}
