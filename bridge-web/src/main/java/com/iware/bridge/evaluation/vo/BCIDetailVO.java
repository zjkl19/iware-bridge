package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: CWY
 * @Date: 2021/5/11 14:01
 */
@ApiModel(value = "BCIDetailVO", description = "BCI详情参数")
public class BCIDetailVO {

    @ApiModelProperty(value = "线路id")
    private Long roadId;

    @ApiModelProperty(value = "桥跨id")
    private Long spanId;

    @ApiModelProperty(value = "桥面系id")
    private Long deckId;

    @ApiModelProperty(value = "上部结构id")
    private Long upperStructureId;

    @ApiModelProperty(value = "下部结构id")
    private Long lowerStructureId;

    @ApiModelProperty(value = "状态：1：开始评价，2：详情 (开始评价需要计算评分并保存，详情不需要)")
    private Integer state;

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public Long getUpperStructureId() {
        return upperStructureId;
    }

    public void setUpperStructureId(Long upperStructureId) {
        this.upperStructureId = upperStructureId;
    }

    public Long getLowerStructureId() {
        return lowerStructureId;
    }

    public void setLowerStructureId(Long lowerStructureId) {
        this.lowerStructureId = lowerStructureId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
