package com.iware.bridge.online.vo;

import java.util.Date;

public class SensorWeightVO {

	private String name;
	private Double weight;
	private Date samplingTime;
	private Float actual;


	private Integer messageType; // 报文类型 messageType String
	private Integer reserved; // 预留 reserved String
	private Integer direction; // 方向 direction String
	private Integer computerNumber; // 网络通讯计算机编号 computerNumber String
	private Integer commandCode; // 命令码 commandCode String
	private Integer dataType; // 数据类型 dataType String
	private Long imgNumber; // 图片编号 imgNumber String
	private Integer trackNumber; // 车道号 trackNumber String
	private String testTime; // 检测时间 testTime String
	private Integer lastrow; // 上下行 lastrow String
	private Integer singleShaftNuber; // 单轴数 singleShaftNuber String
	private Integer setsShaftNuber; // 轴组数 setsShaftNuber String
	private Integer totalWeight; // 总重 totalWeight Intrger
	private Integer overrunRate; // 超限率 overrunRate Intrger
	private Integer models; // 车型 models String
	private Integer left1; // 左1轮重 left1 Intrger
	private Integer left2; // 左2轮重 left2 Intrger
	private Integer left3; // 左3轮重 left3 Intrger
	private Integer left4; // 左4轮重 left4 Intrger
	private Integer left5; // 左5轮重 left5 Intrger
	private Integer left6; // 左6轮重 left6 Intrger
	private Integer left7; // 左7轮重 left7 Intrger
	private Integer left8; // 左8轮重 left8 Intrger
	private Integer right1; // 右1轮重 right1 Intrger
	private Integer right2; // 右2轮重 right2 Intrger
	private Integer right3; // 右3轮重 right3 Intrger
	private Integer right4; // 右4轮重 right4 Intrger
	private Integer right5; // 右5轮重 right5 Intrger
	private Integer right6; // 右6轮重 right6 Intrger
	private Integer right7; // 右7轮重 right7 Intrger
	private Integer right8; // 右8轮重 right8 Intrger
	private Integer spacing12; // 轴1、2间距 spacing12 Intrger
	private Integer spacing23; // 轴2、3间距 spacing23 Intrger
	private Integer spacing34; // 轴3、4间距 spacing34 Intrger
	private Integer spacing45; // 轴4、5间距 spacing45 Intrger
	private Integer spacing56; // 轴5、6间距 spacing56 Intrger
	private Integer spacing67; // 轴6、7间距 spacing67 Intrger
	private Integer spacing78; // 轴7、8间距 spacing78 Intrger
	private Integer violationCode; // 违例码 violationCode Intrger
	private String transfiniteLabeled; // 超限标示 transfiniteLabeled String
	private Integer carSpeed; // 车速 catSpeed Intrger
	private Float acceleration; // 加速度 acceleration Float
	private Float equivalentShaft; // 当量轴次 equivalentShaft Float
	private String licensePlate; // 车牌 licensePlate String
	private String lincenseColor; // 车牌颜色 lincenseColor String
	private Integer bridgeCode; // 桥梁编码 bridgeCode Integer
	private Integer vehicleLength; // 车辆长度 vehicleLength Integer

