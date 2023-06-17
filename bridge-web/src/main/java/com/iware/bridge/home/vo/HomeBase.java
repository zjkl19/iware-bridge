package com.iware.bridge.home.vo;

import com.iware.bridge.info.vo.EchartMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "HomeBase",description = "首页左侧数据")
public class HomeBase implements Serializable {

    private static final long serialVersionUID = -1164166100970511032L;

    @ApiModelProperty(value="项目数")
    private Integer projectNum;
    @ApiModelProperty(value="项目变化数")
    private Integer projectChangeNum;
    @ApiModelProperty(value="桥梁数")
    private Integer bridgeNum;
    @ApiModelProperty(value="桥梁变化数")
    private Integer bridgeChangeNum;
    @ApiModelProperty(value="隧道数")
    private Integer tunnelNum;
    @ApiModelProperty(value="隧道变化数")
    private Integer tunnelChangeNum;
    @ApiModelProperty(value="桥梁概况按分类")
    private List<EchartMap> sortByType;
    @ApiModelProperty(value="桥梁概况按桥型")
    private List<EchartMap> sortByBridgeType;
    @ApiModelProperty(value="桥梁概况按区域")
    private List<EchartMap> sortByArea;
    @ApiModelProperty(value="桥梁公路桥隧技术状况")
    private List<EchartMap> bridgeRoadTechnology;
    @ApiModelProperty(value="隧道公路桥隧技术状况")
    private List<EchartMap> tunnelRoadTechnology;
    @ApiModelProperty(value="桥梁城市桥隧技术状况")
    private List<EchartMap> bridgeCityTechnology;
    @ApiModelProperty(value="隧道城市桥隧技术状况")
    private List<EchartMap> tunnelCityTechnology;

    public Integer getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(Integer projectNum) {
        this.projectNum = projectNum;
    }

    public Integer getProjectChangeNum() {
        return projectChangeNum;
    }

    public void setProjectChangeNum(Integer projectChangeNum) {
        this.projectChangeNum = projectChangeNum;
    }

    public Integer getBridgeNum() {
        return bridgeNum;
    }

    public void setBridgeNum(Integer bridgeNum) {
        this.bridgeNum = bridgeNum;
    }

    public Integer getBridgeChangeNum() {
        return bridgeChangeNum;
    }

    public void setBridgeChangeNum(Integer bridgeChangeNum) {
        this.bridgeChangeNum = bridgeChangeNum;
    }

    public Integer getTunnelNum() {
        return tunnelNum;
    }

    public void setTunnelNum(Integer tunnelNum) {
        this.tunnelNum = tunnelNum;
    }

    public Integer getTunnelChangeNum() {
        return tunnelChangeNum;
    }

    public void setTunnelChangeNum(Integer tunnelChangeNum) {
        this.tunnelChangeNum = tunnelChangeNum;
    }

    public List<EchartMap> getSortByType() {
        return sortByType;
    }

    public void setSortByType(List<EchartMap> sortByType) {
        this.sortByType = sortByType;
    }

    public List<EchartMap> getSortByBridgeType() {
        return sortByBridgeType;
    }

    public void setSortByBridgeType(List<EchartMap> sortByBridgeType) {
        this.sortByBridgeType = sortByBridgeType;
    }

    public List<EchartMap> getSortByArea() {
        return sortByArea;
    }

    public void setSortByArea(List<EchartMap> sortByArea) {
        this.sortByArea = sortByArea;
    }


    public List<EchartMap> getBridgeRoadTechnology() {
        return bridgeRoadTechnology;
    }

    public void setBridgeRoadTechnology(List<EchartMap> bridgeRoadTechnology) {
        this.bridgeRoadTechnology = bridgeRoadTechnology;
    }

    public List<EchartMap> getTunnelRoadTechnology() {
        return tunnelRoadTechnology;
    }

    public void setTunnelRoadTechnology(List<EchartMap> tunnelRoadTechnology) {
        this.tunnelRoadTechnology = tunnelRoadTechnology;
    }

    public List<EchartMap> getBridgeCityTechnology() {
        return bridgeCityTechnology;
    }

    public void setBridgeCityTechnology(List<EchartMap> bridgeCityTechnology) {
        this.bridgeCityTechnology = bridgeCityTechnology;
    }

    public List<EchartMap> getTunnelCityTechnology() {
        return tunnelCityTechnology;
    }

    public void setTunnelCityTechnology(List<EchartMap> tunnelCityTechnology) {
        this.tunnelCityTechnology = tunnelCityTechnology;
    }
}
