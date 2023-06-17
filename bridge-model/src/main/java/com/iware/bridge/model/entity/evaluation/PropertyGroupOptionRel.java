package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PropertyGroupOptionRel", description="属性组选项关联表")
public class PropertyGroupOptionRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "属性组id")
    private Integer propertyGroupId;

    @ApiModelProperty(value = "选项id")
    private Integer propertyOptionId;

    @ApiModelProperty(value = "构件病害关联表id")
    private Integer componentDiseaseRel;

    @ApiModelProperty(value = "顺序")
    private Integer seqNum;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyGroupId() {
		return propertyGroupId;
    }

    public void setPropertyGroupId(Integer propertyGroupId) {
        this.propertyGroupId = propertyGroupId;
    }

    public Integer getPropertyOptionId() {
		return propertyOptionId;
    }

    public void setPropertyOptionId(Integer propertyOptionId) {
        this.propertyOptionId = propertyOptionId;
    }

    public Integer getComponentDiseaseRel() {
		return componentDiseaseRel;
    }

    public void setComponentDiseaseRel(Integer componentDiseaseRel) {
        this.componentDiseaseRel = componentDiseaseRel;
    }

    public Integer getSeqNum() {
		return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }


    @Override
    public String toString() {
        return "PropertyGroupOptionRel{" +
            "  id=" + id +
            ", propertyGroupId=" + propertyGroupId +
            ", propertyOptionId=" + propertyOptionId +
            ", componentDiseaseRel=" + componentDiseaseRel +
            ", seqNum=" + seqNum +
        "}";
    }
}
