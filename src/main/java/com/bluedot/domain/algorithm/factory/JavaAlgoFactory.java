package com.bluedot.domain.algorithm.factory;

import cn.hutool.core.io.IoUtil;
import com.bluedot.domain.algorithm.AbstractAlgorithm;
import com.bluedot.domain.algorithm.AlgoFactory;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.AlgorithmException;
import com.bluedot.domain.algorithm.model.JavaAlgorithm;
import com.bluedot.domain.file.FileFactory;
import com.bluedot.domain.file.model.ClassFile;
import com.bluedot.domain.file.model.JavaFile;
import com.bluedot.infrastructure.clazz.FileClassLoader;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:06
 * @Description ：获取JavaAlgorithm对象
 *
 * TODO 也许可以加个缓存，以免重复多余的IO操作？
 */
public class JavaAlgoFactory implements AlgoFactory {
    public static final Logger log = LoggerFactory.getLogger(JavaAlgoFactory.class);
    public static final String ALGORITHM_CLASS_NAME = "JavaAlgorithm";

    /**
     * 文件管理的接口
     */
    @Inject
    private FileFactory fileManager;
    /**
     * 自定义文件加载class对象类，将class文件加载到内存的类对象
     */
    @Inject
    private FileClassLoader classLoader;
    /**
     * 从数据库查询算法信息
     */
    @Inject
    private AlgorithmRepository algorithmRepository;

    @Override
    public Algorithm getAlgorithm(String algoId) throws AlgorithmException {
        Optional<AbstractAlgorithm> optionalAlgo = algorithmRepository.findById(algoId);

        if (optionalAlgo.isPresent()) {
            JavaFile javaFile;
            ClassFile classFile;
            AbstractAlgorithm algorithm = optionalAlgo.get();
            try {
                javaFile = fileManager.getModelFile(algorithm.getAlgoId(), JavaFile.class);
                classFile = fileManager.getModelFile(algorithm.getAlgoId(), ClassFile.class);
                //检查重新编译
                classFile.checkUpdateClass();

                //构建目标算法
                JavaAlgorithm javaAlgorithm = new JavaAlgorithm();
                javaAlgorithm.setSourceCode(IoUtil.read(new FileInputStream(javaFile)));
                javaAlgorithm.setClazz(classLoader.loadFileClass(classFile, ALGORITHM_CLASS_NAME));
                return javaAlgorithm;
            } catch (FileNotFoundException e) {
                log.warn("算法文件已删除或丢失");
                throw new AlgorithmException("算法文件已删除或丢失");
            } catch (ClassNotFoundException e) {
                log.warn(e.getMessage());
                throw new AlgorithmException(e);
            }
        }else {
            throw new AlgorithmException("该算法不存在");
        }
    }
}
