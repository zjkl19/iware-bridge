package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.global.Project;
import com.iware.bridge.model.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="ProjectUserVO", description="项目下承接单位用户信息")
public class ProjectUserVO extends Project implements Serializable {

    private static final long serialVersionUID = 1220066100970511032L;

    @ApiModelProperty(value = "用户列表")
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
