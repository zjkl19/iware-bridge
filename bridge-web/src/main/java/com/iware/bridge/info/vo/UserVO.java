package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "UserVO",description = "用户属性类")
public class UserVO extends User implements Serializable {

    private static final long serialVersionUID = 3980371031117699917L;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "单位名称")
    private String unitName;
    @ApiModelProperty(value = "创建者")
    private String creator;
    @ApiModelProperty(value = "app权限列表")
    private String appRoleStr;
    @ApiModelProperty(value = "所勾选的用户权限ID集")
    private List<Integer> powerIds;
    @ApiModelProperty(value = "app权限列表")
    private List<Integer> appRoleList;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAppRoleStr() {
        return appRoleStr;
    }

    public void setAppRoleStr(String appRoleStr) {
        this.appRoleStr = appRoleStr;
    }

    public List<Integer> getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(List<Integer> powerIds) {
        this.powerIds = powerIds;
    }

    public List<Integer> getAppRoleList() {
        return appRoleList;
    }

    public void setAppRoleList(List<Integer> appRoleList) {
        this.appRoleList = appRoleList;
    }
}
