package com.bluedot.domain.algorithm.java;

import com.bluedot.domain.algorithm.AlgoFactory;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
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
    /**
     * 自定义文件加载class对象类，将class文件加载到内存的类对象
     */
    @Inject
    private final LoadedAlgorithmRegistry registry = new LoadedAlgorithmRegistry();
    /**
     * 从数据库查询算法信息
     */
    @Inject
    private AlgorithmRepository algorithmRepository;

    @Override
    public Algorithm getAlgorithm(String algoId) throws AlgorithmException {
        //判断算法id是否java开头，是的话才能从这获取到
        if(!algoId.startsWith("java")){
            return null;
        }

        //创建一个算法对象
        //查询数据库中是否存在此方法
        Optional<PersistantAlgorithm> optionalAlgo = algorithmRepository.findById(algoId);

        if (optionalAlgo.isPresent()) {
            //查询缓存中是否存在目标算法，存在的话直接返回
            JavaAlgorithm a = registry.getAlgorithm(algoId);
            if(a != null){
                return a;
            }

            PersistantAlgorithm algorithm = optionalAlgo.get();

            //TODO 这部分逻辑对于JavaAlgoFactory来说是否有些无关
            //构建目标算法
            JavaAlgorithm javaAlgorithm = registry.getAlgorithm(algoId);
            javaAlgorithm.setPersistantAlgorithm(algorithm);

            return javaAlgorithm;
        } else {
            log.info("该算法不存在");
            registry.destroyAlgorithm(algoId);
            throw new AlgorithmException(CommonErrorCode.E_3006);
        }
    }

    public AlgorithmRepository getAlgorithmRepository() {
        return algorithmRepository;
    }

    public void setAlgorithmRepository(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }
}
