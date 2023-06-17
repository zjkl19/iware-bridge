package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Component", description="桥梁构件类型表")
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "构件名称")
    private String name;

    @ApiModelProperty(value = "1：首次添加需判断是否创建 0：首次添加无需判断")
    private Integer needJudge;

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

    public Integer getNeedJudge() {
		return needJudge;
    }

    public void setNeedJudge(Integer needJudge) {
        this.needJudge = needJudge;
    }


    @Override
    public String toString() {
        return "Component{" +
            "  id=" + id +
            ", name=" + name +
            ", needJudge=" + needJudge +
        "}";
    }
}
