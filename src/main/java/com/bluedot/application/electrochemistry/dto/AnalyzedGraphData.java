package com.bluedot.application.electrochemistry.dto;

import com.bluedot.infrastructure.utils.UnitUtil;

import java.util.Map;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 20:36
 */
public class AnalyzedGraphData {
    private String equation;
    private Map<String, Double> params;
    private Double[][] points;
    private String xUnit;
    private String yUnit;

    public AnalyzedGraphData(String equation, Map<String, Double> params, Double[][] points) {
        this.equation = equation;
        this.params = params;
        this.points = points;
        this.xUnit = UnitUtil.Unit.MOL_UM.getUnit();
        this.yUnit = UnitUtil.Unit.MOL_UM.getUnit();;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public Map<String, Double> getParams() {
        return params;
    }

    public void setParams(Map<String, Double> params) {
        this.params = params;
    }

    public Double[][] getPoints() {
        return points;
    }

    public void setPoints(Double[][] points) {
        this.points = points;
    }

    public String getxUnit() {
        return xUnit;
    }

    public void setxUnit(String xUnit) {
        this.xUnit = xUnit;
    }

    public String getyUnit() {
        return yUnit;
    }

    public void setyUnit(String yUnit) {
        this.yUnit = yUnit;
    }
}
