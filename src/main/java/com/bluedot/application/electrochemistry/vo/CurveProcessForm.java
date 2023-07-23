package com.bluedot.application.electrochemistry.vo;

import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.domain.process.model.Curve;
import com.bluedot.domain.process.model.Point;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.infrastructure.utils.Quantity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/14 - 16:39
 */
public class CurveProcessForm {
    private Integer curveDataId;
    private String materialName;
    //物质溶度，包括了单位，以空格为单位
    private String materialSolubility;
    private Integer materialTypeId;
    private Integer bufferSolutionId;
    private double ph;
    private String desc;

    private String[][] newestPointData;
    private String newestEp;
    private String newestIp;

    public Integer getCurveDataId() {
        return curveDataId;
    }

    public void setCurveDataId(Integer curveDataId) {
        this.curveDataId = curveDataId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialSolubility() {
        return materialSolubility;
    }

    public void setMaterialSolubility(String materialSolubility) {
        this.materialSolubility = materialSolubility;
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public Integer getBufferSolutionId() {
        return bufferSolutionId;
    }

    public void setBufferSolutionId(Integer bufferSolutionId) {
        this.bufferSolutionId = bufferSolutionId;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[][] getNewestPointData() {
        return newestPointData;
    }

    public void setNewestPointData(String[][] newestPointData) {
        this.newestPointData = newestPointData;
    }

    public String getNewestEp() {
        return newestEp;
    }

    public void setNewestEp(String newestEp) {
        this.newestEp = newestEp;
    }

    public String getNewestIp() {
        return newestIp;
    }

    public void setNewestIp(String newestIp) {
        this.newestIp = newestIp;
    }

    public CurveData generateCurveData(){
        CurveData data = new CurveData();
        data.setCurveDataId(curveDataId);
        data.setMaterialType(new MaterialType(materialTypeId));
        data.setMaterialName(materialName);
        data.setMaterialSolubility(new Quantity(materialSolubility));
        data.setBufferSolution(new BufferSolution(bufferSolutionId));
        data.setPh(ph);
        data.setDescription(desc);

        List<Point> points = new ArrayList<>();
        for(String[] point : newestPointData){
            points.add(new Point(point[0], point[1]));
        }
        data.setNewestPointsData(new Curve(points));
        data.setNewestEp(new Quantity(newestEp));
        data.setNewestIp(new Quantity(newestIp));
        return data;
    }
}
