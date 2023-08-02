package com.bluedot.resource;

import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.BufferSolutionRepository;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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
    @RequiresPermissions("buffer-solution:get")
    public BufferSolution getBufferSolution(@PathParam("id") int id){
        return repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @POST
    @RequiresPermissions("buffer-solution:create")
    public BufferSolution addBufferSolution(@BeanParam BufferSolution bufferSolution){
        return repository.save(bufferSolution);
    }

    @PUT
    @RequiresPermissions("buffer-solution:update")
    public BufferSolution updateBufferSolution(@BeanParam BufferSolution bufferSolution){
        return ResourceUtil.updateResource(bufferSolution, bufferSolution.getId(), repository);
    }

    @DELETE
    @RequiresPermissions("buffer-solution:delete")
    public void deleteBufferSolution(@QueryParam("id") List<Integer> ids){
        repository.deleteAllById(ids);
    }

    @Path("pages")
    @RequiresPermissions("buffer-solution:get")
    public PageData<BufferSolution> getBufferSolutionPage(@BeanParam PageInfo pageInfo){
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    public BufferSolutionRepository getRepository() {
        return repository;
    }

    public void setRepository(BufferSolutionRepository repository) {
        this.repository = repository;
    }
}
