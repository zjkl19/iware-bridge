package com.iware.bridge.home.vo;

import com.iware.bridge.model.entity.global.Structure;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "StructureType",description = "首页结构物分类")
public class StructureType implements Serializable {

    private static final long serialVersionUID = -1164166100970511032L;

    @ApiModelProperty(value="结构物列表")
    private List<Structure> structureList;
    @ApiModelProperty(value="在线监测列表")
    private List<Integer> onlineList;
    @ApiModelProperty(value="日常巡查列表")
    private List<Integer> inspectionList;
    @ApiModelProperty(value="维修养护列表")
    private List<Integer> maintainList;
    @ApiModelProperty(value="检测评估列表")
    private List<Integer> evaluationList;

    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }

    public List<Integer> getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(List<Integer> onlineList) {
        this.onlineList = onlineList;
    }

    public List<Integer> getInspectionList() {
        return inspectionList;
    }

    public void setInspectionList(List<Integer> inspectionList) {
        this.inspectionList = inspectionList;
    }

    public List<Integer> getMaintainList() {
        return maintainList;
    }

    public void setMaintainList(List<Integer> maintainList) {
        this.maintainList = maintainList;
    }

    public List<Integer> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(List<Integer> evaluationList) {
        this.evaluationList = evaluationList;
    }
}
