package com.iware.bridge.model.entity.user;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */

@ApiModel(value="UserAppRoleRel", description="用户app角色关联表")
public class UserAppRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "app角色id")
    private Integer appRoleId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppRoleId() {
		return appRoleId;
    }

    public void setAppRoleId(Integer appRoleId) {
        this.appRoleId = appRoleId;
    }

    public Integer getUserId() {
		return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "UserAppRoleRel{" +
            "  id=" + id +
            ", appRoleId=" + appRoleId +
            ", userId=" + userId +
        "}";
    }
}
