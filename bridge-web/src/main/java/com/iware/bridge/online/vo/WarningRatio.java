package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "WarningRatio",description = "预警统计信息")
public class WarningRatio implements Serializable {

    private static final long serialVersionUID = -1177966100970511032L;

    @ApiModelProperty(value="预警等级")
    private String warningLevel;
    @ApiModelProperty(value="预警状态")
    private String status;
    @ApiModelProperty(value="数量")
    private Integer count;

    public String getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(String warningLevel) {
        this.warningLevel = warningLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
