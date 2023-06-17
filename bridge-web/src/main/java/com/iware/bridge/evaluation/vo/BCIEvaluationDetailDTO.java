package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:28
 */
@ApiModel(value = "BCIEvaluationDetailDTO", description = "BCI评价详情")
public class BCIEvaluationDetailDTO implements Serializable {

    @ApiModelProperty(value = "桥梁类型")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "跨径数量")
    private Integer spanNumber;

    @ApiModelProperty(value = "BCI评分")
    private Double BCIScore;

    @ApiModelProperty(value = "BSI评分")
    private Double BSIScore;

    @ApiModelProperty(value = "线路评分")
    private Double score;

    @ApiModelProperty(value = "评定等级")
    private String ratingLevel;

    @ApiModelProperty(value = "线路Id")
    private Long roadId;

    @ApiModelProperty(value = "BCI评价结果")
    private BCIEvaluationResultDTO bciEvaluationResultDTO;

    @ApiModelProperty(value = "桥面系")
    private FloorSystemDTO floorSystemDTO;

    @ApiModelProperty(value = "上部结构")
    private UpperStructureDTO upperStructureDTO;

    @ApiModelProperty(value = "下部结构")
    private LowerStructureDTO lowerStructureDTO;

    @ApiModelProperty(value = "是否已经生成记录 true-是 false-否")
    private Boolean evaluateFlag;


    public Integer getSpanNumber() {
        return spanNumber;
    }

    public void setSpanNumber(Integer spanNumber) {
        this.spanNumber = spanNumber;
    }

    public Double getBCIScore() {
        return BCIScore;
    }

    public void setBCIScore(Double BCIScore) {
        this.BCIScore = BCIScore;
    }

    public BCIEvaluationResultDTO getBciEvaluationResultDTO() {
        return bciEvaluationResultDTO;
    }

    public void setBciEvaluationResultDTO(BCIEvaluationResultDTO bciEvaluationResultDTO) {
        this.bciEvaluationResultDTO = bciEvaluationResultDTO;
    }

    public FloorSystemDTO getFloorSystemDTO() {
        return floorSystemDTO;
    }

    public void setFloorSystemDTO(FloorSystemDTO floorSystemDTO) {
        this.floorSystemDTO = floorSystemDTO;
    }

    public UpperStructureDTO getUpperStructureDTO() {
        return upperStructureDTO;
    }

    public void setUpperStructureDTO(UpperStructureDTO upperStructureDTO) {
        this.upperStructureDTO = upperStructureDTO;
    }

    public LowerStructureDTO getLowerStructureDTO() {
        return lowerStructureDTO;
    }

    public void setLowerStructureDTO(LowerStructureDTO lowerStructureDTO) {
        this.lowerStructureDTO = lowerStructureDTO;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public Double getBSIScore() {
        return BSIScore;
    }

    public void setBSIScore(Double BSIScore) {
        this.BSIScore = BSIScore;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }

    public Boolean getEvaluateFlag() {
        return evaluateFlag;
    }

    public void setEvaluateFlag(Boolean evaluateFlag) {
        this.evaluateFlag = evaluateFlag;
    }
}















