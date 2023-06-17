package com.iware.bridge.model.entity.user;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="RolePowerRel", description="角色权限关联表")
public class RolePowerRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    private Integer powerId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
		return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPowerId() {
		return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }


    @Override
    public String toString() {
        return "RolePowerRel{" +
            "  id=" + id +
            ", roleId=" + roleId +
            ", powerId=" + powerId +
        "}";
    }
}
