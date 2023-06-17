package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: HX
 * @date: 2021-6-30
 * @since 1.0
 */
@ApiModel(value = "BridgeSurveyDetailListFilter", description = "检测记录-桥梁概况查询列表过滤器")
@Data
public class BridgeSurveyDetailListFilter implements Serializable {
    private static final long serialVersionUID = 3953354569647795534L;

    @ApiModelProperty(value = "桥梁结构物id")
    private Integer bid;

    @ApiModelProperty(value = "计划结构物id")
    private Integer mpSrelId;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getMpSrelId() {
        return mpSrelId;
    }

    public void setMpSrelId(Integer mpSrelId) {
        this.mpSrelId = mpSrelId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
