package com.iware.bridge.inspection.vo;

import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.inspection.PlanInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "PlanStructureVO",description = "计划结构物信息")
public class PlanStructureVO extends PlanInfo implements Serializable {

    private static final long serialVersionUID = -1264996100970511032L;

    @ApiModelProperty(value = "结构物列表")
    private List<Structure> structureList;

    public List<Structure> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<Structure> structureList) {
        this.structureList = structureList;
    }
}
