package com.bluedot.application.electrochemistry.vo;

import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.infrastructure.utils.Quantity;
import com.bluedot.infrastructure.utils.UnitUtil;
import org.apache.commons.fileupload.FileItem;

import java.math.BigDecimal;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 12:31
 */
public class CurveFileProcessForm {
    private FileItem file;
    private Integer materialTypeId;
    private String materialName;
    private Double materialSolubility;
    private String unit;
    private Integer bufferSolutionId;
    private double ph;
    private String desc;

    public FileItem getFile() {
        return file;
    }

    public void setFile(FileItem file) {
        this.file = file;
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Double getMaterialSolubility() {
        return materialSolubility;
    }

    public void setMaterialSolubility(Double materialSolubility) {
        this.materialSolubility = materialSolubility;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public CurveData generateCurveData(){
        CurveData data = new CurveData();
        data.setMaterialType(new MaterialType(materialTypeId));
        data.setMaterialName(materialName);
        data.setMaterialSolubility(new Quantity(BigDecimal.valueOf(materialSolubility), UnitUtil.getUnit(unit)));
        data.setBufferSolution(new BufferSolution(bufferSolutionId));
        data.setPh(ph);
        data.setDescription(desc);
        return data;
    }
}
