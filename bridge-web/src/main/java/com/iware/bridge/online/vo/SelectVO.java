package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.global.ComponentInfo;
import com.iware.bridge.model.entity.online.Company;
import com.iware.bridge.model.entity.online.SensorDetails;
import com.iware.bridge.model.entity.online.SensorPrinciple;
import com.iware.bridge.model.entity.online.SensorType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "SelectVO",description = "传感器选择对象")
public class SelectVO implements Serializable {

    private static final long serialVersionUID = 1121966100970511032L;

    @ApiModelProperty(value = "产商")
    private List<Company> companyList;
    @ApiModelProperty(value = "传感器类型")
    private List<SensorType> sensorTypeList;
    @ApiModelProperty(value = "传感器细项")
    private List<SensorDetails> sensorDetailsList;
    @ApiModelProperty(value = "传感器原理")
    private List<SensorPrinciple> sensorPrincipleList;
    @ApiModelProperty(value = "构件")
    private List<ComponentInfo> componentInfoList;


    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<SensorType> getSensorTypeList() {
        return sensorTypeList;
    }

    public void setSensorTypeList(List<SensorType> sensorTypeList) {
        this.sensorTypeList = sensorTypeList;
    }

    public List<SensorDetails> getSensorDetailsList() {
        return sensorDetailsList;
    }

    public void setSensorDetailsList(List<SensorDetails> sensorDetailsList) {
        this.sensorDetailsList = sensorDetailsList;
    }

    public List<SensorPrinciple> getSensorPrincipleList() {
        return sensorPrincipleList;
    }

    public void setSensorPrincipleList(List<SensorPrinciple> sensorPrincipleList) {
        this.sensorPrincipleList = sensorPrincipleList;
    }

    public List<ComponentInfo> getComponentInfoList() {
        return componentInfoList;
    }

    public void setComponentInfoList(List<ComponentInfo> componentInfoList) {
        this.componentInfoList = componentInfoList;
    }
}
