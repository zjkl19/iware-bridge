package com.iware.bridge.model.entity.online;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Company", description="产商表")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "产商")
    private String company;

    @ApiModelProperty(value = "备注")
    private String remarks;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
		return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemarks() {
		return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return "Company{" +
            "  id=" + id +
            ", company=" + company +
            ", remarks=" + remarks +
        "}";
    }
}
