package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("维修类型分布")
public class MaintainTypeVo {

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("对应数量")
    private Integer count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
