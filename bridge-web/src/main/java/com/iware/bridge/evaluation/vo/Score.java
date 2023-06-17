package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年7月30日17:43:11
 * @since 1.0
 */
public class Score {

    @ApiModelProperty(value = "桥面系/上部结构/下部结构")
    private String code;
    @ApiModelProperty(value = "构件实例id")
    private Integer id;
    @ApiModelProperty(value = "构件类型名称")
    private String creator;
    @ApiModelProperty(value = "桥跨名称")
    private String spanCode;
    @ApiModelProperty(value = "扣分值")
    private Double deduct;
    @ApiModelProperty(value = "是否带* 1:带 2不带")
    private Integer limit;
    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Double getDeduct() {
        return deduct;
    }

    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSpanCode() {
        return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
    }

    @Override
    public String toString() {
        return "Score{" +
                "code='" + code + '\'' +
                ", id=" + id +
                ", creator='" + creator + '\'' +
                ", spanCode='" + spanCode + '\'' +
                ", deduct=" + deduct +
                '}';
    }
}
