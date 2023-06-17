package com.iware.bridge.model.entity.global;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Video", description="视频表")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "部位id")
    private Integer partId;

    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    @ApiModelProperty(value = "监控视角名称")
    private String monitorName;

    @ApiModelProperty(value = "设备类型 1:萤石 2:海康威视")
    private Integer type;

    @ApiModelProperty(value = "萤石云监控地址")
    private String ezopenUrl;

    @ApiModelProperty(value = "视频ip地址")
    private String ip;

    @ApiModelProperty(value = "视频端口号")
    private Integer port;

    @ApiModelProperty(value = "监控appkey")
    private String appKey;

    @ApiModelProperty(value = "监控密码")
    private String password;

    @ApiModelProperty(value = "监控视频码")
    private String videoCoding;

    @ApiModelProperty(value = "X坐标")
    private Float xAxis;

    @ApiModelProperty(value = "Y坐标")
    private Float yAxis;

    @ApiModelProperty(value = "Z坐标")
    private Float zAxis;

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

    public Integer getStructureId() {
		return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getPartId() {
		return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public Integer getComponentId() {
		return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public String getMonitorName() {
		return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public Integer getType() {
		return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEzopenUrl() {
		return ezopenUrl;
    }

    public void setEzopenUrl(String ezopenUrl) {
        this.ezopenUrl = ezopenUrl;
    }

    public String getIp() {
		return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
		return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAppKey() {
		return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPassword() {
		return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVideoCoding() {
		return videoCoding;
    }

    public void setVideoCoding(String videoCoding) {
        this.videoCoding = videoCoding;
    }

    public Float getXAxis() {
		return xAxis;
    }

    public void setXAxis(Float xAxis) {
        this.xAxis = xAxis;
    }

    public Float getYAxis() {
		return yAxis;
    }

    public void setYAxis(Float yAxis) {
        this.yAxis = yAxis;
    }

    public Float getZAxis() {
		return zAxis;
    }

    public void setZAxis(Float zAxis) {
        this.zAxis = zAxis;
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
        return "Video{" +
            "  id=" + id +
            ", structureId=" + structureId +
            ", partId=" + partId +
            ", componentId=" + componentId +
            ", monitorName=" + monitorName +
            ", type=" + type +
            ", ezopenUrl=" + ezopenUrl +
            ", ip=" + ip +
            ", port=" + port +
            ", appKey=" + appKey +
            ", password=" + password +
            ", videoCoding=" + videoCoding +
            ", xAxis=" + xAxis +
            ", yAxis=" + yAxis +
            ", zAxis=" + zAxis +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
