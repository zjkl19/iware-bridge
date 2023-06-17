package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年8月16日10:28:41
 * @since 1.0
 */
@ApiModel(value = "EvaluationAnalysisVO", description = "检测数据分析最新一次的数据")
public class EvaluationAnalysisVO {

    @ApiModelProperty(value = "结构物计划表id")
    private Integer id;
    @ApiModelProperty(value = "结构物id")
    private Integer structureInfoId;
    @ApiModelProperty(value = "项目id")
    private Integer projectInfoId;
    @ApiModelProperty(value = "时间")
    private String startTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStructureInfoId() {
        return structureInfoId;
    }

    public void setStructureInfoId(Integer structureInfoId) {
        this.structureInfoId = structureInfoId;
    }

    public Integer getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Integer projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
