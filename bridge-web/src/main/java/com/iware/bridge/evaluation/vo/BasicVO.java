package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.TunnelInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: wjp
 * @date: 2021年8月17日10:19:25
 * @since 1.0
 */
@ApiModel(value = "BasicVO", description = "结构物检测基本信息和照片")
public class BasicVO extends Structure {

    @ApiModelProperty(value = "桥跨数量")
    private Integer spanNumber;
    @ApiModelProperty(value = "最大跨径")
    private Double maxSpanLength;
    @ApiModelProperty(value = "照片列表")
    private List<Photo> list;
    @ApiModelProperty(value = "桥梁详情")
    private BridgeInfo bridgeInfo;
    @ApiModelProperty(value = "隧道详情")
    private TunnelInfo tunnelInfo;
    @ApiModelProperty(value = "所属项目")
    private String projectName;
    @ApiModelProperty(value = "桥梁类型名称")
    private String bridgeTypeName;


    public String getBridgeTypeName() {
        return bridgeTypeName;
    }

    public void setBridgeTypeName(String bridgeTypeName) {
        this.bridgeTypeName = bridgeTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public TunnelInfo getTunnelInfo() {
        return tunnelInfo;
    }

    public void setTunnelInfo(TunnelInfo tunnelInfo) {
        this.tunnelInfo = tunnelInfo;
    }

    public BridgeInfo getBridgeInfo() {
        return bridgeInfo;
    }

    public void setBridgeInfo(BridgeInfo bridgeInfo) {
        this.bridgeInfo = bridgeInfo;
    }

    public Double getMaxSpanLength() {
        return maxSpanLength;
    }

    public void setMaxSpanLength(Double maxSpanLength) {
        this.maxSpanLength = maxSpanLength;
    }

    public Integer getSpanNumber() {
        return spanNumber;
    }

    public void setSpanNumber(Integer spanNumber) {
        this.spanNumber = spanNumber;
    }

    public List<Photo> getList() {
        return list;
    }

    public void setList(List<Photo> list) {
        this.list = list;
    }
}
