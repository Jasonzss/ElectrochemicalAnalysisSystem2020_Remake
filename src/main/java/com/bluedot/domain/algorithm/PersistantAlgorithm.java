package com.bluedot.domain.algorithm;

import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.json.ResponseEntity;
import com.bluedot.infrastructure.json.adapter.ApproximateTimeTypeAdapter;
import com.bluedot.infrastructure.json.adapter.DateInChineseUnitTypeAdapter;
import com.bluedot.infrastructure.repository.enumeration.AlgorithmStatus;
import com.google.gson.annotations.JsonAdapter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jason
 * @creationDate 2023/06/22 - 17:19
 *
 * TODO JPA存在查询数据量过多的情况，比如查询算法类会把创建者对应的用户类一并查询出来，这中过度查询不可避免的会浪费一些数据库资源
 */
@Entity
@Table(name="algorithm")
public class PersistantAlgorithm implements ResponseEntity {
    /**
     * 算法的唯一标识，格式为：【java_12345678】
     */
    @Id
    @Column(name = "algo_id")
    @GeneratedValue
    private String algoId;

    /**
     * 算法的名称，是展示给用户看的名称，而不是类名或是文件名。
     */
    @Column(name = "algo_name", nullable = false)
    private String algoName;

    /**
     * 算法的创建者
     * 此处使用OneToOne注解表示两者一对一的强联系，但rbac系统作为领域外系统这样侵入好吗？或许可以直接使用一个用户id（值对象）作为替代？
     *
     * 【另外】第一版有个算法状态字段，用来表示算法在个人管理下处于公开、私有、审核中中的哪一状态。这同样是在算法领域之外的逻辑，
     * 还是说对算法领域的理解存在矛盾的地方？作为算法领域的核心同样是用户的个人资源
     *
     * 这里就暂时选择将两个业务放在一个类里吧
     */
    @OneToOne
    private User creator;

    /**
     * 算法的信息，由用户自己编写描述
     */
    private String info;

    /**
     * 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "create_date", nullable = false)
    @JsonAdapter(ApproximateTimeTypeAdapter.class)
    private Date createDate;

    /**
     * 修改日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "modify_date", nullable = false)
    @JsonAdapter(DateInChineseUnitTypeAdapter.class)
    private Date modifyDate;

    /**
     * 算法的管理状态，在数据库中使用数字代替存储
     */
    @Enumerated(EnumType.ORDINAL)
    private AlgorithmStatus status;

    /**
     * 算法应用场景类型
     */
    @Column(name = "algo_type", nullable = false)
    private String algoType;

    public PersistantAlgorithm() {

    }

    public PersistantAlgorithm(String algoId) {
        this.algoId = algoId;
    }

    public String getAlgoId() {
        return algoId;
    }

    public void setAlgoId(String algoId) {
        this.algoId = algoId;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public AlgorithmStatus getStatus() {
        return status;
    }

    public void setStatus(AlgorithmStatus status) {
        this.status = status;
    }

    public String getAlgoType() {
        return algoType;
    }

    public void setAlgoType(String algoType) {
        this.algoType = algoType;
    }

    @Override
    public String toString() {
        return "PersistantAlgorithm{" +
                "algoId='" + algoId + '\'' +
                ", algoName='" + algoName + '\'' +
                ", creator=" + creator +
                ", info='" + info + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", status=" + status +
                ", algoType='" + algoType + '\'' +
                '}';
    }
}
