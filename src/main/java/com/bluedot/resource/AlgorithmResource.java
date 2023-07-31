package com.bluedot.resource;

import cn.hutool.core.io.IoUtil;
import com.bluedot.application.AlgorithmService;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.http.entity.ContentType;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.data.domain.Example;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:14
 */
@Path("algorithm")
public class AlgorithmResource {
    @Inject
    private AlgorithmRepository repository;

    @Inject
    private AlgorithmService service;

    @GET
    @Path("{id}")
    public PersistantAlgorithm getPersistantAlgorithm(@PathParam("id") String id){
        return repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @GET
    @Path("algo-content/{id}")
    public Response getAlgoContent(@PathParam("id") String id) throws FileNotFoundException {
        InputStream inputStream = service.getFileById(id).getInputStream();
        return Response.ok((StreamingOutput) output -> IoUtil.copy(inputStream, output))
                .header("Content-type", ContentType.DEFAULT_TEXT)
                .header("Content-disposition", "attachment;filename="+id+".txt")
                .header("Cache-Control", "no-cache")
                .status(200)
                .build();
    }

    @PUT
    @Path("algo-content/{id}")
    public String updateAlgoContent(@PathParam("id") String id, @FormDataParam("file") InputStream fileStream)
            throws FileNotFoundException {
        service.updateAlgorithm(id, fileStream);
        return "修改成功";
    }

    @POST
    public PersistantAlgorithm addPersistantAlgorithm(@BeanParam PersistantAlgorithm algorithm,
                                                      @FormDataParam("file") InputStream fileStream){
        Algorithm a = service.createAlgorithm(fileStream, algorithm);
        return repository.findById(a.getAlgoId()).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @PUT
    public PersistantAlgorithm updatePersistantAlgorithm(@BeanParam PersistantAlgorithm algorithm){
        return ResourceUtil.updateResource(algorithm, algorithm.getAlgoId(), repository);
    }

    @DELETE
    public void deletePersistantAlgorithm(@QueryParam("id") List<String> ids){
        repository.deleteAllById(ids);
    }

    @Path("pages")
    @GET
    public PageData<PersistantAlgorithm> getPersistantAlgorithmPage(@BeanParam PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }


    @Path("pages/{email}")
    @GET
    public PageData<PersistantAlgorithm> getPersistantAlgorithmPageByEmail(
            @PathParam("email") String email,
            @BeanParam PageInfo pageInfo){
        User user = new User(email);
        PersistantAlgorithm algorithm = new PersistantAlgorithm();
        algorithm.setCreator(user);
        Example<PersistantAlgorithm> algorithmExample = Example.of(algorithm);

        return PageData.of(repository.findAll(algorithmExample, pageInfo.getPageable()));
    }

    public AlgorithmRepository getRepository() {
        return repository;
    }

    public void setRepository(AlgorithmRepository repository) {
        this.repository = repository;
    }

    public AlgorithmService getService() {
        return service;
    }

    public void setService(AlgorithmService service) {
        this.service = service;
    }
}
