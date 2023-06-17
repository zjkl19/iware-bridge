package com.iware.bridge.model.entity.online;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="SensorRecord", description="传感器维护记录")
public class SensorRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "测点id")
    private Integer meansPointId;

    @ApiModelProperty(value = "操作日期")
    private Date operateTime;

    @ApiModelProperty(value = "操作类型")
    private String type;

    @ApiModelProperty(value = "操作原因")
    private String reason;

    @ApiModelProperty(value = "操作内容")
    private String content;

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

    public Integer getMeansPointId() {
        return meansPointId;
    }

    public void setMeansPointId(Integer meansPointId) {
        this.meansPointId = meansPointId;
    }

    public Date getOperateTime() {
		return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getType() {
		return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
		return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContent() {
		return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "SensorLog{" +
            "  id=" + id +
            ", meansPointId=" + meansPointId +
            ", operateTime=" + operateTime +
            ", type=" + type +
            ", reason=" + reason +
            ", content=" + content +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
