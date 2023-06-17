package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: CWY
 * @Date: 2021/8/2 16:48
 */
@ApiModel(value = "RoadScoreDTO", description = "线路评分")
public class RoadScoreDTO implements Serializable {

    @ApiModelProperty(value = "线路id")
    private Long id;

    @ApiModelProperty(value = "线路bci评分")
    private Double bridgeConditionIndex;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBridgeConditionIndex() {
        return bridgeConditionIndex;
    }

    public void setBridgeConditionIndex(Double bridgeConditionIndex) {
        this.bridgeConditionIndex = bridgeConditionIndex;
    }
}















