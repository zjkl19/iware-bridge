package com.iware.bridge.model.entity.inspection;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="DiseaseInstanceOptionRel", description="巡查病害实例选项关联表")
public class DiseaseInstanceOptionRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "病害实例表id")
    private Integer inspectionDiseaseInstanceId;

    @ApiModelProperty(value = "病害选项表id")
    private Integer inspectionDiseaseOptionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectionDiseaseInstanceId() {
        return inspectionDiseaseInstanceId;
    }

    public void setInspectionDiseaseInstanceId(Integer inspectionDiseaseInstanceId) {
        this.inspectionDiseaseInstanceId = inspectionDiseaseInstanceId;
    }

    public Integer getInspectionDiseaseOptionId() {
        return inspectionDiseaseOptionId;
    }

    public void setInspectionDiseaseOptionId(Integer inspectionDiseaseOptionId) {
        this.inspectionDiseaseOptionId = inspectionDiseaseOptionId;
    }


    @Override
    public String toString() {
        return "DiseaseInstanceOptionRel{" +
                "  id=" + id +
                ", inspectionDiseaseInstanceId=" + inspectionDiseaseInstanceId +
                ", inspectionDiseaseOptionId=" + inspectionDiseaseOptionId +
                "}";
    }
}
