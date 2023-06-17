package com.iware.bridge.inspection.vo;

public class ExcelDiseaseInstanceTableVo {

    private Integer id;

    private Integer planDetailId;

    private String inspectionDisease;

    private Integer quantity;

    private Integer strategy;

    private String remark;

    private Integer status;

    private String exceptionPart;

    private Integer exceptionType;

    private String options;

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(Integer planDetailId) {
        this.planDetailId = planDetailId;
    }

    public String getInspectionDisease() {
        return inspectionDisease;
    }

    public void setInspectionDisease(String inspectionDisease) {
        this.inspectionDisease = inspectionDisease;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExceptionPart() {
        return exceptionPart;
    }

    public void setExceptionPart(String exceptionPart) {
        this.exceptionPart = exceptionPart;
    }

    public Integer getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Integer exceptionType) {
        this.exceptionType = exceptionType;
    }
}
