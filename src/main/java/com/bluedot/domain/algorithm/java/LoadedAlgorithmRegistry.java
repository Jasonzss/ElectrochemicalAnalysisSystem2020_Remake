package com.bluedot.domain.algorithm.java;

import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.file.model.ClassFile;
import com.bluedot.domain.file.model.JavaFile;
import com.bluedot.infrastructure.clazz.LoadedClassRegistry;
import com.bluedot.infrastructure.exception.CommonErrorCode;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason
 * @creationDate 2023/06/27 - 20:35
 *
 * 加载类注册表，记录了所有被加载的类以及对应的classloader、实例、类对象
 *
 *
 * 一个类的加载、销毁工作在此完成
 */
public class LoadedAlgorithmRegistry {
    /**
     * TODO 后续可以把这些加入到缓存中
     * 已加载的算法对象
     */
    private final Map<String, JavaAlgorithm> loadedAlgorithm = new HashMap<>(16);

    private final LoadedClassRegistry registry = LoadedClassRegistry.Singleton.SINGLETON.getInstance();

    /**
     * 获取算法
     *
     * 如果算法缓存中仍有算法则直接判断该算法是否过期
     *      没有过期的话直接返回
     *      过期了就销毁算法并生成新的算法
     * @param algoId 算法id
     * @return 目标算法
     */
    public JavaAlgorithm getAlgorithm(String algoId) {
        JavaFile file = new JavaFile(algoId);
        if(loadedAlgorithm.containsKey(algoId)){
            JavaAlgorithm algorithm = loadedAlgorithm.get(algoId);

            if(file.lastModified() > algorithm.getFileUpdateTimeStamp()){
                //算法过期销毁现有算法
                destroyAlgorithm(algoId);
            }else {
                return algorithm;
            }
        }

        return createAlgorithm(file);
    }

    public void destroyAlgorithm(String algoId){
        JavaAlgorithm remove = loadedAlgorithm.remove(algoId);
        if(remove != null){
            remove = null;
            registry.unloadClass(JavaFile.getFullClassName(algoId));
        }
    }

    private JavaAlgorithm createAlgorithm(JavaFile javaFile) {
        ClassFile compile = javaFile.compile();
        try {
            Object instance = registry.loadInstance(compile.getInputStream(), JavaFile.getFullClassName(javaFile.getFileId()));
            JavaAlgorithm algorithm = new JavaAlgorithm();
            algorithm.setDomainModelFile(javaFile);
            algorithm.setTarget(instance);
            algorithm.setFileUpdateTimeStamp(javaFile.lastModified());
            return algorithm;
        } catch (ClassNotFoundException e) {
            throw new AlgorithmException(CommonErrorCode.E_3005);
        } catch (FileNotFoundException e) {
            throw new AlgorithmException(CommonErrorCode.E_3004);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new AlgorithmException(CommonErrorCode.E_3019);
        }
    }
}
