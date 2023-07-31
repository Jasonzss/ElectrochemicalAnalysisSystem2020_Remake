package com.bluedot.resource;

import com.bluedot.application.electrochemistry.ElectrochemistryService;
import com.bluedot.application.electrochemistry.dto.AnalyzedModelReport;
import com.bluedot.application.electrochemistry.vo.ModelAnalysisForm;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AnalyzedModelReportRepository;
import com.bluedot.infrastructure.shiro.Auth;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Example;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:16
 */
@Path("report")
public class ModelAnalysisReportResource {
    @Inject
    private AnalyzedModelReportRepository repository;

    @Inject
    private ElectrochemistryService service;

    @GET
    @Path("{id}")
    public AnalyzedModelReport getReport(@PathParam("id") int reportId){
        return repository.findById(reportId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @POST
    public AnalyzedModelReport addReport(@BeanParam ModelAnalysisForm form,@Auth Subject subject){
        return service.modelingAnalysis(form, (String) subject.getPrincipal());
    }

    @DELETE
    public void deleteReport(List<Integer> reportIds){
        repository.deleteAllById(reportIds);
    }

    @PUT
    public AnalyzedModelReport updateReport(@BeanParam AnalyzedModelReport report){
        return ResourceUtil.updateResource(report, report.getReportId(), repository);
    }

    @Path("pages")
    public PageData<AnalyzedModelReport> getReportPage(@BeanParam PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    @Path("pages/{email}")
    public PageData<AnalyzedModelReport> getReportPageByEmail(@PathParam("email") String email,@BeanParam PageInfo pageInfo){
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
