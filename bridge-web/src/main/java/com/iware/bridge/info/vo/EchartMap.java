package com.iware.bridge.info.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(value = "EchartMap",description = "<String,Integer>类型键值对")
public class EchartMap implements Serializable {

    private static final long serialVersionUID = -1164166100970511032L;

    @ApiModelProperty(value="名称")
    private String name;
    @ApiModelProperty(value="数量")
    private Integer count;
    @ApiModelProperty(value="状态")
    private String status;

    public EchartMap() {
    }

    public EchartMap(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public EchartMap(String name, Integer count, String status) {
        this.name = name;
        this.count = count;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
