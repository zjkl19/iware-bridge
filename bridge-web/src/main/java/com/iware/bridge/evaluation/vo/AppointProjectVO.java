package com.iware.bridge.evaluation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: HX
 * @date: 2021-6-7
 * @since 1.0
 */
@ApiModel(value = "AppointProjectVO", description = "项目名称")
public class AppointProjectVO implements Serializable {
    private static final long serialVersionUID = -6034799823936389325L;

    @ApiModelProperty(value = "项目ID")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value = "项目指派开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projectStartTime;

    @ApiModelProperty(value = "项目指派结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projectEndTime;



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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(Date projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public Date getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(Date projectEndTime) {
        this.projectEndTime = projectEndTime;
    }
}
