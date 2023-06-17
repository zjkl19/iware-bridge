package com.iware.bridge.model.entity.online;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="SensorPrinciple", description="传感器原理表")
public class SensorPrinciple implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "原理名称")
    private String principle;

    @ApiModelProperty(value = "备注")
    private String remarks;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrinciple() {
		return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getRemarks() {
		return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return "SensorPrinciple{" +
            "  id=" + id +
            ", principle=" + principle +
            ", remarks=" + remarks +
        "}";
    }
}
