package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年9月23日13:38:53
 * @since 1.0
 */
@ApiModel(value = "BridgeSituationFilter", description = "检测记录-桥梁概况详细数据查询条件")
public class BridgeSituationFilter {

    @ApiModelProperty(value = "类型 1：线路 2:桥跨 3：桥面系 4:上部结构 5：下部结构 6：上部结构构件 7：下部结构构件")
    private Integer type;
    @ApiModelProperty(value = "类型对应的主键id")
    private Integer id;
    @ApiModelProperty(value = "结构物计划id")
    private Integer moniPlanStructId;

    public Integer getMoniPlanStructId() {
        return moniPlanStructId;
    }

    public void setMoniPlanStructId(Integer moniPlanStructId) {
        this.moniPlanStructId = moniPlanStructId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
