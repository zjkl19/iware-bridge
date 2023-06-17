package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PropertyOption", description="属性选项表")
public class PropertyOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "选项名称")
    private String optionName;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionName() {
		return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }


    @Override
    public String toString() {
        return "PropertyOption{" +
            "  id=" + id +
            ", optionName=" + optionName +
        "}";
    }
}
