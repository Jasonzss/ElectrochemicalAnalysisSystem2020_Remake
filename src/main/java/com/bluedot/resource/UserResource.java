package com.bluedot.resource;

import com.bluedot.application.AlgorithmService;
import com.bluedot.application.UserService;
import com.bluedot.application.electrochemistry.dto.AnalyzedModelReport;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.domain.algorithm.Algorithm;
import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.repository.AlgorithmRepository;
import com.bluedot.infrastructure.repository.AnalyzedModelReportRepository;
import com.bluedot.infrastructure.repository.CurveDataRepository;
import com.bluedot.infrastructure.repository.UserRepository;
import com.bluedot.infrastructure.shiro.Auth;
import com.bluedot.infrastructure.utils.ResourceUtil;
import com.bluedot.resource.dto.PageData;
import com.bluedot.resource.vo.PageInfo;
import com.bluedot.resource.vo.UploadFile;
import com.bluedot.resource.vo.UserInfo;
import com.bluedot.resource.vo.UserRegistryForm;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.data.domain.Example;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Optional;

/**
 * 用户资源
 * <p>
 * TODO 有部分是给管理员管理的资源，理论上不应该出现在这里
 *
 * @author Jason
 * @since 2023/05/28 - 14:32
 */
@Path("users")
@Produces({"application/json;charset=UTF-8"})
public class UserResource {
    @Inject
    private UserRepository repository;

    @Inject
    private UserService userService;

    @Inject
    private AlgorithmService algorithmService;

    @Inject
    private AlgorithmRepository algoRepository;

    @Inject
    private CurveDataRepository curveDataRepository;

    @Inject
    private AnalyzedModelReportRepository reportRepository;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("user:get:self")
    public User getUser(@PathParam("email") final String email) {
        Optional<User> user = repository.findById(email);
        User user1 = user.orElse(null);
        return user.orElse(null);
    }

