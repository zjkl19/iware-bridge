package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RecordVO", description = "查看记录信息")
public class RecordVO {

    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "病害类型")
    private String name;
    @ApiModelProperty(value = "程度")
    private String degree;
    @ApiModelProperty(value = "病害编号")
    private String code;
    @ApiModelProperty(value = "修改时间")
    private String createTime;
    @ApiModelProperty(value = "修改人员")
    private String creator;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
    @ApiModelProperty(value = "当前页码")
    private Integer currentPage;
    @ApiModelProperty(value = "构件类型id")
    private Integer partType;
    @ApiModelProperty(value = "构件实例化id")
    private Integer targetId;
    @ApiModelProperty(value = "病害id")
    private Integer diseaseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
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
}
