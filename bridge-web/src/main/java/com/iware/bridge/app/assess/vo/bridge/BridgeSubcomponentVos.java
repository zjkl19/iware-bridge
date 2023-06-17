package com.iware.bridge.app.assess.vo.bridge;

import com.iware.bridge.model.entity.evaluation.BridgeSubcomponent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BridgeSubcomponentVos",description = "下部结构构件实例")
public class BridgeSubcomponentVos extends BridgeSubcomponent {
    @ApiModelProperty("有无桥台 1=有 0=没有")
    private Integer isAbutment;

    public Integer getIsAbutment() {
        return isAbutment;
    }

    public void setIsAbutment(Integer isAbutment) {
        this.isAbutment = isAbutment;
    }

    @Override
    public String toString() {
        return "BridgeSubcomponentVos{" +
                "id="+super.getId()+
                "isAbutment=" + isAbutment +
                '}';
    }
}
