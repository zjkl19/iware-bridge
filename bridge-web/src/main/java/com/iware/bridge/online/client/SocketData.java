package com.iware.bridge.online.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "SocketData",description = "传感器websocket接入数据")
public class SocketData implements Serializable {

    private static final long serialVersionUID = -1177958121470511032L;

    @ApiModelProperty(value = "采集时间")
    private Long date;
    @ApiModelProperty(value = "")
    private Integer projId;
    @ApiModelProperty(value = "测点名称")
    private String mpName;
    @ApiModelProperty(value = "传感器类型名")
    private String sensorTypeName;
    @ApiModelProperty(value = "工程")
    private Project project;
    @ApiModelProperty(value = "单位")
    private String currentUnit;
    @ApiModelProperty(value = "")
    private String cumulativeChange;
    @ApiModelProperty(value = "设备id")
    private Integer deviceId;
    @ApiModelProperty(value = "传感器id")
    private Integer sensorId;
    @ApiModelProperty(value = "监测类型")
    private String monitorType;
    @ApiModelProperty(value = "")
    private Long collTime;
    @ApiModelProperty(value = "计算结果值")
    private Float value;
    @ApiModelProperty(value = "方向")
    private String direction;
    @ApiModelProperty(value = "对比上次的间隔时间")
    private Integer intervalTime;
    @ApiModelProperty(value = "")
    private Integer projCategory;
    @ApiModelProperty(value = "告警状态名称")
    private String alarmStatusName;
    @ApiModelProperty(value = "监测类型名称")
    private String monitorTypeName;
    @ApiModelProperty(value = "数据类型")
    private String dataType;
    @ApiModelProperty(value = "监测项目信息")
    private MonitorItem monitorItem;
    @ApiModelProperty(value = "")
    private Integer mpId;
    @ApiModelProperty(value = "告警状态")
    private String alarmStatus;
    @ApiModelProperty(value = "公司id")
    private String companyId;
    @ApiModelProperty(value = "对比上次更改值")
    private Float changeValue;
    @ApiModelProperty(value = "测点信息")
    private MonitorPoint monitorPoint;
    @ApiModelProperty(value = "原始数据1")
    private Float v1;
    @ApiModelProperty(value = "原始数据2")
    private Float v2;
    @ApiModelProperty(value = "原始数据3")
    private Float v3;
    @ApiModelProperty(value = "")
    private Float baseVal;
    @ApiModelProperty(value = "")
    private Float currentValue;
    @ApiModelProperty(value = "")
    private Float currentChange;
    @ApiModelProperty(value = "")
    private String status;


    class Project {
        private String name;
        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    class MonitorItem {
        private String name;
        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    class MonitorPoint {
        private String name;
        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getMpName() {
        return mpName;
    }

    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

    public String getSensorTypeName() {
        return sensorTypeName;
    }

    public void setSensorTypeName(String sensorTypeName) {
        this.sensorTypeName = sensorTypeName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(String currentUnit) {
        this.currentUnit = currentUnit;
    }

    public String getCumulativeChange() {
        return cumulativeChange;
    }

    public void setCumulativeChange(String cumulativeChange) {
        this.cumulativeChange = cumulativeChange;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public Long getCollTime() {
        return collTime;
    }

    public void setCollTime(Long collTime) {
        this.collTime = collTime;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getProjCategory() {
        return projCategory;
    }

    public void setProjCategory(Integer projCategory) {
        this.projCategory = projCategory;
    }

    public String getAlarmStatusName() {
        return alarmStatusName;
    }

    public void setAlarmStatusName(String alarmStatusName) {
        this.alarmStatusName = alarmStatusName;
    }

    public String getMonitorTypeName() {
        return monitorTypeName;
    }

    public void setMonitorTypeName(String monitorTypeName) {
        this.monitorTypeName = monitorTypeName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public MonitorItem getMonitorItem() {
        return monitorItem;
    }

    public void setMonitorItem(MonitorItem monitorItem) {
        this.monitorItem = monitorItem;
    }

    public Integer getMpId() {
        return mpId;
    }

    public void setMpId(Integer mpId) {
        this.mpId = mpId;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Float getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(Float changeValue) {
        this.changeValue = changeValue;
    }

    public MonitorPoint getMonitorPoint() {
        return monitorPoint;
    }

    public void setMonitorPoint(MonitorPoint monitorPoint) {
        this.monitorPoint = monitorPoint;
    }

    public Float getV1() {
        return v1;
    }

    public void setV1(Float v1) {
        this.v1 = v1;
    }

    public Float getV2() {
        return v2;
    }

    public void setV2(Float v2) {
        this.v2 = v2;
    }

    public Float getV3() {
        return v3;
    }

    public void setV3(Float v3) {
        this.v3 = v3;
    }

    public Float getBaseVal() {
        return baseVal;
    }

    public void setBaseVal(Float baseVal) {
        this.baseVal = baseVal;
    }

    public Float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Float currentValue) {
        this.currentValue = currentValue;
    }

    public Float getCurrentChange() {
        return currentChange;
    }

    public void setCurrentChange(Float currentChange) {
        this.currentChange = currentChange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
