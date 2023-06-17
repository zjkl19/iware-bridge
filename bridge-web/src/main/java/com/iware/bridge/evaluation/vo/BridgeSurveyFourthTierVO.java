package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.evaluation.BridgeSubcomponent;
import com.iware.bridge.model.entity.evaluation.BridgeSupcomponent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: HX
 * @date: 2021-6-28
 * @since 1.0
 */
@ApiModel(value = "BridgeSurveyFourthTierVO", description = "桥梁概况四级树菜单")
@Data
public class BridgeSurveyFourthTierVO implements Serializable {
    private static final long serialVersionUID = 5405117326664532745L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "构件ID")
    private Integer bridgeDeckSystemId;

    @ApiModelProperty(value = "组件ID")
    private Integer componentId;

//    @ApiModelProperty(value = "桥面系组件")
//    private BridgeDeckComponent bridgeDeckComponent;

    @ApiModelProperty(value = "桥梁上部构件实例")
    private BridgeSupcomponent bridgeSupcomponent;

    @ApiModelProperty(value = "桥梁下部构件实例")
    private BridgeSubcomponent bridgeSubcomponent;


}
