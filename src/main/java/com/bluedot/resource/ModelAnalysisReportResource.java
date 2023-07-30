package com.bluedot.resource;

import com.bluedot.application.electrochemistry.ElectrochemistryService;
import com.bluedot.application.electrochemistry.dto.AnalyzedModelReport;
import com.bluedot.application.electrochemistry.vo.CurveProcessForm;
import com.bluedot.application.electrochemistry.vo.ModelAnalysisForm;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AnalyzedModelReportRepository;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:16
 */
public class ModelAnalysisReportResource {
    @Inject
    private AnalyzedModelReportRepository repository;

    @Inject
    private ElectrochemistryService service;

    public AnalyzedModelReport getReport(int reportId){
        return repository.findById(reportId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    public AnalyzedModelReport addReport(ModelAnalysisForm form, Subject subject){
        return service.modelingAnalysis(form, (String) subject.getPrincipal());
    }

    public void deleteReport(List<Integer> reportIds){
        repository.deleteAllById(reportIds);
    }

    public AnalyzedModelReport updateReport(@BeanParam AnalyzedModelReport report){
        return ResourceUtil.updateResource(report, report.getReportId(), repository);
    }


    public PageData<AnalyzedModelReport> getReportPage(PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public PageData<AnalyzedModelReport> getReportPageByEmail(String email, PageInfo pageInfo){
        User user = new User(email);
        AnalyzedModelReport report = new AnalyzedModelReport();
        report.setUser(user);
        Example<AnalyzedModelReport> reportExample = Example.of(report);

        return PageData.of(repository.findAll(reportExample, pageInfo.getPageable()));
    }

    public AnalyzedModelReportRepository getRepository() {
        return repository;
    }

    public void setRepository(AnalyzedModelReportRepository repository) {
        this.repository = repository;
    }

    public ElectrochemistryService getService() {
        return service;
    }

    public void setService(ElectrochemistryService service) {
        this.service = service;
    }
}
