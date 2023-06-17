package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: wjp
 * @date:
 * @since 1.0
 */
@ApiModel(value = "CorrelationAnalysisVO", description = "相关分析返回数据")
public class CorrelationAnalysisVO {

    @ApiModelProperty(value = "拟合线第一个点X轴")
    private Float middleFirstX;
    @ApiModelProperty(value = "拟合线第一个点Y轴")
    private Float middleFirstY;
    @ApiModelProperty(value = "拟合线第二个点X轴")
    private Float middleSecondX;
    @ApiModelProperty(value = "拟合线第二个点Y轴")
    private Float middleSecondY;
    @ApiModelProperty(value = "相关系数")
    private Float correlationCoefficient;
    @ApiModelProperty(value = "离差平方和")
    private Float dispersion;
    @ApiModelProperty(value = "回归方程")
    private String regressionEquation;
    @ApiModelProperty(value = "X轴所有点")
    private List<Float> allX;
    @ApiModelProperty(value = "Y轴所有点")
    private List<Float> allY;
    @ApiModelProperty(value = "拟合线正方向的δ线第一个点X轴")
    private Float upFirstX;
    @ApiModelProperty(value = "拟合线正方向的δ线第一个点Y轴")
    private Float upFirstY;
    @ApiModelProperty(value = "拟合线正方向的δ线第二个点X轴")
    private Float upSecondX;
    @ApiModelProperty(value = "拟合线正方向的δ线第二个点Y轴")
    private Float upSecondY;
    @ApiModelProperty(value = "拟合线负方向的δ线第一个点X轴")
    private Float downFirstX;
    @ApiModelProperty(value = "拟合线负方向的δ线第一个点Y轴")
    private Float downFirstY;
    @ApiModelProperty(value = "拟合线负方向的δ线第二个点X轴")
    private Float downSecondX;
    @ApiModelProperty(value = "拟合线负方向的δ线第二个点Y轴")
    private Float downSecondY;

    public Float getUpFirstX() {
        return upFirstX;
    }

    public void setUpFirstX(Float upFirstX) {
        this.upFirstX = upFirstX;
    }

    public Float getUpFirstY() {
        return upFirstY;
    }

    public void setUpFirstY(Float upFirstY) {
        this.upFirstY = upFirstY;
    }

    public Float getUpSecondX() {
        return upSecondX;
    }

    public void setUpSecondX(Float upSecondX) {
        this.upSecondX = upSecondX;
    }

    public Float getUpSecondY() {
        return upSecondY;
    }

    public void setUpSecondY(Float upSecondY) {
        this.upSecondY = upSecondY;
    }

    public Float getDownFirstX() {
        return downFirstX;
    }

    public void setDownFirstX(Float downFirstX) {
        this.downFirstX = downFirstX;
    }

    public Float getDownFirstY() {
        return downFirstY;
    }

    public void setDownFirstY(Float downFirstY) {
        this.downFirstY = downFirstY;
    }

    public Float getDownSecondX() {
        return downSecondX;
    }

    public void setDownSecondX(Float downSecondX) {
        this.downSecondX = downSecondX;
    }

    public Float getDownSecondY() {
        return downSecondY;
    }

    public void setDownSecondY(Float downSecondY) {
        this.downSecondY = downSecondY;
    }

    public List<Float> getAllX() {
        return allX;
    }

    public void setAllX(List<Float> allX) {
        this.allX = allX;
    }

    public List<Float> getAllY() {
        return allY;
    }

    public void setAllY(List<Float> allY) {
        this.allY = allY;
    }

    public Float getMiddleFirstX() {
        return middleFirstX;
    }

    public void setMiddleFirstX(Float middleFirstX) {
        this.middleFirstX = middleFirstX;
    }

    public Float getMiddleFirstY() {
        return middleFirstY;
    }

    public void setMiddleFirstY(Float middleFirstY) {
        this.middleFirstY = middleFirstY;
    }

    public Float getMiddleSecondX() {
        return middleSecondX;
    }

    public void setMiddleSecondX(Float middleSecondX) {
        this.middleSecondX = middleSecondX;
    }

    public Float getMiddleSecondY() {
        return middleSecondY;
    }

    public void setMiddleSecondY(Float middleSecondY) {
        this.middleSecondY = middleSecondY;
    }

    public Float getCorrelationCoefficient() {
        return correlationCoefficient;
    }

    public void setCorrelationCoefficient(Float correlationCoefficient) {
        this.correlationCoefficient = correlationCoefficient;
    }

    public Float getDispersion() {
        return dispersion;
    }

    public void setDispersion(Float dispersion) {
        this.dispersion = dispersion;
    }

    public String getRegressionEquation() {
        return regressionEquation;
    }

    public void setRegressionEquation(String regressionEquation) {
        this.regressionEquation = regressionEquation;
    }
}
