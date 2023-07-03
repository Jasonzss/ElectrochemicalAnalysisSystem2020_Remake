package com.bluedot.infrastructure.clazz;

import cn.hutool.core.util.ReflectUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Jason
 * @creationDate 2023/06/29 - 1:13
 */
public class LoadedClassRegistryTest {
    @Test
    public void test() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        LoadedClassRegistry registry = LoadedClassRegistry.Singleton.SINGLETON.getInstance();
        registry.loadClass(new FileInputStream("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.class"), "algo.java.java_test.JavaAlgorithm");
        Object classInstance = registry.getClassInstance("algo.java.java_test.JavaAlgorithm");
        ReflectUtil.invoke(classInstance, classInstance.getClass().getMethod("hello"));
        System.out.println(classInstance.getClass().hashCode());

        registry.unloadClass("algo.java.java_test.JavaAlgorithm");

        registry.loadClass(new FileInputStream("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.class"), "algo.java.java_test.JavaAlgorithm");
        classInstance = registry.getClassInstance("algo.java.java_test.JavaAlgorithm");
        System.out.println(classInstance.getClass().hashCode());
    }
    
    @Test
    public void test1() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        //这是怎么找到我写的类的？路径放这么奇怪都能找到
        Class<?> clazz = Class.forName("algo.java.java_test.JavaAlgorithm");
        Object classInstance = clazz.newInstance();
        ReflectUtil.invoke(classInstance, classInstance.getClass().getMethod("hello"));
    }



    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        InputStreamClassLoader classLoader = new InputStreamClassLoader();
        classLoader.setInputStream(new FileInputStream("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.class"));
        Class<?> clazz = classLoader.loadClass("algo.java.java_test.JavaAlgorithm");
        Object object = clazz.newInstance();
        System.out.println("class HashCode: "+clazz.hashCode());
        System.out.println(object.getClass().getClassLoader());
        System.out.println(Class.forName("algo.java.java_test.JavaAlgorithm").hashCode());
        System.out.println("-----------------------");
        //让类和自定义类加载器不再互相引用
        ClassLoader classLoader1 = clazz.getClassLoader();
        classLoader1 = null;
        clazz = null;
        object = null;

        System.gc();//实际场景不这么用

        classLoader = new InputStreamClassLoader();
        classLoader.setInputStream(new FileInputStream("src\\main\\resources\\algo\\java\\java_test\\JavaAlgorithm.class"));
        clazz = classLoader.loadClass("algo.java.java_test.JavaAlgorithm");
        object = clazz.newInstance();
        System.out.println("class HashCode: "+clazz.hashCode());
        System.out.println(object.getClass().getClassLoader());
        System.out.println(Class.forName("algo.java.java_test.JavaAlgorithm").hashCode());
    }
}
