package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.evaluation.BridgeDeckSystem;
import com.iware.bridge.model.entity.evaluation.BridgeOtherStructure;
import com.iware.bridge.model.entity.evaluation.BridgeSubstructure;
import com.iware.bridge.model.entity.evaluation.BridgeSupstructure;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: HX
 * @date: 2021-6-28
 * @since 1.0
 */
@ApiModel(value = "BridgeSurveyThirdTierVO", description = "桥梁概况三级级树菜单")
@Data
public class BridgeSurveyThirdTierVO implements Serializable {
    private static final long serialVersionUID = -4286774515734368029L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "bridgeSpanId")
    private Integer bridgeSpanId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "桥面系信息")
    private BridgeDeckSystem bridgeDeckSystem;

    @ApiModelProperty(value = "桥梁上部结构")
    private BridgeSupstructure bridgeSupstructure;

    @ApiModelProperty(value = "桥梁下部结构")
    private BridgeSubstructure bridgeSubstructure;

    @ApiModelProperty(value = "桥梁其他结构")
    private BridgeOtherStructure bridgeOtherStructure;

    @ApiModelProperty(value = "四级菜单")
    private List<BridgeSurveyFourthTierVO> children;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BridgeOtherStructure getBridgeOtherStructure() {
        return bridgeOtherStructure;
    }

    public void setBridgeOtherStructure(BridgeOtherStructure bridgeOtherStructure) {
        this.bridgeOtherStructure = bridgeOtherStructure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeSpanId() {
        return bridgeSpanId;
    }

    public void setBridgeSpanId(Integer bridgeSpanId) {
        this.bridgeSpanId = bridgeSpanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BridgeDeckSystem getBridgeDeckSystem() {
        return bridgeDeckSystem;
    }

    public void setBridgeDeckSystem(BridgeDeckSystem bridgeDeckSystem) {
        this.bridgeDeckSystem = bridgeDeckSystem;
    }

    public BridgeSupstructure getBridgeSupstructure() {
        return bridgeSupstructure;
    }

    public void setBridgeSupstructure(BridgeSupstructure bridgeSupstructure) {
        this.bridgeSupstructure = bridgeSupstructure;
    }

    public BridgeSubstructure getBridgeSubstructure() {
        return bridgeSubstructure;
    }

    public void setBridgeSubstructure(BridgeSubstructure bridgeSubstructure) {
        this.bridgeSubstructure = bridgeSubstructure;
    }

    public List<BridgeSurveyFourthTierVO> getChildren() {
        return children;
    }

    public void setChildren(List<BridgeSurveyFourthTierVO> children) {
        this.children = children;
    }
}
