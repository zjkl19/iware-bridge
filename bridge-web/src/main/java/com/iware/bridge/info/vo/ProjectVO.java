package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "ProjectVO",description = "项目信息")
public class ProjectVO extends Project implements Serializable {

    private static final long serialVersionUID = -1224126100970511032L;

    @ApiModelProperty(value="业主单位")
    private String unitName;
    @ApiModelProperty(value="结构物id列表")
    private List<Integer> structureIds;
    @ApiModelProperty(value="结构物id列表")
    private String idStr;
    @ApiModelProperty(value="结构物名称")
    private String structureNames;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<Integer> getStructureIds() {
        return structureIds;
    }

    public void setStructureIds(List<Integer> structureIds) {
        this.structureIds = structureIds;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getStructureNames() {
        return structureNames;
    }

    public void setStructureNames(String structureNames) {
        this.structureNames = structureNames;
    }
}
