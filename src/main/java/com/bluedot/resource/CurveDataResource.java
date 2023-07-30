package com.bluedot.resource;

import com.bluedot.application.electrochemistry.ElectrochemistryService;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.application.electrochemistry.vo.CurveFileProcessForm;
import com.bluedot.application.electrochemistry.vo.CurveProcessForm;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.CurveDataRepository;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Example;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:16
 */
public class CurveDataResource {
    @Inject
    private CurveDataRepository repository;

    @Inject
    private ElectrochemistryService service;

    public CurveData getCurveData(int curveDataId){
        return repository.findById(curveDataId).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    public CurveData addCurveData(CurveFileProcessForm form, Subject subject){
        return service.processFile(form, (String) subject.getPrincipal());
    }

    public void deleteCurveData(List<Integer> ids){
        repository.deleteAllById(ids);
    }

    public CurveData updateCurveData(CurveProcessForm form){
        return service.saveCurveData(form);
    }

    public PageData<CurveData> getCurveDataPage(PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public PageData<CurveData> getCurveDataPageByEmail(String email, PageInfo pageInfo){
        User user = new User(email);
        CurveData data = new CurveData();
        data.setUser(user);
        Example<CurveData> dataExample = Example.of(data);

        return PageData.of(repository.findAll(dataExample, pageInfo.getPageable()));
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
