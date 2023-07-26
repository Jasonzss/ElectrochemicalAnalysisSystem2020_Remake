package com.bluedot.resource;

import com.bluedot.application.electrochemistry.dto.AnalyzedModelReport;
import com.bluedot.application.electrochemistry.vo.ModelAnalysisForm;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:16
 */
public class ModelAnalysisReportResource {
    public AnalyzedModelReport getReport(int reportId){
        return null;
    }

    public AnalyzedModelReport addReport(ModelAnalysisForm form){
        return null;
    }

    public void deleteReport(List<Integer> reportId){

    }

    public AnalyzedModelReport updateReport(){
        return null;
    }


    public PageData<AnalyzedModelReport> getReportPage(PageInfo pageInfo){
        return null;
    }

    public PageData<AnalyzedModelReport> getReportPageByEmail(String email, PageInfo pageInfo){
        return null;
    }
}
