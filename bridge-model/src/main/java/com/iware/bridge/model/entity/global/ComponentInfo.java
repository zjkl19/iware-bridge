package com.iware.bridge.model.entity.global;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="ComponentInfo", description="巡查维养构件表")
public class ComponentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "构件名称")
    private String name;

    @ApiModelProperty(value = "英文名")
    private String englishName;

    @ApiModelProperty(value = "1:桥面系 2:上部结构 3:上部结构 4:人行地下通道")
    private Integer partId;

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

    public Integer getPartId() {
		return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }


    @Override
    public String toString() {
        return "ComponentInfo{" +
            "  id=" + id +
            ", name=" + name +
            ", englishName=" + englishName +
            ", partId=" + partId +
        "}";
    }
}
