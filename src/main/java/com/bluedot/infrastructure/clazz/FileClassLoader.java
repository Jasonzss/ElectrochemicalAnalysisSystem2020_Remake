package com.bluedot.infrastructure.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 8:46
 * @Description ：加载class字节码文件到内存的类加载器
 *
 * 相关名词定义
 * filename
 */
public class FileClassLoader extends ClassLoader {
    private static final Logger log = LoggerFactory.getLogger(FileClassLoader.class);

    /**
     * 用于创建文件类包含路径的文件名称，可以是绝对路径也可以是相对路径
     */
    private File classFile;

    public void setClassFile(String pathFileName) {
        this.classFile = new File(pathFileName);
    }

    public void setClassFile(File classFile) {
        this.classFile = classFile;
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

        try(FileInputStream fileInputStream = new FileInputStream(classFile)) {
            clazz = instantiateClass(className, fileInputStream, classFile.length());
        } catch (FileNotFoundException e) {
            log.error("can't find class file named:"+classFile.getPath());
            throw new ClassNotFoundException("can't find class file:"+classFile.getPath(),e);
        } catch (IOException e){
            log.error("can't read the class file:"+classFile.getPath());
            throw new ClassNotFoundException("can't read the class file:"+classFile.getPath(),e);
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
    private Class<?> instantiateClass(String name, InputStream fin, long len) throws IOException {
        byte[] raw = new byte[(int)len];
        fin.read(raw);
        fin.close();
        return defineClass(name, raw, 0, raw.length);
    }

    /**
     * 方便使用的loadClass方法，可能会有些不规范
     */
    public Class<?> loadFileClass(String fileName, String className) throws ClassNotFoundException {
        setClassFile(fileName);
        return loadClass(className);
    }

    public Class<?> loadFileClass(File file, String className) throws ClassNotFoundException {
        setClassFile(file);
        return loadClass(className);
    }
}
