package com.iware.bridge.info.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "PowerVO", description = "用户权限")
public class PowerVO implements Serializable {
    private static final long serialVersionUID = -4750668074972867390L;
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "子权限")
    private List<PowerVO> children;

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

    public List<PowerVO> getChildren() {
        return children;
    }

    public void setChildren(List<PowerVO> children) {
        this.children = children;
    }
}
