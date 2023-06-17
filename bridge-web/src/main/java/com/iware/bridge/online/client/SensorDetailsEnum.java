package com.iware.bridge.online.client;


/**
 * 对方传感器细项映射到我们平台的细项id
 */
public enum SensorDetailsEnum {

    INCLINATION_X("TILT", 6), //倾角X
    INCLINATION_Y("TILT", 10), //倾角Y
    DISPLACEMENT("CRACK", 8), //位移
    STRAIN("STRAIN", 2); //应变

    private String typeName;

    private Integer sensorDetailsId;

    SensorDetailsEnum(String typeName, Integer sensorDetailsId) {
        this.typeName = typeName;
        this.sensorDetailsId = sensorDetailsId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSensorDetailsId() {
        return sensorDetailsId;
    }

    public void setSensorDetailsId(Integer sensorDetailsId) {
        this.sensorDetailsId = sensorDetailsId;
    }

    public static Integer getDetailsIdByTypeName(String typeName){
        SensorDetailsEnum[] values = SensorDetailsEnum.values();
        for (SensorDetailsEnum value : values) {
            if (value.getTypeName().equals(typeName)){
                return value.getSensorDetailsId();
            }
        }
        return null;
    }
}
