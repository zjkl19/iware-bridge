package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.ElectronicArchives;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "ElectronicArchivesVO",description = "电子档案实体类")
public class ElectronicArchivesVO extends ElectronicArchives implements Serializable {

    private static final long serialVersionUID = -1164996100970511032L;

    @ApiModelProperty(value="用户名")
    private String username;
    @ApiModelProperty(value="类型名称")
    private String typeName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
