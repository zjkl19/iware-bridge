package com.iware.bridge.model.entity.user;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="User", description="用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String petName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "所属单位id")
    private Integer unitId;

    @ApiModelProperty(value = "创建者")
    private Integer createUserId;

    @ApiModelProperty(value = "设备编号")
    private String deviceId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
		return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
		return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
		return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
		return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
		return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPetName() {
		return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getEmail() {
		return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUnitId() {
		return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getCreateUserId() {
		return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getDeviceId() {
		return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
		return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
		return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "User{" +
            "  id=" + id +
            ", username=" + username +
            ", password=" + password +
            ", gender=" + gender +
            ", phone=" + phone +
            ", realName=" + realName +
            ", petName=" + petName +
            ", email=" + email +
            ", unitId=" + unitId +
            ", createUserId=" + createUserId +
            ", deviceId=" + deviceId +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
