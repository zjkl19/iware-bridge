package com.iware.bridge.model.entity.inspection;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */

@ApiModel(value="BridgeTypeDiseaseRel", description="结构物类型巡检病害关联表")
public class BridgeTypeDiseaseRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "巡检病害表id")
    private Integer inspectionDiseaseId;

    @ApiModelProperty(value = "桥梁类型表id")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "顺序")
    private Integer seqNum;

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

    public Integer getBridgeTypeId() {
		return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Integer getSeqNum() {
		return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }


    @Override
    public String toString() {
        return "BridgeTypeDiseaseRel{" +
            "  id=" + id +
            ", inspectionDiseaseId=" + inspectionDiseaseId +
            ", bridgeTypeId=" + bridgeTypeId +
            ", seqNum=" + seqNum +
        "}";
    }
}
