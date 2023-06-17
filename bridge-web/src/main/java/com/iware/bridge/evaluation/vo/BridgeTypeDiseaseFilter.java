package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年8月13日09:45:26
 * @since 1.0
 */
@ApiModel(value = "BridgeTypeDiseaseFilter",description = "检测总览构件病害类型统计条件")
public class BridgeTypeDiseaseFilter {

    @ApiModelProperty(value = "桥梁类型Id")
    private Integer bridgeTypeId;
    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }
}
