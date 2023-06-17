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

@ApiModel(value="SensorWeight", description="称重传感器")
public class SensorWeight implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报文类型")
    private Integer messageType;

    @ApiModelProperty(value = "预留")
    private Integer reserved;

    @ApiModelProperty(value = "方向")
    private Integer direction;

    @ApiModelProperty(value = "网络通讯计算机编号")
    private Integer computerNumber;

    @ApiModelProperty(value = "命令码")
    private Integer commandCode;

    @ApiModelProperty(value = "数据类型")
    private Integer dataType;

    @ApiModelProperty(value = "图片编号")
    private Long imgNumber;

    @ApiModelProperty(value = "车道号")
    private Integer trackNumber;

    @ApiModelProperty(value = "检测时间")
    private Date samplingTime;

    @ApiModelProperty(value = "上下行")
    private Integer lastrow;

    @ApiModelProperty(value = "单轴数")
    private Integer singleShaftNuber;

    @ApiModelProperty(value = "轴组数")
    private Integer setsShaftNuber;

    @ApiModelProperty(value = "总重")
    private Integer totalWeight;

    @ApiModelProperty(value = "超限率")
    private Integer overrunRate;

    @ApiModelProperty(value = "车型")
    private Integer models;

    @ApiModelProperty(value = "左1轮重")
    private Integer leftWheelOne;

    @ApiModelProperty(value = "左2轮重")
    private Integer leftWheelTwo;

    @ApiModelProperty(value = "左3轮重")
    private Integer leftWheelThree;

    @ApiModelProperty(value = "左4轮重")
    private Integer leftWheelFour;

    @ApiModelProperty(value = "左5轮重")
    private Integer leftWheelFive;

    @ApiModelProperty(value = "左6轮重")
    private Integer leftWheelSix;

    @ApiModelProperty(value = "左7轮重")
    private Integer leftWheelSeven;

    @ApiModelProperty(value = "左8轮重")
    private Integer leftWheelEight;

    @ApiModelProperty(value = "右1轮重")
    private Integer rightWheelOne;

    @ApiModelProperty(value = "右2轮重")
    private Integer rightWheelTwo;

    @ApiModelProperty(value = "右3轮重")
    private Integer rightWheelThree;

    @ApiModelProperty(value = "右4轮重")
    private Integer rightWheelFour;

    @ApiModelProperty(value = "右5轮重")
    private Integer rightWheelFive;

    @ApiModelProperty(value = "右6轮重")
    private Integer rightWheelSix;

    @ApiModelProperty(value = "右7轮重")
    private Integer rightWheelSeven;

    @ApiModelProperty(value = "右8轮重")
    private Integer rightWheelEight;

    @ApiModelProperty(value = "轴1、2间距")
    private Integer spacingOneTwo;

    @ApiModelProperty(value = "轴2、3间距")
    private Integer spacingTwoThree;

    @ApiModelProperty(value = "轴3、4间距")
    private Integer spacingThreeFour;

    @ApiModelProperty(value = "轴4、5间距")
    private Integer spacingFourFive;

    @ApiModelProperty(value = "轴5、6间距")
    private Integer spacingFiveSix;

    @ApiModelProperty(value = "轴6、7间距")
    private Integer spacingSixSeven;

    @ApiModelProperty(value = "轴7、8间距")
    private Integer spacingSevenEight;

    @ApiModelProperty(value = "超限标示")
    private String transfiniteLabeled;

    @ApiModelProperty(value = "违例码")
    private Integer violationCode;

    @ApiModelProperty(value = "车速")
    private Integer carSpeed;

    @ApiModelProperty(value = "加速度")
    private Float acceleration;

    @ApiModelProperty(value = "当量轴次")
    private Float equivalentShaft;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;

    @ApiModelProperty(value = "传感器细项")
    private Integer sensorDetailsId;

    @ApiModelProperty(value = "车牌颜色")
    private String licenseColor;

    @ApiModelProperty(value = "桥梁编号")
    private String bridgeCode;

    @ApiModelProperty(value = "车长度")
    private Integer vehicleLength;

    public Integer getMessageType() {
		return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getReserved() {
		return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public Integer getDirection() {
		return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getComputerNumber() {
		return computerNumber;
    }

    public void setComputerNumber(Integer computerNumber) {
        this.computerNumber = computerNumber;
    }

    public Integer getCommandCode() {
		return commandCode;
    }

    public void setCommandCode(Integer commandCode) {
        this.commandCode = commandCode;
    }

    public Integer getDataType() {
		return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getImgNumber() {
		return imgNumber;
    }

    public void setImgNumber(Long imgNumber) {
        this.imgNumber = imgNumber;
    }

    public Integer getTrackNumber() {
		return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Date getSamplingTime() {
		return samplingTime;
    }

    public void setSamplingTime(Date samplingTime) {
        this.samplingTime = samplingTime;
    }

    public Integer getLastrow() {
		return lastrow;
    }

    public void setLastrow(Integer lastrow) {
        this.lastrow = lastrow;
    }

    public Integer getSingleShaftNuber() {
		return singleShaftNuber;
    }

    public void setSingleShaftNuber(Integer singleShaftNuber) {
        this.singleShaftNuber = singleShaftNuber;
    }

    public Integer getSetsShaftNuber() {
		return setsShaftNuber;
    }

    public void setSetsShaftNuber(Integer setsShaftNuber) {
        this.setsShaftNuber = setsShaftNuber;
    }

    public Integer getTotalWeight() {
		return totalWeight;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getOverrunRate() {
		return overrunRate;
    }

    public void setOverrunRate(Integer overrunRate) {
        this.overrunRate = overrunRate;
    }

    public Integer getModels() {
		return models;
    }

    public void setModels(Integer models) {
        this.models = models;
    }

    public Integer getLeftWheelOne() {
		return leftWheelOne;
    }

    public void setLeftWheelOne(Integer leftWheelOne) {
        this.leftWheelOne = leftWheelOne;
    }

    public Integer getLeftWheelTwo() {
		return leftWheelTwo;
    }

    public void setLeftWheelTwo(Integer leftWheelTwo) {
        this.leftWheelTwo = leftWheelTwo;
    }

    public Integer getLeftWheelThree() {
		return leftWheelThree;
    }

    public void setLeftWheelThree(Integer leftWheelThree) {
        this.leftWheelThree = leftWheelThree;
    }

    public Integer getLeftWheelFour() {
		return leftWheelFour;
    }

    public void setLeftWheelFour(Integer leftWheelFour) {
        this.leftWheelFour = leftWheelFour;
    }

    public Integer getLeftWheelFive() {
		return leftWheelFive;
    }

    public void setLeftWheelFive(Integer leftWheelFive) {
        this.leftWheelFive = leftWheelFive;
    }

    public Integer getLeftWheelSix() {
		return leftWheelSix;
    }

    public void setLeftWheelSix(Integer leftWheelSix) {
        this.leftWheelSix = leftWheelSix;
    }

    public Integer getLeftWheelSeven() {
		return leftWheelSeven;
    }

    public void setLeftWheelSeven(Integer leftWheelSeven) {
        this.leftWheelSeven = leftWheelSeven;
    }

    public Integer getLeftWheelEight() {
		return leftWheelEight;
    }

    public void setLeftWheelEight(Integer leftWheelEight) {
        this.leftWheelEight = leftWheelEight;
    }

    public Integer getRightWheelOne() {
		return rightWheelOne;
    }

    public void setRightWheelOne(Integer rightWheelOne) {
        this.rightWheelOne = rightWheelOne;
    }

    public Integer getRightWheelTwo() {
		return rightWheelTwo;
    }

    public void setRightWheelTwo(Integer rightWheelTwo) {
        this.rightWheelTwo = rightWheelTwo;
    }

    public Integer getRightWheelThree() {
		return rightWheelThree;
    }

    public void setRightWheelThree(Integer rightWheelThree) {
        this.rightWheelThree = rightWheelThree;
    }

    public Integer getRightWheelFour() {
		return rightWheelFour;
    }

    public void setRightWheelFour(Integer rightWheelFour) {
        this.rightWheelFour = rightWheelFour;
    }

    public Integer getRightWheelFive() {
		return rightWheelFive;
    }

    public void setRightWheelFive(Integer rightWheelFive) {
        this.rightWheelFive = rightWheelFive;
    }

    public Integer getRightWheelSix() {
		return rightWheelSix;
    }

    public void setRightWheelSix(Integer rightWheelSix) {
        this.rightWheelSix = rightWheelSix;
    }

    public Integer getRightWheelSeven() {
		return rightWheelSeven;
    }

    public void setRightWheelSeven(Integer rightWheelSeven) {
        this.rightWheelSeven = rightWheelSeven;
    }

    public Integer getRightWheelEight() {
		return rightWheelEight;
    }

    public void setRightWheelEight(Integer rightWheelEight) {
        this.rightWheelEight = rightWheelEight;
    }

    public Integer getSpacingOneTwo() {
		return spacingOneTwo;
    }

    public void setSpacingOneTwo(Integer spacingOneTwo) {
        this.spacingOneTwo = spacingOneTwo;
    }

    public Integer getSpacingTwoThree() {
		return spacingTwoThree;
    }

    public void setSpacingTwoThree(Integer spacingTwoThree) {
        this.spacingTwoThree = spacingTwoThree;
    }

    public Integer getSpacingThreeFour() {
		return spacingThreeFour;
    }

    public void setSpacingThreeFour(Integer spacingThreeFour) {
        this.spacingThreeFour = spacingThreeFour;
    }

    public Integer getSpacingFourFive() {
		return spacingFourFive;
    }

    public void setSpacingFourFive(Integer spacingFourFive) {
        this.spacingFourFive = spacingFourFive;
    }

    public Integer getSpacingFiveSix() {
		return spacingFiveSix;
    }

    public void setSpacingFiveSix(Integer spacingFiveSix) {
        this.spacingFiveSix = spacingFiveSix;
    }

    public Integer getSpacingSixSeven() {
		return spacingSixSeven;
    }

    public void setSpacingSixSeven(Integer spacingSixSeven) {
        this.spacingSixSeven = spacingSixSeven;
    }

    public Integer getSpacingSevenEight() {
		return spacingSevenEight;
    }

    public void setSpacingSevenEight(Integer spacingSevenEight) {
        this.spacingSevenEight = spacingSevenEight;
    }

    public String getTransfiniteLabeled() {
		return transfiniteLabeled;
    }

    public void setTransfiniteLabeled(String transfiniteLabeled) {
        this.transfiniteLabeled = transfiniteLabeled;
    }

    public Integer getViolationCode() {
		return violationCode;
    }

    public void setViolationCode(Integer violationCode) {
        this.violationCode = violationCode;
    }

    public Integer getCarSpeed() {
		return carSpeed;
    }

    public void setCarSpeed(Integer carSpeed) {
        this.carSpeed = carSpeed;
    }

    public Float getAcceleration() {
		return acceleration;
    }

    public void setAcceleration(Float acceleration) {
        this.acceleration = acceleration;
    }

    public Float getEquivalentShaft() {
		return equivalentShaft;
    }

    public void setEquivalentShaft(Float equivalentShaft) {
        this.equivalentShaft = equivalentShaft;
    }

    public String getLicensePlate() {
		return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getSensorCoding() {
		return sensorCoding;
    }

    public void setSensorCoding(String sensorCoding) {
        this.sensorCoding = sensorCoding;
    }

    public Integer getSensorDetailsId() {
		return sensorDetailsId;
    }

    public void setSensorDetailsId(Integer sensorDetailsId) {
        this.sensorDetailsId = sensorDetailsId;
    }

    public String getLicenseColor() {
		return licenseColor;
    }

    public void setLicenseColor(String licenseColor) {
        this.licenseColor = licenseColor;
    }

    public String getBridgeCode() {
		return bridgeCode;
    }

    public void setBridgeCode(String bridgeCode) {
        this.bridgeCode = bridgeCode;
    }

    public Integer getVehicleLength() {
		return vehicleLength;
    }

    public void setVehicleLength(Integer vehicleLength) {
        this.vehicleLength = vehicleLength;
    }


    @Override
    public String toString() {
        return "SensorWeight{" +
            "  messageType=" + messageType +
            ", reserved=" + reserved +
            ", direction=" + direction +
            ", computerNumber=" + computerNumber +
            ", commandCode=" + commandCode +
            ", dataType=" + dataType +
            ", imgNumber=" + imgNumber +
            ", trackNumber=" + trackNumber +
            ", samplingTime=" + samplingTime +
            ", lastrow=" + lastrow +
            ", singleShaftNuber=" + singleShaftNuber +
            ", setsShaftNuber=" + setsShaftNuber +
            ", totalWeight=" + totalWeight +
            ", overrunRate=" + overrunRate +
            ", models=" + models +
            ", leftWheelOne=" + leftWheelOne +
            ", leftWheelTwo=" + leftWheelTwo +
            ", leftWheelThree=" + leftWheelThree +
            ", leftWheelFour=" + leftWheelFour +
            ", leftWheelFive=" + leftWheelFive +
            ", leftWheelSix=" + leftWheelSix +
            ", leftWheelSeven=" + leftWheelSeven +
            ", leftWheelEight=" + leftWheelEight +
            ", rightWheelOne=" + rightWheelOne +
            ", rightWheelTwo=" + rightWheelTwo +
            ", rightWheelThree=" + rightWheelThree +
            ", rightWheelFour=" + rightWheelFour +
            ", rightWheelFive=" + rightWheelFive +
            ", rightWheelSix=" + rightWheelSix +
            ", rightWheelSeven=" + rightWheelSeven +
            ", rightWheelEight=" + rightWheelEight +
            ", spacingOneTwo=" + spacingOneTwo +
            ", spacingTwoThree=" + spacingTwoThree +
            ", spacingThreeFour=" + spacingThreeFour +
            ", spacingFourFive=" + spacingFourFive +
            ", spacingFiveSix=" + spacingFiveSix +
            ", spacingSixSeven=" + spacingSixSeven +
            ", spacingSevenEight=" + spacingSevenEight +
            ", transfiniteLabeled=" + transfiniteLabeled +
            ", violationCode=" + violationCode +
            ", carSpeed=" + carSpeed +
            ", acceleration=" + acceleration +
            ", equivalentShaft=" + equivalentShaft +
            ", licensePlate=" + licensePlate +
            ", sensorCoding=" + sensorCoding +
            ", sensorDetailsId=" + sensorDetailsId +
            ", licenseColor=" + licenseColor +
            ", bridgeCode=" + bridgeCode +
            ", vehicleLength=" + vehicleLength +
        "}";
    }
}
