package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.SensorWeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年10月22日10:50:37
 * @since 1.0
 */
@ApiModel(value="SensorWeightData", description="称重传感器数据")
public class SensorWeightData extends SensorWeight {
    @ApiModelProperty(value = "车轴类型id")
    private Integer axleTypeId;

    public Integer getAxleTypeId() {
        return axleTypeId;
    }

    public void setAxleTypeId(Integer axleTypeId) {
        this.axleTypeId = axleTypeId;
    }
}
