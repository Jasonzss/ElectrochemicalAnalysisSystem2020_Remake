package com.bluedot.domain.analysis.model;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 17:53
 */
public class AnalyzedData {
    private QuantitySet currentSet;
    private QuantitySet concentrationSet;
    private QuantitySet predictedConcentrationSet;

    public AnalyzedData() {
    }

    public AnalyzedData(QuantitySet currentSet, QuantitySet concentrationSet, QuantitySet predictedConcentrationSet) {
        this.currentSet = currentSet;
        this.concentrationSet = concentrationSet;
        this.predictedConcentrationSet = predictedConcentrationSet;
    }

    public QuantitySet getCurrentSet() {
        return currentSet;
    }

    public void setCurrentSet(QuantitySet currentSet) {
        this.currentSet = currentSet;
    }

    public QuantitySet getConcentrationSet() {
        return concentrationSet;
    }

    public void setConcentrationSet(QuantitySet concentrationSet) {
        this.concentrationSet = concentrationSet;
    }

    public QuantitySet getPredictedConcentrationSet() {
        return predictedConcentrationSet;
    }

    public void setPredictedConcentrationSet(QuantitySet predictedConcentrationSet) {
        this.predictedConcentrationSet = predictedConcentrationSet;
    }
}
