package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.MeansPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="MeansPointVO", description="传感器信息")
public class MeansPointVO extends MeansPoint implements Serializable {

    private static final long serialVersionUID = 1177966100970511032L;

    @ApiModelProperty(value = "传感器类型")
    private String sensorTypeName;
    @ApiModelProperty(value = "结构物名称")
    private String structureName;
    @ApiModelProperty(value = "构件名称")
    private String componentName;
    @ApiModelProperty(value = "产商名")
    private String companyName;
    @ApiModelProperty(value = "部位名称")
    private String partName;
    @ApiModelProperty(value = "传感器单位")
    private String sensorUnit;
    @ApiModelProperty(value = "传感器细项信息")
    private List<SensorVO> sensorVOList;

    public String getSensorTypeName() {
        return sensorTypeName;
    }

    public void setSensorTypeName(String sensorTypeName) {
        this.sensorTypeName = sensorTypeName;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getSensorUnit() {
        return sensorUnit;
    }

    public void setSensorUnit(String sensorUnit) {
        this.sensorUnit = sensorUnit;
    }

    public List<SensorVO> getSensorVOList() {
        return sensorVOList;
    }

    public void setSensorVOList(List<SensorVO> sensorVOList) {
        this.sensorVOList = sensorVOList;
    }
}
