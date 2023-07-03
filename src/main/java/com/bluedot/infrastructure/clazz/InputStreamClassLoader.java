package com.bluedot.infrastructure.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * @author Jason
 * @creationDate 2023/06/29 - 0:34
 */
public class InputStreamClassLoader extends ClassLoader {
    private static final Logger log = LoggerFactory.getLogger(FileClassLoader.class);

    /**
     * 用于创建文件类包含路径的文件名称，可以是绝对路径也可以是相对路径
     */
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 加载class字节码文件到内存中的class对象
     * @param className 全类名
     * @return 字节码文件对应的class对象
     * @throws ClassNotFoundException 找不到该字节码文件
     */
    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        // 如果类的全类名以java开头，则使用JVM的类加载器加载
        if(className.startsWith("java")){
            return getSystemClassLoader().loadClass(className);
        }
        Class<?> clazz;

        try(InputStream inputStream = this.inputStream) {
            clazz = instantiateClass(className, inputStream, inputStream.available());
        } catch (IOException e){
            log.error("load class failed : "+className);
            throw new ClassNotFoundException("load class failed : "+className ,e);
        }

        return clazz;
    }

    /**
     * 将Class字节码IO流实例化为class对象
     * @param name 全类名
     * @param fin 字节码输入流
     * @param len 读取字节码的长度
     * @return 字节码文件对应的class对象
     * @throws IOException 读取字节码输入流失败
     */
    private Class<?> instantiateClass(String name, InputStream fin, int len) throws IOException {
        byte[] raw = new byte[len];
        fin.read(raw);
        fin.close();
        return defineClass(name, raw, 0, raw.length);
    }
}
