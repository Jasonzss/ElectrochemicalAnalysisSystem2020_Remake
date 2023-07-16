package com.bluedot.domain.analysis;

import com.bluedot.infrastructure.utils.LinearEquation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 22:53
 *
 * 模型的分析结果
 */
public class AnalysisResult {
    private Double rc2;
    private Double rmsec;
    private Double maec;
    private Double rp2;
    private Double rmsep;
    private Double maep;
    private Double rpd;

    private LinearEquation trainEquation;
    private LinearEquation testEquation;

    public Map<String, Double> getTestMap(){
        Map<String, Double> m = new HashMap<>();
        m.put("Rp2",rp2);
        m.put("RMSEp", rmsep);
        m.put("MAEp", maep);
        m.put("RPD", rpd);
        return m;
    }

    public Map<String, Double> getTrainMap(){
        Map<String, Double> m = new HashMap<>();
        m.put("Rc2",rc2);
        m.put("RMSEc", rmsec);
        m.put("MAEc", maec);
        return m;
    }

    public Double getRc2() {
        return rc2;
    }

    public void setRc2(Double rc2) {
        this.rc2 = rc2;
    }

    public Double getRmsec() {
        return rmsec;
    }

    public void setRmsec(Double rmsec) {
        this.rmsec = rmsec;
    }

    public Double getMaec() {
        return maec;
    }

    public void setMaec(Double maec) {
        this.maec = maec;
    }

    public Double getRp2() {
        return rp2;
    }

    public void setRp2(Double rp2) {
        this.rp2 = rp2;
    }

    public Double getRmsep() {
        return rmsep;
    }

    public void setRmsep(Double rmsep) {
        this.rmsep = rmsep;
    }

    public Double getMaep() {
        return maep;
    }

    public void setMaep(Double maep) {
        this.maep = maep;
    }

    public Double getRpd() {
        return rpd;
    }

    public void setRpd(Double rpd) {
        this.rpd = rpd;
    }

    public LinearEquation getTrainEquation() {
        return trainEquation;
    }

    public void setTrainEquation(LinearEquation trainEquation) {
        this.trainEquation = trainEquation;
    }

    public LinearEquation getTestEquation() {
        return testEquation;
    }

    public void setTestEquation(LinearEquation testEquation) {
        this.testEquation = testEquation;
    }
}
