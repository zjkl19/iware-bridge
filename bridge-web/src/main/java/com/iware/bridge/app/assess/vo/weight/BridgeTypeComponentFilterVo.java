package com.iware.bridge.app.assess.vo.weight;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="BridgeTypeComponentFilterVo",description = "桥梁类型构件权重过滤器")
public class BridgeTypeComponentFilterVo  implements Serializable {
    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;
    @ApiModelProperty(value = "桥梁部位")
    private Byte partType;
    @ApiModelProperty(value = "桥梁构件要素id")
    private List<Integer> componentIds;
    @ApiModelProperty(value = "是否是桥墩 0：桥墩 1：桥台")
    private Integer pierAbutment;

    public Integer getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Byte getPartType() {
        return partType;
    }

    public void setPartType(Byte partType) {
        this.partType = partType;
    }

    public List<Integer> getComponentIds() {
        return componentIds;
    }

    public void setComponentIds(List<Integer> componentIds) {
        this.componentIds = componentIds;
    }

    public Integer getPierAbutment() {
        return pierAbutment;
    }

    public void setPierAbutment(Integer pierAbutment) {
        this.pierAbutment = pierAbutment;
    }
}
