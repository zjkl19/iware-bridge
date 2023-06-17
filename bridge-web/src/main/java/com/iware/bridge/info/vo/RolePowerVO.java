package com.iware.bridge.info.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "RolePowerVO",description = "角色权限权限显示")
public class RolePowerVO implements Serializable {

    private static final long serialVersionUID = 5777551933728487050L;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "默认权限ids")
    private List<Integer> powerList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<Integer> powerList) {
        this.powerList = powerList;
    }
}
