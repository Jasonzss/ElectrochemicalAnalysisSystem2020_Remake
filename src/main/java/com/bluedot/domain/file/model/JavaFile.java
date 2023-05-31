package com.bluedot.domain.file.model;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.Assert;
import com.bluedot.domain.file.AbstractDomainModelFile;
import com.bluedot.domain.file.FileSystemException;
import com.bluedot.domain.file.exception.JavaCodeException;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 16:21
 * @Description ：
 * 约定：所有java文件放在\src\main\resources\algo\java目录下，每个算法一个文件夹，
 * 文件夹名称为 算法id：【java_12345678】，这同时也是领域模型文件id
 * 文件夹内的java文件名称统一命名为 JavaAlgorithm.java ，与类名相同
 */
public class JavaFile extends AbstractDomainModelFile {

    private String fullClassName;

    public JavaFile(String pathname) {
        super(pathname);
    }

    public JavaFile(String parent, String child) {
        super(parent, child);
    }

    public JavaFile(File parent, String child) {
        super(parent, child);
    }

    public JavaFile(URI uri) {
        super(uri);
    }

    @Override
    public String getSuffix() {
        return ".java";
    }

    /**
     * 从java文件中读取全类名，文件不存在异常的情况下通常可以读成功，但是如果乱换行的话还是会失败
     * 要求是 package、class 关键字和对应的值在一行，不能换行
     * @return 全类名
     */
    private String readClassName() {
        FileReader reader = new FileReader(this);
        List<String> list = reader.readLines();
        String className = "";
        String packageName = "";
        for(String line : list){
            if(line.contains("package ")){
                line = line.replace(" ","");
                packageName = line.substring(7,line.indexOf(';'));
            }
            if(line.contains(" class ")){
                int i = line.indexOf(" class ");
                Integer start = null;
                Integer end = null;
                for (int j = i+6; j < line.length(); j++) {
                    if(start == null){
                        if(line.charAt(j) != ' '){
                            start = j;
                        }
                    }else {
                        if (line.charAt(j) == ' ' || line.charAt(j) == '{'){
                            end = j;
                            break;
                        }else {
                            end = line.length();
                        }
                    }
                }
                try{
                    Assert.notNull(start);
                    Assert.notNull(end);
                    className = line.substring(start, end);
                    break;
                }catch (IllegalArgumentException e){
                    throw new FileSystemException("there is no class name in the source file:"+getPath(), e);
                }
            }
        }

        String name = packageName+className;

        if ("".equals(name)){
            throw new JavaCodeException("the java source code don't have a class name:"+getPath());
        }
        return name;
    }

    public String getFullClassName() {
        if(fullClassName == null){
            return (fullClassName = readClassName());
        }
        return fullClassName;
    }

    /**
     * 检查自身源码是否正常可用
     */
    public void checkSourceCode() throws JavaCodeException{
        checkSyntax();
        checkSecurity();
    }

    /**
     * 检查自身源码语法是否正常
     */
    private void checkSyntax(){
        try{

        }catch (Exception e){
            throw new JavaCodeException("the code has a syntax err", e);
        }
    }

    /**
     * 检查改源码内容是否安全符合安全要求
     */
    private void checkSecurity(){
        try {

        }catch (Exception e){
            throw new JavaCodeException("calls to sensitive methods are not allowed", e);
        }
    }
}
