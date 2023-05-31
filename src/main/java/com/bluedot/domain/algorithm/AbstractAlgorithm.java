package com.bluedot.domain.algorithm;

import com.bluedot.domain.rbac.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 14:32
 * @Description ：
 */
@Entity
public abstract class AbstractAlgorithm implements Algorithm{
    /**
     * 算法的唯一标识，格式为：【java_12345678】
     */
    @Id
    private String algoId;

    /**
     * 算法的名称，是展示给用户看的名称，而不是类名或是文件名。
     */
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
//    @JoinColumn(name = "creatorEmail")
    private User creator;

    /**
     * 算法的信息，由用户自己编写描述
     */
    private String info;

    /**
     * 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    /**
     * 修改日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifyDate;

    /**
     * 算法的管理状态，在数据库中使用数字代替存储
     */
    @Enumerated(EnumType.ORDINAL)
    private AlgorithmStatus status;

    @Override
    public String getAlgoId() {
        return algoId;
    }

    /**
     * 得到算法使用的编程语言
     * @return 编程语言
     */
    protected abstract String getLanguage();

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
}
