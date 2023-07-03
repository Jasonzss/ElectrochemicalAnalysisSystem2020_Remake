package com.bluedot.domain.algorithm;

import cn.hutool.core.util.ReflectUtil;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.domain.file.AbstractDomainFile;
import com.bluedot.domain.file.model.JavaFile;
import com.bluedot.domain.file.model.PythonFile;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import com.bluedot.infrastructure.utils.PojoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 1:54
 * 负责管理算法的增删改查事务
 *
 * TODO 如何做到优雅的映射关系转换 ：算法id 算法实体类 算法工厂类 算法模型文件类 算法文件储存路径
 */
public class AlgorithmManager {
    private static final Logger log = LoggerFactory.getLogger(AlgorithmManager.class);

    @Inject
    private AlgorithmRepository repository;

    @Inject
    private AlgoFactory algoFactory;

    /**
     * 查询算法
     * @param algoId 算法id
     * @return 查询的目标算法
     */
    public Algorithm getAlgorithm(String algoId){
        return algoFactory.getAlgorithm(algoId);
    }

    /**
     * 创建算法
     * @param inputStream 算法内容
     * @param algorithm 算法持久化内容
     * @return 业务模型类算法
     */
    public Algorithm createAlgorithm(InputStream inputStream, PersistantAlgorithm algorithm){
        //保存到数据库，并获取生成的id
        PersistantAlgorithm savedAlgo = repository.save(algorithm);

        //创建对应的文件实体
        AbstractDomainFile file = getFileById(savedAlgo.getAlgoId());
        file.updateFile(inputStream);

        //获取算法类
        return algoFactory.getAlgorithm(savedAlgo.getAlgoId());
    }

    /**
     * 删除算法
     * @param algoId 算法id
     */
    public void deleteAlgorithm(String algoId){
        Optional<PersistantAlgorithm> algo = repository.findById(algoId);
        if(algo.isPresent()){
            PersistantAlgorithm persistantAlgorithm = algo.get();
            //删除数据库中数据
            repository.deleteById(algoId);
            //删除文件数据
            AbstractDomainFile fileById = getFileById(algoId);
            fileById.deleteFile();
        }
    }

    /**
     * 更新算法文本内容
     * @param algoId 算法id
     * @param inputStream 修改后的算法内容
     * @return 修改后算法的字节数
     * @throws FileNotFoundException 找不到要修改的算法
     */
    public long updateAlgorithm(String algoId, InputStream inputStream) throws FileNotFoundException {
        AbstractDomainFile fileById = getFileById(algoId);
        return fileById.updateFile(inputStream);
    }

    /**
     * 修改算法持久化内容
     * @param algorithm 算法
     * @return 是否发生修改
     */
    public boolean updateAlgorithm(PersistantAlgorithm algorithm) {
        Optional<PersistantAlgorithm> byId = repository.findById(algorithm.getAlgoId());
        if(byId.isPresent()){
            PersistantAlgorithm persistantAlgorithm = byId.get();
            PojoUtil.updatePojo(algorithm, persistantAlgorithm);
            repository.save(persistantAlgorithm);
            return true;
        }

        return false;
    }


    /**
     * 截取算法id的前缀判断这个类的类型 TODO 捞的不谈，想办法改改
     * @param algoId 算法id
     * @return id对应的算法类型
     */
    private AbstractDomainFile getFileById(String algoId) {
        return ReflectUtil.newInstance(getClassByType(algoId.substring(0, algoId.indexOf("_"))), algoId);
    }

    /**
     * String类型和对应文件类的转换
     * @param type String类型的文件支持
     * @return 领域模型文件类
     * TODO 有关同一数据不同表示数据之间的映射转换该如何设计？下面这种属实不够 优雅
     */
    private Class<? extends AbstractDomainFile> getClassByType(String type){
        Class<? extends AbstractDomainFile> clazz;
        if("java".equals(type)){
            clazz = JavaFile.class;
        }else if("python".equals(type)){
            clazz = PythonFile.class;
        }else {
            throw new AlgorithmException(CommonErrorCode.E_3013);
        }
        return clazz;
    }

    public AlgorithmRepository getRepository() {
        return repository;
    }

    public void setRepository(AlgorithmRepository repository) {
        this.repository = repository;
    }

    public AlgoFactory getAlgoFactory() {
        return algoFactory;
    }

    public void setAlgoFactory(AlgoFactory algoFactory) {
        this.algoFactory = algoFactory;
    }
}
