package com.bluedot.resource;

import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.BufferSolutionRepository;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:19
 */
@Path("buffer-solution")
public class BufferSolutionResource {
    @Inject
    private BufferSolutionRepository repository;

    @GET
    @Path("{id}")
    public BufferSolution getBufferSolution(@PathParam("id") int id){
        return repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @POST
    public BufferSolution addBufferSolution(@BeanParam BufferSolution bufferSolution){
        return repository.save(bufferSolution);
    }

    @PUT
    public BufferSolution updateBufferSolution(@BeanParam BufferSolution bufferSolution){
        return ResourceUtil.updateResource(bufferSolution, bufferSolution.getId(), repository);
    }

    @DELETE
    public void deleteBufferSolution(@QueryParam("id") List<Integer> ids){
        repository.deleteAllById(ids);
    }

    public PageData<BufferSolution> getBufferSolutionPage(PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public BufferSolutionRepository getRepository() {
        return repository;
    }

    public void setRepository(BufferSolutionRepository repository) {
        this.repository = repository;
    }
}
