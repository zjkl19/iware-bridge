package com.iware.bridge.evaluation.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: wjp
 * @date: 2021年8月16日09:17:09
 * @since 1.0
 */
@ApiModel(value = "AssessRecordFilter", description = "结构物BCI记录过滤对象")
public class AssessRecordFilter {

    @ApiModelProperty(value="项目id")
    private Integer projectInfoId;
    @ApiModelProperty(value="结构物计划id")
    private Integer structureInfoId;
    @ApiModelProperty(value = "当前用户所有项目")
    @JsonIgnore
    private List<AppointProjectVO> list;

    public List<AppointProjectVO> getList() {
        return list;
    }

    public void setList(List<AppointProjectVO> list) {
        this.list = list;
    }

    public Integer getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Integer projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    public Integer getStructureInfoId() {
        return structureInfoId;
    }

    public void setStructureInfoId(Integer structureInfoId) {
        this.structureInfoId = structureInfoId;
    }
}
