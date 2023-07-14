package com.bluedot.application.electrochemistry.dto;

import com.bluedot.domain.process.model.Curve;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.repository.converter.QuantityConverter;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.infrastructure.utils.Quantity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:12
 */
@Entity
@Table(name = "curve_data")
public class CurveData {
    @Id
    @Column(name = "curve_data_id")
    @GeneratedValue
    private Integer curveDataId;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "material_type_id", referencedColumnName = "id",nullable = false)
    private MaterialType materialType;

    @Column(name = "material_name", nullable = false)
    private String materialName;

    @Column(name = "material_solubility", nullable = false)
    @Convert(converter = QuantityConverter.class)
    private Quantity materialSolubility;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "points", column = @Column(name = "original_points_data"))
    )
    private Curve originalPointsData;
    @Column(name = "original_ep", nullable = false)
    private BigDecimal originalEp;
    @Column(name = "original_ip", nullable = false)
    private BigDecimal originalIp;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "points", column = @Column(name = "newest_points_data"))
    )
    private Curve newestPointsData;
    @Column(name = "newest_ep")
    private BigDecimal newestEp;
    @Column(name = "newest_ip")
    private BigDecimal newestIp;

    @ManyToOne
    @JoinColumn(name = "buffer_solution_id", referencedColumnName = "id", nullable = false)
    private BufferSolution bufferSolution;

    @Column(nullable = false)
    private Double ph;

    private String description;

    /**
     * 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "modify_date")
    private Date modifyDate;

    public Integer getCurveDataId() {
        return curveDataId;
    }

    public void setCurveDataId(Integer curveDataId) {
        this.curveDataId = curveDataId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Quantity getMaterialSolubility() {
        return materialSolubility;
    }

    public void setMaterialSolubility(Quantity materialSolubility) {
        this.materialSolubility = materialSolubility;
    }

    public Curve getOriginalPointsData() {
        return originalPointsData;
    }

    public void setOriginalPointsData(Curve originalPointsData) {
        this.originalPointsData = originalPointsData;
    }

    public BigDecimal getOriginalEp() {
        return originalEp;
    }

    public void setOriginalEp(BigDecimal originalEp) {
        this.originalEp = originalEp;
    }

    public BigDecimal getOriginalIp() {
        return originalIp;
    }

    public void setOriginalIp(BigDecimal originalIp) {
        this.originalIp = originalIp;
    }

    public Curve getNewestPointsData() {
        return newestPointsData;
    }

    public void setNewestPointsData(Curve newestPointsData) {
        this.newestPointsData = newestPointsData;
    }

    public BigDecimal getNewestEp() {
        return newestEp;
    }

    public void setNewestEp(BigDecimal newestEp) {
        this.newestEp = newestEp;
    }

    public BigDecimal getNewestIp() {
        return newestIp;
    }

    public void setNewestIp(BigDecimal newestIp) {
        this.newestIp = newestIp;
    }

    public BufferSolution getBufferSolution() {
        return bufferSolution;
    }

    public void setBufferSolution(BufferSolution bufferSolution) {
        this.bufferSolution = bufferSolution;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "CurveData{" +
                "curveDataId='" + curveDataId + '\'' +
                ", user=" + user +
                ", materialType=" + materialType +
                ", materialName='" + materialName + '\'' +
                ", materialSolubility=" + materialSolubility +
                ", originalPointsData=" + originalPointsData +
                ", originalEp=" + originalEp +
                ", originalIp=" + originalIp +
                ", newestPointsData=" + newestPointsData +
                ", newestEp=" + newestEp +
                ", newestIp=" + newestIp +
                ", bufferSolution=" + bufferSolution +
                ", ph=" + ph +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
