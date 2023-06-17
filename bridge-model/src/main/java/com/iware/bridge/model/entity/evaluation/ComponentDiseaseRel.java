package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="ComponentDiseaseRel", description="构件病害关联表")
public class ComponentDiseaseRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥梁构件_id")
    private Integer componentId;

    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "部位类型")
    private Integer partType;

    @ApiModelProperty(value = "病害id")
    private Integer diseaseId;

    @ApiModelProperty(value = "属性组组别")
    private Integer groups;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComponentId() {
		return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getBridgeTypeId() {
		return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Integer getPartType() {
		return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
    }

    public Integer getDiseaseId() {
		return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Integer getGroups() {
		return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }


    @Override
    public String toString() {
        return "ComponentDiseaseRel{" +
            "  id=" + id +
            ", componentId=" + componentId +
            ", bridgeTypeId=" + bridgeTypeId +
            ", partType=" + partType +
            ", diseaseId=" + diseaseId +
            ", groups=" + groups +
        "}";
    }
}
