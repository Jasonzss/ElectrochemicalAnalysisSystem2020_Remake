package com.bluedot.resource;

import com.bluedot.application.electrochemistry.ElectrochemistryService;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.application.electrochemistry.vo.CurveFileProcessForm;
import com.bluedot.application.electrochemistry.vo.CurveProcessForm;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.CurveDataRepository;
import com.bluedot.infrastructure.shiro.Auth;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.shiro.subject.Subject;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:16
 */
@Path("curve-data")
public class CurveDataResource {
    @Inject
    private CurveDataRepository repository;

    @Inject
    private ElectrochemistryService service;

    @GET
    @Path("{id}")
    public CurveData getCurveData(@PathParam("id") int curveDataId){
        return repository.findById(curveDataId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @POST
    public CurveData addCurveData(@BeanParam CurveFileProcessForm form,@Auth Subject subject){
        return service.processFile(form, (String) subject.getPrincipal());
    }

    @DELETE
    public void deleteCurveData(@QueryParam("id") List<Integer> ids){
        repository.deleteAllById(ids);
    }

    @PUT
    public CurveData updateCurveData(@BeanParam CurveProcessForm form){
        return service.saveCurveData(form);
    }

    @Path("pages")
    public PageData<CurveData> getCurveDataPage(@BeanParam PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public CurveDataRepository getRepository() {
        return repository;
    }

    public void setRepository(CurveDataRepository repository) {
        this.repository = repository;
    }

    public ElectrochemistryService getService() {
        return service;
    }

    public void setService(ElectrochemistryService service) {
        this.service = service;
    }
}
