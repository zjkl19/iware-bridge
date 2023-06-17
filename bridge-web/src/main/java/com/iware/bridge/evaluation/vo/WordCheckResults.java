package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.evaluation.Attachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "WordCheckResults", description = "poi文档检查结果汇总")
public class WordCheckResults {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "要素名称")
    private String name;
    @ApiModelProperty(value = "病害名称")
    private String diseaseName;
    @ApiModelProperty(value = "跨")
    private String spanCode;
    @ApiModelProperty(value = "病害程度")
    private String degree;
    @ApiModelProperty(value = "流水号")
    private Integer sort;
    @ApiModelProperty(value = "病害缩写")
    private String code;

    @ApiModelProperty(value = "病害图片")
    private List<Attachment> pathName;

    @ApiModelProperty(value = "文件路径")
    private String path;
    @ApiModelProperty(value = "文件名称")
    private String photoName;
    @ApiModelProperty(value = "图示编号")
    private Integer number;
    @ApiModelProperty(value = "备注")
    private String remark;

    public List<Attachment> getPathName() {
        return pathName;
    }

    public void setPathName(List<Attachment> pathName) {
        this.pathName = pathName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

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

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getSpanCode() {
        return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WordCheckResults{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                ", spanCode=" + spanCode +
                ", degree='" + degree + '\'' +
                ", sort=" + sort +
                ", code='" + code + '\'' +
                ", path='" + path + '\'' +
                ", photoName='" + photoName + '\'' +
                '}';
    }
}
