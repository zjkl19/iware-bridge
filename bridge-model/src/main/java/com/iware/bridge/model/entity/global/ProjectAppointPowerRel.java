package com.iware.bridge.model.entity.global;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="ProjectAppointPowerRel", description="")
public class ProjectAppointPowerRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "项目指派记录id")
    private Integer projectAppointId;

    @ApiModelProperty(value = "业务权限id")
    private Integer powerId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectAppointId() {
		return projectAppointId;
    }

    public void setProjectAppointId(Integer projectAppointId) {
        this.projectAppointId = projectAppointId;
    }

    public Integer getPowerId() {
		return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }


    @Override
    public String toString() {
        return "ProjectAppointPowerRel{" +
            "  id=" + id +
            ", projectAppointId=" + projectAppointId +
            ", powerId=" + powerId +
        "}";
    }
}
