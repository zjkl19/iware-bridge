package com.iware.bridge.evaluation.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: wjp
 * @date: 2021年8月16日09:19:02
 * @since 1.0
 */
@ApiModel(value = "AssessRecordVO", description = "检测列表")
public class AssessRecordVO {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "结构物id")
    private Long structureInfoId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "结构物")
    private String structureName;
    @ApiModelProperty(value = "检测完成时间")
    @JSONField(format="yyyy-MM-dd")
    private Date time;
    @ApiModelProperty(value = "评定等级")
    private String ratingLevel;
    @ApiModelProperty(value = "评分")
    private Double score;
    @ApiModelProperty(value = "是否生成原始记录 有值为有生成，无值为无生成")
    private Integer original;

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStructureInfoId() {
        return structureInfoId;
    }

    public void setStructureInfoId(Long structureInfoId) {
        this.structureInfoId = structureInfoId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }
}
