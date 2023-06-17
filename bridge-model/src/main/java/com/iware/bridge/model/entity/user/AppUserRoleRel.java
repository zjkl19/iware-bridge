package com.iware.bridge.model.entity.user;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="AppUserRoleRel", description="app用户角色关联表")
public class AppUserRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "app用户id")
    private Integer appUserId;

    @ApiModelProperty(value = "app角色id")
    private Integer appRoleId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppUserId() {
		return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getAppRoleId() {
		return appRoleId;
    }

    public void setAppRoleId(Integer appRoleId) {
        this.appRoleId = appRoleId;
    }


    @Override
    public String toString() {
        return "AppUserRoleRel{" +
            "  id=" + id +
            ", appUserId=" + appUserId +
            ", appRoleId=" + appRoleId +
        "}";
    }
}
