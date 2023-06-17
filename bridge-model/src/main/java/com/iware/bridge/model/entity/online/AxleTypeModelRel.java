package com.iware.bridge.model.entity.online;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="AxleTypeModelRel", description="车轴类型表车型关联表")
public class AxleTypeModelRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "车轴类型id")
    private Integer axleTypeId;

    @ApiModelProperty(value = "车型id")
    private Integer modelId;

    @ApiModelProperty(value = "车型名")
    private String name;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAxleTypeId() {
		return axleTypeId;
    }

    public void setAxleTypeId(Integer axleTypeId) {
        this.axleTypeId = axleTypeId;
    }

    public Integer getModelId() {
		return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AxleTypeModelRel{" +
            "  id=" + id +
            ", axleTypeId=" + axleTypeId +
            ", modelId=" + modelId +
            ", name=" + name +
        "}";
    }
}