	private String sensorCoding; //传感器编码
	private Integer id; //传感器细项id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Date getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(Date samplingTime) {
		this.samplingTime = samplingTime;
	}

	public Float getActual() {
		return actual;
	}
	public void setActual(Float actual) {
		this.actual = actual;
	}
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
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
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
	public Integer getLeft1() {
		return left1;
	}
	public void setLeft1(Integer left1) {
		this.left1 = left1;
	}
	public Integer getLeft2() {
		return left2;
	}
	public void setLeft2(Integer left2) {
		this.left2 = left2;
	}
	public Integer getLeft3() {
		return left3;
	}
	public void setLeft3(Integer left3) {
		this.left3 = left3;
	}
	public Integer getLeft4() {
		return left4;
	}
	public void setLeft4(Integer left4) {
		this.left4 = left4;
	}
	public Integer getLeft5() {
		return left5;
	}
	public void setLeft5(Integer left5) {
		this.left5 = left5;
	}
	public Integer getLeft6() {
		return left6;
	}
	public void setLeft6(Integer left6) {
		this.left6 = left6;
	}
	public Integer getLeft7() {
		return left7;
	}
	public void setLeft7(Integer left7) {
		this.left7 = left7;
	}
	public Integer getLeft8() {
		return left8;
	}
	public void setLeft8(Integer left8) {
		this.left8 = left8;
	}
	public Integer getRight1() {
		return right1;
	}
	public void setRight1(Integer right1) {
		this.right1 = right1;
	}
	public Integer getRight2() {
		return right2;
	}
	public void setRight2(Integer right2) {
		this.right2 = right2;
	}
	public Integer getRight3() {
		return right3;
	}
	public void setRight3(Integer right3) {
		this.right3 = right3;
	}
	public Integer getRight4() {
		return right4;
	}
	public void setRight4(Integer right4) {
		this.right4 = right4;
	}
	public Integer getRight5() {
		return right5;
	}
	public void setRight5(Integer right5) {
		this.right5 = right5;
	}
	public Integer getRight6() {
		return right6;
	}
	public void setRight6(Integer right6) {
		this.right6 = right6;
	}
	public Integer getRight7() {
		return right7;
	}
	public void setRight7(Integer right7) {
		this.right7 = right7;
	}
	public Integer getRight8() {
		return right8;
	}
	public void setRight8(Integer right8) {
		this.right8 = right8;
	}
	public Integer getSpacing12() {
		return spacing12;
	}
	public void setSpacing12(Integer spacing12) {
		this.spacing12 = spacing12;
	}
	public Integer getSpacing23() {
		return spacing23;
	}
	public void setSpacing23(Integer spacing23) {
		this.spacing23 = spacing23;
	}
	public Integer getSpacing34() {
		return spacing34;
	}
	public void setSpacing34(Integer spacing34) {
		this.spacing34 = spacing34;
	}
	public Integer getSpacing45() {
		return spacing45;
	}
	public void setSpacing45(Integer spacing45) {
		this.spacing45 = spacing45;
	}
	public Integer getSpacing56() {
		return spacing56;
	}
	public void setSpacing56(Integer spacing56) {
		this.spacing56 = spacing56;
	}
	public Integer getSpacing67() {
		return spacing67;
	}
	public void setSpacing67(Integer spacing67) {
		this.spacing67 = spacing67;
	}
	public Integer getSpacing78() {
		return spacing78;
	}
	public void setSpacing78(Integer spacing78) {
		this.spacing78 = spacing78;
	}
	public Integer getViolationCode() {
		return violationCode;
	}
	public void setViolationCode(Integer violationCode) {
		this.violationCode = violationCode;
	}
	public String getTransfiniteLabeled() {
		return transfiniteLabeled;
	}
	public void setTransfiniteLabeled(String transfiniteLabeled) {
		this.transfiniteLabeled = transfiniteLabeled;
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
	public String getLincenseColor() {
		return lincenseColor;
	}
	public void setLincenseColor(String lincenseColor) {
		this.lincenseColor = lincenseColor;
	}
	public Integer getBridgeCode() {
		return bridgeCode;
	}
	public void setBridgeCode(Integer bridgeCode) {
		this.bridgeCode = bridgeCode;
	}
	public Integer getVehicleLength() {
		return vehicleLength;
	}
	public void setVehicleLength(Integer vehicleLength) {
		this.vehicleLength = vehicleLength;
	}
	public String getSensorCoding() {
		return sensorCoding;
	}
	public void setSensorCoding(String sensorCoding) {
		this.sensorCoding = sensorCoding;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SensorWeightVO(String name, Double weight, Date samplingTime, Float actual, Integer messageType,
                          Integer reserved, Integer direction, Integer computerNumber, Integer commandCode, Integer dataType,
                          Long imgNumber, Integer trackNumber, String testTime, Integer lastrow, Integer singleShaftNuber,
                          Integer setsShaftNuber, Integer totalWeight, Integer overrunRate, Integer models, Integer left1,
                          Integer left2, Integer left3, Integer left4, Integer left5, Integer left6, Integer left7, Integer left8,
                          Integer right1, Integer right2, Integer right3, Integer right4, Integer right5, Integer right6,
                          Integer right7, Integer right8, Integer spacing12, Integer spacing23, Integer spacing34, Integer spacing45,
                          Integer spacing56, Integer spacing67, Integer spacing78, Integer violationCode, String transfiniteLabeled,
                          Integer carSpeed, Float acceleration, Float equivalentShaft, String licensePlate, String lincenseColor,
                          Integer bridgeCode, Integer vehicleLength, String sensorCoding, Integer id) {
		super();
		this.name = name;
		this.weight = weight;
		this.samplingTime = samplingTime;
		this.actual = actual;
		this.messageType = messageType;
		this.reserved = reserved;
		this.direction = direction;
		this.computerNumber = computerNumber;
		this.commandCode = commandCode;
		this.dataType = dataType;
		this.imgNumber = imgNumber;
		this.trackNumber = trackNumber;
		this.testTime = testTime;
		this.lastrow = lastrow;
		this.singleShaftNuber = singleShaftNuber;
		this.setsShaftNuber = setsShaftNuber;
		this.totalWeight = totalWeight;
		this.overrunRate = overrunRate;
		this.models = models;
		this.left1 = left1;
		this.left2 = left2;
		this.left3 = left3;
		this.left4 = left4;
		this.left5 = left5;
		this.left6 = left6;
		this.left7 = left7;
		this.left8 = left8;
		this.right1 = right1;
		this.right2 = right2;
		this.right3 = right3;
		this.right4 = right4;
		this.right5 = right5;
		this.right6 = right6;
		this.right7 = right7;
		this.right8 = right8;
		this.spacing12 = spacing12;
		this.spacing23 = spacing23;
		this.spacing34 = spacing34;
		this.spacing45 = spacing45;
		this.spacing56 = spacing56;
		this.spacing67 = spacing67;
		this.spacing78 = spacing78;
		this.violationCode = violationCode;
		this.transfiniteLabeled = transfiniteLabeled;
		this.carSpeed = carSpeed;
		this.acceleration = acceleration;
		this.equivalentShaft = equivalentShaft;
		this.licensePlate = licensePlate;
		this.lincenseColor = lincenseColor;
		this.bridgeCode = bridgeCode;
		this.vehicleLength = vehicleLength;
		this.sensorCoding = sensorCoding;
		this.id = id;
	}
	public SensorWeightVO() {
		super();
	}
	@Override
	public String toString() {
		return "SensorWeightDto [name=" + name + ", weight=" + weight + ", samplingTime=" + samplingTime + ", actual="
				+ actual + ", messageType=" + messageType + ", reserved=" + reserved + ", direction=" + direction
				+ ", computerNumber=" + computerNumber + ", commandCode=" + commandCode + ", dataType=" + dataType
				+ ", imgNumber=" + imgNumber + ", trackNumber=" + trackNumber + ", testTime=" + testTime + ", lastrow="
				+ lastrow + ", singleShaftNuber=" + singleShaftNuber + ", setsShaftNuber=" + setsShaftNuber
				+ ", totalWeight=" + totalWeight + ", overrunRate=" + overrunRate + ", models=" + models + ", left1="
				+ left1 + ", left2=" + left2 + ", left3=" + left3 + ", left4=" + left4 + ", left5=" + left5 + ", left6="
				+ left6 + ", left7=" + left7 + ", left8=" + left8 + ", right1=" + right1 + ", right2=" + right2
				+ ", right3=" + right3 + ", right4=" + right4 + ", right5=" + right5 + ", right6=" + right6
				+ ", right7=" + right7 + ", right8=" + right8 + ", spacing12=" + spacing12 + ", spacing23=" + spacing23
				+ ", spacing34=" + spacing34 + ", spacing45=" + spacing45 + ", spacing56=" + spacing56 + ", spacing67="
				+ spacing67 + ", spacing78=" + spacing78 + ", violationCode=" + violationCode + ", transfiniteLabeled="
				+ transfiniteLabeled + ", carSpeed=" + carSpeed + ", acceleration=" + acceleration
				+ ", equivalentShaft=" + equivalentShaft + ", licensePlate=" + licensePlate + ", lincenseColor="
				+ lincenseColor + ", bridgeCode=" + bridgeCode + ", vehicleLength=" + vehicleLength + ", sensorCoding="
				+ sensorCoding + ", id=" + id + "]";
	}


}
