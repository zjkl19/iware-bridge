package com.iware.bridge.model.entity.evaluation;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="DiseaseInstance", description="桥梁检测病害实例")
public class DiseaseInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "1：桥面系构件 2：上部构件 3：下部构件")
    private Integer partType;

    @ApiModelProperty(value = "构件实例id")
    private Integer targetId;

    @ApiModelProperty(value = "病害id")
    private Integer diseaseId;

    @ApiModelProperty(value = "长度")
    private Double length;

    @ApiModelProperty(value = "宽度")
    private Double width;

    @ApiModelProperty(value = "深度")
    private Double depth;

    @ApiModelProperty(value = "缝长")
    private Double seamLength;

    @ApiModelProperty(value = "缝宽")
    private Double seamWidth;

    @ApiModelProperty(value = "角度")
    private Double angle;

    @ApiModelProperty(value = "程度")
    private String degree;

    @ApiModelProperty(value = "X坐标")
    private Double xAxis;

    @ApiModelProperty(value = "Y坐标")
    private Double yAxis;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "高度差")
    private Double heightDifference;

    @ApiModelProperty(value = "下挠值")
    private Double deflectionValue;

    @ApiModelProperty(value = "走向")
    private String trend;

    @ApiModelProperty(value = "数量")
    private Double number;

    @ApiModelProperty(value = "裂缝坐标")
    private String crackAxis;

    @ApiModelProperty(value = "扣分值")
    private Double deduct;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "是否删除操作")
    private Integer isDelete;

    @ApiModelProperty(value = "1：正常 0：回收")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartType() {
        return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getSeamLength() {
        return seamLength;
    }

    public void setSeamLength(Double seamLength) {
        this.seamLength = seamLength;
    }

    public Double getSeamWidth() {
        return seamWidth;
    }

    public void setSeamWidth(Double seamWidth) {
        this.seamWidth = seamWidth;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Double getXAxis() {
        return xAxis;
    }

    public void setXAxis(Double xAxis) {
        this.xAxis = xAxis;
    }

    public Double getYAxis() {
        return yAxis;
    }

    public void setYAxis(Double yAxis) {
        this.yAxis = yAxis;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getHeightDifference() {
        return heightDifference;
    }

    public void setHeightDifference(Double heightDifference) {
        this.heightDifference = heightDifference;
    }

    public Double getDeflectionValue() {
        return deflectionValue;
    }

    public void setDeflectionValue(Double deflectionValue) {
        this.deflectionValue = deflectionValue;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getCrackAxis() {
        return crackAxis;
    }

    public void setCrackAxis(String crackAxis) {
        this.crackAxis = crackAxis;
    }

    public Double getDeduct() {
        return deduct;
    }

    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer delete) {
        this.isDelete = delete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "DiseaseInstance{" +
                "  id=" + id +
                ", partType=" + partType +
                ", targetId=" + targetId +
                ", diseaseId=" + diseaseId +
                ", length=" + length +
                ", width=" + width +
                ", depth=" + depth +
                ", seamLength=" + seamLength +
                ", seamWidth=" + seamWidth +
                ", angle=" + angle +
                ", degree=" + degree +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", remark=" + remark +
                ", heightDifference=" + heightDifference +
                ", deflectionValue=" + deflectionValue +
                ", trend=" + trend +
                ", number=" + number +
                ", crackAxis=" + crackAxis +
                ", deduct=" + deduct +
                ", sort=" + sort +
                ", creator=" + creator +
                ", delete=" + isDelete +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
