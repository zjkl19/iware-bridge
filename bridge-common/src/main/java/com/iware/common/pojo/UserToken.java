package com.iware.common.pojo;

import com.iware.bridge.model.entity.user.Power;
import com.iware.bridge.model.entity.user.Role;
import com.iware.bridge.model.entity.user.User;

import java.io.Serializable;
import java.util.List;

public class UserToken implements Serializable {

    private static final long serialVersionUID = 4513319766782963238L;

    private User userInfo;
    private Role roleInfo;
    private List<Power> powerList;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public Role getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(Role roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<Power> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<Power> powerList) {
        this.powerList = powerList;
    }
}
