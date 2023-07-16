package com.bluedot.domain.file.model;

import com.bluedot.domain.file.AbstractDomainFile;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 21:29
 */
public class ModelAnalysisGraphFile extends AbstractDomainFile {
    private static final String TEST_PATH = "test";
    private static final String TRAIN_PATH = "train";

    public ModelAnalysisGraphFile(Integer id, boolean isTest) {
        super((isTest ? TEST_PATH : TRAIN_PATH) + id);
    }

    @Override
    protected String generateName() {
        return "graph.png";
    }

    @Override
    protected String generatePath() {
        return "src/main/resources/analysis/"+getFileId()+"/";
    }
}
