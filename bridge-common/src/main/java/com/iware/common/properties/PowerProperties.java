package com.iware.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PowerProperties {

    @Value("${power.online}")
    private Integer onlinePower;
    @Value("${power.inspection}")
    private Integer inspectionPower;
    @Value("${power.maintain}")
    private Integer maintainPower;
    @Value("${power.evaluation}")
    private Integer evaluationPower;
    @Value("${power.video}")
    private Integer video;

    public Integer getOnlinePower() {
        return onlinePower;
    }

    public void setOnlinePower(Integer onlinePower) {
        this.onlinePower = onlinePower;
    }

    public Integer getInspectionPower() {
        return inspectionPower;
    }

    public void setInspectionPower(Integer inspectionPower) {
        this.inspectionPower = inspectionPower;
    }

    public Integer getMaintainPower() {
        return maintainPower;
    }

    public void setMaintainPower(Integer maintainPower) {
        this.maintainPower = maintainPower;
    }

    public Integer getEvaluationPower() {
        return evaluationPower;
    }

    public void setEvaluationPower(Integer evaluationPower) {
        this.evaluationPower = evaluationPower;
    }

    public Integer getVideo() {
        return video;
    }

    public void setVideo(Integer video) {
        this.video = video;
    }
}
