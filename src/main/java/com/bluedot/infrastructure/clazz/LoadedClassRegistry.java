package com.bluedot.infrastructure.clazz;

import cn.hutool.core.util.ClassLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason
 * @creationDate 2023/06/28 - 23:55
 *
 * 用于注册所有已加载的类及其相关信息
 */
public class LoadedClassRegistry {
    private static final Logger log = LoggerFactory.getLogger(LoadedClassRegistry.class);

    private static final Map<String, Map.Entry<Class<?>, Object> > loadedClass = new HashMap<>(16);

    private LoadedClassRegistry() {
    }

    public enum Singleton{
        SINGLETON;
        private final LoadedClassRegistry instance;
        Singleton(){
            instance = new LoadedClassRegistry();
        }
        public LoadedClassRegistry getInstance() {
            return instance;
        }
    }


    public void loadClass(InputStream inputStream, String fullClassName) throws ClassNotFoundException {
        Class<?> clazz = doLoadClass(inputStream, fullClassName);
        loadedClass.put(fullClassName, new AbstractMap.SimpleEntry<>(clazz, null));
    }

    public Object loadInstance(InputStream inputStream, String fullClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = doLoadClass(inputStream, fullClassName);
        Object instance = clazz.newInstance();
        loadedClass.put(fullClassName, new AbstractMap.SimpleEntry<>(clazz, instance));
        return instance;
    }

    private Class<?> doLoadClass(InputStream inputStream, String fullClassName) throws ClassNotFoundException {
        if(loadedClass.containsKey(fullClassName)){
            log.info("class had loaded, it will be reloaded : "+fullClassName);
        }
        InputStreamClassLoader classLoader = new InputStreamClassLoader();
        classLoader.setInputStream(inputStream);
        return classLoader.loadClass(fullClassName);
    }

    public Object getClassInstance(String fullClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Map.Entry<Class<?>, Object> entry = loadedClass.get(fullClassName);

        if(entry != null){
            Object instance = entry.getValue();
            Class<?> clazz = entry.getKey();

            if (instance == null){
                instance = clazz.newInstance();
                loadedClass.put(fullClassName, new AbstractMap.SimpleEntry<>(clazz, instance));
            }

            return instance;
        }else {
            throw new ClassNotFoundException();
        }
    }

    public void unloadClass(String fullClassName){
        if (loadedClass.containsKey(fullClassName)) {
            Map.Entry<Class<?>, Object> remove = loadedClass.remove(fullClassName);

            Class<?> cls = remove.getKey();
            Object instance = remove.getValue();
            ClassLoader classLoader = cls.getClassLoader();

            remove = null;
            classLoader = null;
            instance = null;
            cls = null;
        }
    }
}
