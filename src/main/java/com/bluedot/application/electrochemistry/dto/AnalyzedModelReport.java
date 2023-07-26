package com.bluedot.application.electrochemistry.dto;

import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.domain.analysis.model.AnalyzedData;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.json.ResponseEntity;
import com.bluedot.infrastructure.repository.converter.AnalyzedDataConverter;
import com.bluedot.infrastructure.repository.converter.LinearEquationConverter;
import com.bluedot.infrastructure.utils.LinearEquation;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 20:34
 */
@Entity
@Table(name = "report")
public class AnalyzedModelReport implements ResponseEntity {
    @Id
    @Column(name = "report_id")
    @GeneratedValue
    private Integer report_id;

    private String title;

    @Column(name = "material_name")
    private String materialName;

    @OneToOne
    @JoinColumn(name = "preprocess_algo_id", referencedColumnName = "algo_id")
    private PersistantAlgorithm preprocessAlgorithm;

    @OneToOne
    @JoinColumn(name = "model_algo_id", referencedColumnName = "algo_id")
    private PersistantAlgorithm modelAlgorithm;

    @Convert(converter = LinearEquationConverter.class)
    private LinearEquation equation;

    @Column(name = "training_set_graph")
    private String trainingSetGraph;

    @Column(name = "test_set_graph")
    private String testSetGraph;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private User user;

    @Convert(converter = AnalyzedDataConverter.class)
    @Column(name = "training_data")
    private AnalyzedData trainingData;

    @Convert(converter = AnalyzedDataConverter.class)
    @Column(name = "test_data")
    private AnalyzedData testData;

    @Column(nullable = false)
    private BigDecimal rc2;

    @Column(nullable = false)
    private BigDecimal rmsec;

    @Column(nullable = false)
    private BigDecimal maec;

    @Column(nullable = false)
    private BigDecimal rp2;

    @Column(nullable = false)
    private BigDecimal rmsep;

    @Column(nullable = false)
    private BigDecimal maep;

    @Column(nullable = false)
    private BigDecimal rpd;

    @Column(name = "description")
    private String reportDesc;

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public PersistantAlgorithm getPreprocessAlgorithm() {
        return preprocessAlgorithm;
    }

    public void setPreprocessAlgorithm(PersistantAlgorithm preprocessAlgorithm) {
        this.preprocessAlgorithm = preprocessAlgorithm;
    }

    public PersistantAlgorithm getModelAlgorithm() {
        return modelAlgorithm;
    }

    public void setModelAlgorithm(PersistantAlgorithm modelAlgorithm) {
        this.modelAlgorithm = modelAlgorithm;
    }

    public LinearEquation getEquation() {
        return equation;
    }

    public void setEquation(LinearEquation equation) {
        this.equation = equation;
    }

    public String getTrainingSetGraph() {
        return trainingSetGraph;
    }

    public void setTrainingSetGraph(String trainingSetGraph) {
        this.trainingSetGraph = trainingSetGraph;
    }

    public String getTestSetGraph() {
        return testSetGraph;
    }

    public void setTestSetGraph(String testSetGraph) {
        this.testSetGraph = testSetGraph;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AnalyzedData getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(AnalyzedData trainingData) {
        this.trainingData = trainingData;
    }

    public AnalyzedData getTestData() {
        return testData;
    }

    public void setTestData(AnalyzedData testData) {
        this.testData = testData;
    }

    public BigDecimal getRc2() {
        return rc2;
    }

    public void setRc2(BigDecimal rc2) {
        this.rc2 = rc2;
    }

    public BigDecimal getRmsec() {
        return rmsec;
    }

    public void setRmsec(BigDecimal rmsec) {
        this.rmsec = rmsec;
    }

    public BigDecimal getMaec() {
        return maec;
    }

    public void setMaec(BigDecimal maec) {
        this.maec = maec;
    }

    public BigDecimal getRp2() {
        return rp2;
    }

    public void setRp2(BigDecimal rp2) {
        this.rp2 = rp2;
    }

    public BigDecimal getRmsep() {
        return rmsep;
    }

    public void setRmsep(BigDecimal rmsep) {
        this.rmsep = rmsep;
    }

    public BigDecimal getMaep() {
        return maep;
    }

    public void setMaep(BigDecimal maep) {
        this.maep = maep;
    }

    public BigDecimal getRpd() {
        return rpd;
    }

    public void setRpd(BigDecimal rpd) {
        this.rpd = rpd;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }
}
