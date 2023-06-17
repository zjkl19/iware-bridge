package com.iware.bridge.model.entity.inspection;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="InspectionDiseaseOption", description="巡检病害选项表")
public class InspectionDiseaseOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "巡检病害id")
    private Integer inspectionDiseaseId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序,下标从1开始")
    private Integer seqNum;

    @ApiModelProperty(value = "0:病害 1:完好")
    private Integer optionStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectionDiseaseId() {
        return inspectionDiseaseId;
    }

    public void setInspectionDiseaseId(Integer inspectionDiseaseId) {
        this.inspectionDiseaseId = inspectionDiseaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public Integer getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(Integer optionStatus) {
        this.optionStatus = optionStatus;
    }


    @Override
    public String toString() {
        return "InspectionDiseaseOption{" +
                "  id=" + id +
                ", inspectionDiseaseId=" + inspectionDiseaseId +
                ", name=" + name +
                ", seqNum=" + seqNum +
                ", optionStatus=" + optionStatus +
                "}";
    }
}
