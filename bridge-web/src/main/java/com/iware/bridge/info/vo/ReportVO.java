package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.Report;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="ReportVO", description="报告")
public class ReportVO extends Report implements Serializable {

    private static final long serialVersionUID = -1164912100970511032L;

    @ApiModelProperty(value = "上传人")
    private String username;
    @ApiModelProperty(value = "项目名")
    private String projectName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
