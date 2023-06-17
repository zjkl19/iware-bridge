package com.iware.bridge.model.entity.user;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Power", description="权限表")
public class Power implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "模块url")
    private String routerUrl;

    @ApiModelProperty(value = "父级模块id")
    private Integer parentId;

    @ApiModelProperty(value = "是否是行为权限(0=不是，1=是)")
    private Integer active;

    @ApiModelProperty(value = "顺序")
    private Integer priority;

    @ApiModelProperty(value = "是否指派业务")
    private Integer business;

    @ApiModelProperty(value = "行为类型")
    private String type;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
		return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
		return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRouterUrl() {
		return routerUrl;
    }

    public void setRouterUrl(String routerUrl) {
        this.routerUrl = routerUrl;
    }

    public Integer getParentId() {
		return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getActive() {
		return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getPriority() {
		return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getBusiness() {
		return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getType() {
		return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Power{" +
            "  id=" + id +
            ", name=" + name +
            ", remarks=" + remarks +
            ", routerUrl=" + routerUrl +
            ", parentId=" + parentId +
            ", active=" + active +
            ", priority=" + priority +
            ", business=" + business +
            ", type=" + type +
        "}";
    }
}
