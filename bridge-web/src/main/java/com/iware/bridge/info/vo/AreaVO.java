package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.Area;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "AreaVO",description = "地区信息")
public class AreaVO extends Area implements Serializable {

    private static final long serialVersionUID = 1163456120970510032L;

    @ApiModelProperty(value = "子地区")
    private List<AreaVO> children;

    public List<AreaVO> getChildren() {
        return children;
    }

    public void setChildren(List<AreaVO> children) {
        this.children = children;
    }
}
