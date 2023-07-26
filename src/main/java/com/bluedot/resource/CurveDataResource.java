package com.bluedot.resource;

import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.application.electrochemistry.vo.CurveFileProcessForm;
import com.bluedot.application.electrochemistry.vo.CurveProcessForm;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:16
 */
public class CurveDataResource {
    public CurveData getCurveData(int curveDataId){
        return null;
    }

    public CurveData addCurveData(CurveFileProcessForm form){
        return null;
    }

    public void deleteCurveData(List<Integer> reportId){

    }

    public CurveData updateCurveData(CurveProcessForm form){
        return null;
    }

    public PageData<CurveData> getCurveDataPage(PageInfo pageInfo){
        return null;
    }

    public PageData<CurveData> getCurveDataPageByEmail(String email, PageInfo pageInfo){
        return null;
    }
}
