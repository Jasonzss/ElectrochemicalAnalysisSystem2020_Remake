package com.bluedot.application.electrochemistry.vo;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/14 - 15:19
 */
public class ModelAnalysisTable {
    private String materialName;
    private String preprocessAlgorithmId;
    private String modelingAlgoId;
    private List<Integer> dataIds;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getPreprocessAlgorithmId() {
        return preprocessAlgorithmId;
    }

    public void setPreprocessAlgorithmId(String preprocessAlgorithmId) {
        this.preprocessAlgorithmId = preprocessAlgorithmId;
    }

    public String getModelingAlgoId() {
        return modelingAlgoId;
    }

    public void setModelingAlgoId(String modelingAlgoId) {
        this.modelingAlgoId = modelingAlgoId;
    }

    public List<Integer> getDataIds() {
        return dataIds;
    }

    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }
}
