package com.iware.bridge.model.entity.inspection;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="InspectionDiseaseInstance", description="巡检病害实例表")
public class InspectionDiseaseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "计划详情id")
    private Integer planDetailId;

    @ApiModelProperty(value = "巡检病害id")
    private Integer inspectionDiseaseId;

    @ApiModelProperty(value = "病害数量")
    private Integer quantity;

    @ApiModelProperty(value = "1:观察 2:报修 3:监测 4:即修 5:更换 6:增设 7.跟踪监测 8.维修处置 9.定期或专项检查")
    private Integer strategy;

    @ApiModelProperty(value = "异常部位")
    private String exceptionPart;

    @ApiModelProperty(value = "判定")
    private Integer exceptionType;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "0:未加入维养计划 1:待修 2:已修")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

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

    public Integer getInspectionDiseaseId() {
        return inspectionDiseaseId;
    }

    public void setInspectionDiseaseId(Integer inspectionDiseaseId) {
        this.inspectionDiseaseId = inspectionDiseaseId;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "InspectionDiseaseInstance{" +
                "  id=" + id +
                ", planDetailId=" + planDetailId +
                ", inspectionDiseaseId=" + inspectionDiseaseId +
                ", quantity=" + quantity +
                ", strategy=" + strategy +
                ", exceptionPart=" + exceptionPart +
                ", exceptionType=" + exceptionType +
                ", remarks=" + remarks +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
