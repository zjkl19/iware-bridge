package com.iware.bridge.inspection.vo;

import com.iware.bridge.model.entity.inspection.InspectionDisease;

import java.util.List;

public class InspectionDiseaseTableCellVo extends InspectionDisease {

    private String optionName;

    private List<InspectionDiseaseTableCellVo> list;

    public List<InspectionDiseaseTableCellVo> getList() {
        return list;
    }

    public void setList(List<InspectionDiseaseTableCellVo> list) {
        this.list = list;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

}