    @DELETE
    @Path("{email}")
    @RequiresPermissions("user:delete:self")
    public void deleteUser(@PathParam("email") final String email) {
        repository.deleteById(email);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("user:create:self")
    public User registerUser(@BeanParam UserRegistryForm info,
                             @FormDataParam("registry-code") @DefaultValue("") String registryCode) {
        return userService.registerUser(info, registryCode);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresPermissions("user:update:self")
    public User updateUser(@BeanParam UserInfo form) {
        return userService.updateUser(form);
    }

    @GET
    @Path("pages")
    @RequiresPermissions("user:get:*")
    public PageData<User> getUserPage(@BeanParam PageInfo pageInfo) {
        return PageData.of(repository.findAll(pageInfo.getPageable()));
    }

    /**
     * 用户头像的查询是属于用户资源的子资源，所以将用户头像相关方法移入这里
     *
     * @param fileData   图片文件相关信息
     * @param fileStream 图片文件IO流
     * @param subject    shiro认证对象
     */
    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path("img")
    @RequiresPermissions("user:update:self")
    public String updateUserImg(@FormDataParam("file") InputStream fileStream,
                                @FormDataParam("file") FormDataContentDisposition fileData,
                                @Auth Subject subject) {

        return userService.updateUserImg((String) subject.getPrincipal(),
                new UploadFile(fileData, fileStream));
    }

    @Path("registry-email")
    @POST
    @RequiresGuest
    public String sendRegistryEmail(@Auth Subject subject) {
        userService.sendRegistryEmail((String) subject.getPrincipal());
        return "邮件已发送，请及时查收";
    }

    //-------------------resources of private user------------------


    @GET
    @Path("{email}/algorithm/{id}")
    @RequiresPermissions("algorithm:get:self")
    public PersistantAlgorithm getPersistantAlgorithm(@PathParam("id") String id,
                                                      @PathParam("email") String email) {
        return algoRepository.findByAlgoIdAndCreatorEmail(id, email)
                .orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @Path("{email}/algorithm/pages")
    @GET
    @RequiresPermissions("algorithm:get:self")
    public PageData<PersistantAlgorithm> getPersistantAlgorithmPageByEmail(
            @PathParam("email") String email,
            @BeanParam PageInfo pageInfo) {
        User user = new User(email);
        PersistantAlgorithm algorithm = new PersistantAlgorithm();
        algorithm.setCreator(user);
        Example<PersistantAlgorithm> algorithmExample = Example.of(algorithm);

        return PageData.of(algoRepository.findAll(algorithmExample, pageInfo.getPageable()));
    }

    @POST
    @Path("{email}/algorithm/{id}")
    @RequiresPermissions("algorithm:create:self")
    public PersistantAlgorithm addPersistantAlgorithm(@BeanParam PersistantAlgorithm algorithm,
                                                      @FormDataParam("file") InputStream fileStream) {
        Algorithm a = algorithmService.createAlgorithm(fileStream, algorithm);
        return algoRepository.findById(a.getAlgoId()).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @PUT
    @Path("{email}/algorithm/{id}")
    @RequiresPermissions("algorithm:update:self")
    public PersistantAlgorithm updatePersistantAlgorithm(@BeanParam PersistantAlgorithm algorithm, @PathParam("email") String email) {
        //TODO 你不查一下这个数据是不是你的你怎么改？
        algorithm.getCreator().setEmail(email);
        return ResourceUtil.updateResource(algorithm, algorithm.getAlgoId(), algoRepository);
    }

    @Path("{email}/curve-data/pages")
    @RequiresPermissions("curve-data:get:self")
    public PageData<CurveData> getCurveDataPageByEmail(@PathParam("email") String email, @BeanParam PageInfo pageInfo) {
        User user = new User(email);
        CurveData data = new CurveData();
        data.setUser(user);
        Example<CurveData> dataExample = Example.of(data);

        return PageData.of(curveDataRepository.findAll(dataExample, pageInfo.getPageable()));
    }

    @GET
    @Path("{email}/curve-data/{id}")
    @RequiresPermissions("curve-data:get:self")
    public CurveData getCurveData(@PathParam("id") int curveDataId, @PathParam("email") String email) {
        return curveDataRepository.findByCurveDataIdAndUserEmail(curveDataId, email).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @GET
    @Path("{email}/report/{id}")
    @RequiresPermissions("report:get:self")
    public AnalyzedModelReport getReport(@PathParam("id") int reportId, @PathParam("email") String email) {
        return reportRepository.findByReportIdAndUserEmail(reportId, email).orElseThrow(new ResourceException(CommonErrorCode.E_7001));
    }

    @Path("{email}/report/pages")
    @GET
    @RequiresPermissions("report:get:self")
    public PageData<AnalyzedModelReport> getReportPageByEmail(@PathParam("email") String email, @BeanParam PageInfo pageInfo) {
        User user = new User(email);
        AnalyzedModelReport report = new AnalyzedModelReport();
        report.setUser(user);
        Example<AnalyzedModelReport> reportExample = Example.of(report);

        return PageData.of(reportRepository.findAll(reportExample, pageInfo.getPageable()));
    }

    //---------------------Getter & Setter------------------------

    public UserRepository getRepository() {
        return repository;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AlgorithmService getAlgorithmService() {
        return algorithmService;
    }

    public void setAlgorithmService(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    public AlgorithmRepository getAlgoRepository() {
        return algoRepository;
    }

    public void setAlgoRepository(AlgorithmRepository algoRepository) {
        this.algoRepository = algoRepository;
    }

    public CurveDataRepository getCurveDataRepository() {
        return curveDataRepository;
    }

    public void setCurveDataRepository(CurveDataRepository curveDataRepository) {
        this.curveDataRepository = curveDataRepository;
    }

    public AnalyzedModelReportRepository getReportRepository() {
        return reportRepository;
    }

    public void setReportRepository(AnalyzedModelReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
