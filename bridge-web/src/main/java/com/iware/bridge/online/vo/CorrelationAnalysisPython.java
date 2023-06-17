package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: wjp
 * @date:
 * @since 1.0
 */
@ApiModel(value = "CorrelationAnalysisPython", description = "相关分析传给python数据")
public class CorrelationAnalysisPython {
    @ApiModelProperty(value = "X坐标数据数组")
    private List<Float> one;
    @ApiModelProperty(value = "Y坐标数据数组")
    private List<Float> two;

    public List<Float> getOne() {
        return one;
    }

    public void setOne(List<Float> one) {
        this.one = one;
    }

    public List<Float> getTwo() {
        return two;
    }

    public void setTwo(List<Float> two) {
        this.two = two;
    }
}
