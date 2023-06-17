package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "MonitorStructureRelVO",description = "计划结构物关联")
public class MonitorStructureRelVO extends MonitorPlanStructureRel implements Serializable {

    private static final long serialVersionUID = -1164221111970511032L;

    @ApiModelProperty(value = "长度大于0表示这个结构物计划有文件上传上来")
    private List<Object> fileList;
    @ApiModelProperty(value = "文件名")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Object> getFileList() {
        return fileList;
    }

    public void setFileList(List<Object> fileList) {
        this.fileList = fileList;
    }


}
