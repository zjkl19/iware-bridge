package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.SensorLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="SensorLogVO", description="维护日志实体类")
public class SensorLogVO extends SensorLog implements Serializable {

    private static final long serialVersionUID = 2271966100970511032L;

    @ApiModelProperty(value = "项目名")
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
