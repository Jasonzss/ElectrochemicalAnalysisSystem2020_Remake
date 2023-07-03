package com.bluedot.domain.algorithm;

import com.bluedot.domain.algorithm.po.PersistantAlgorithm;
import com.bluedot.domain.file.AbstractDomainFile;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 14:32
 * @Description ：
 */
public abstract class AbstractAlgorithm implements Algorithm{
    private PersistantAlgorithm persistantAlgorithm;

    private AbstractDomainFile domainModelFile;

    @Override
    public boolean support(String algoType) {
        if(algoType == null){
            return false;
        }
        return persistantAlgorithm.getAlgoType().equals(algoType);
    }

    public AbstractAlgorithm(String algoId) {
        persistantAlgorithm = new PersistantAlgorithm(algoId);
        persistantAlgorithm.setAlgoId(algoId);
    }

    public AbstractAlgorithm(PersistantAlgorithm persistantAlgorithm) {
        this.persistantAlgorithm = persistantAlgorithm;
    }

    public AbstractAlgorithm() {

    }

    @Override
    public String getAlgoId() {
        return persistantAlgorithm.getAlgoId();
    }

    /**
     * 得到算法使用的编程语言
     * @return 编程语言
     */
    protected abstract String getLanguage();

    public void setPersistantAlgorithm(PersistantAlgorithm persistantAlgorithm){
        this.persistantAlgorithm = persistantAlgorithm;
    }

    public PersistantAlgorithm getPersistantAlgorithm() {
        return persistantAlgorithm;
    }

    public AbstractDomainFile getDomainModelFile() {
        return domainModelFile;
    }

    public void setDomainModelFile(AbstractDomainFile domainModelFile) {
        this.domainModelFile = domainModelFile;
    }
}
