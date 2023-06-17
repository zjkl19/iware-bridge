package com.iware.bridge.model.entity.user;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="AppRole", description="app角色表")
public class AppRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "英文名")
    private String englishName;

    @ApiModelProperty(value = "备注")
    private String remarks;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
		return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
		return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getRemarks() {
		return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return "AppRole{" +
            "  id=" + id +
            ", name=" + name +
            ", englishName=" + englishName +
            ", remarks=" + remarks +
        "}";
    }
}
