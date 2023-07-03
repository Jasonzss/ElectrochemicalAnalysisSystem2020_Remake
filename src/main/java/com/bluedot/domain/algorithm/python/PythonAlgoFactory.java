package com.bluedot.domain.algorithm.python;

import com.bluedot.domain.algorithm.AlgoFactory;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.domain.file.model.PythonFile;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:07
 * @Description ：
 */
public class PythonAlgoFactory implements AlgoFactory {
    public static final Logger log = LoggerFactory.getLogger(PythonAlgoFactory.class);
    /**
     * 从数据库查询算法信息
     */
    @Inject
    private AlgorithmRepository algorithmRepository;

    @Override
    public Algorithm getAlgorithm(String algoId) throws AlgorithmException {
        if(!algoId.startsWith("python")){
            return null;
        }

        //数据库查询算法
        Optional<PersistantAlgorithm> optionalAlgo = algorithmRepository.findById(algoId);

        if (optionalAlgo.isPresent()) {
            PythonFile pythonFile = new PythonFile(algoId);;
            PersistantAlgorithm persistantAlgorithm = optionalAlgo.get();

            //构建目标算法
            PythonAlgorithm python = new PythonAlgorithm();
            python.setPersistantAlgorithm(persistantAlgorithm);
            try {
                python.setInputStream(pythonFile.getInputStream());
            } catch (FileNotFoundException e) {
                throw new AlgorithmException(e, CommonErrorCode.E_3004);
            }

            return python;
        }else {
            log.info("该算法不存在");
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
