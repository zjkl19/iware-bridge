package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: HX
 * @date: 2021-7-28
 * @since 1.0
 */
@ApiModel(value = "ScoreSearchVO" , description = "用于查询桥隧评分时，获取最新时间的ID")
@Data
public class ScoreSearchVO implements Serializable {
    private static final long serialVersionUID = -9130738320252357726L;

    @ApiModelProperty(value = "桥梁评分的ID")
    private Integer id;

    @ApiModelProperty(value = "结构物ID")
    private Integer structureId;

    @ApiModelProperty(value = "评分时间")
    private Date evaluationTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }
}
