package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.ProjectAppoint;
import com.iware.bridge.model.entity.user.Power;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "ProjectAppointVO",description = "用户项目记录")
public class ProjectAppointVO extends ProjectAppoint implements Serializable {

    private static final long serialVersionUID = 1113456100970511000L;

    @ApiModelProperty(value = "指派单位名称")
    private String appointUnitName;
    @ApiModelProperty(value = "被派单位名称")
    private String receiveUnitName;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "业务名称列表")
    private String businessNames;
    @ApiModelProperty(value = "业务id列表")
    private List<Integer> business;
    @ApiModelProperty(value = "限制的业务列表")
    private List<Power> limitBusiness;

    public String getAppointUnitName() {
        return appointUnitName;
    }

    public void setAppointUnitName(String appointUnitName) {
        this.appointUnitName = appointUnitName;
    }

    public String getReceiveUnitName() {
        return receiveUnitName;
    }

    public void setReceiveUnitName(String receiveUnitName) {
        this.receiveUnitName = receiveUnitName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBusinessNames() {
        return businessNames;
    }

    public void setBusinessNames(String businessNames) {
        this.businessNames = businessNames;
    }

    public List<Integer> getBusiness() {
        return business;
    }

    public void setBusiness(List<Integer> business) {
        this.business = business;
    }

    public List<Power> getLimitBusiness() {
        return limitBusiness;
    }

    public void setLimitBusiness(List<Power> limitBusiness) {
        this.limitBusiness = limitBusiness;
    }
}
