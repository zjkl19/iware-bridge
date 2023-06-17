package com.iware.bridge.model.entity.global;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PartInfo", description="部位信息表")
public class PartInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "部位名称")
    private String name;

    @ApiModelProperty(value = "英文名")
    private String englishName;

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


    @Override
    public String toString() {
        return "PartInfo{" +
            "  id=" + id +
            ", name=" + name +
            ", englishName=" + englishName +
        "}";
    }
}
