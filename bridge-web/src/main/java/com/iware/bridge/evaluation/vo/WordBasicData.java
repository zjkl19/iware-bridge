package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "WordBasicData", description = "poi文档基础数据")
public class WordBasicData {

    @ApiModelProperty(value = "业主名称")
    private String userName;
    @ApiModelProperty(value = "工程地点")
    private String projectSite;
    @ApiModelProperty(value = "检验内容")
    private String detectionType;
    @ApiModelProperty(value = "检验依据")
    private String DetectionBase;
    @ApiModelProperty(value = "BCI评分")
    private String BciScore;
    @ApiModelProperty(value = "Bci等级")
    private String BciLevel;
    @ApiModelProperty(value = "桥梁现状照片")
    private List<PhotoUrl> photoManage;
    @ApiModelProperty(value = "桥面系病害位置图")
    private List<PhotoUrl> totalBD;
    @ApiModelProperty(value = "桥面系缺损状况")
    private List<WordCheckResults> BD;
    @ApiModelProperty(value = "上部结构病害位置图")
    private List<PhotoUrl> totalSUP;
    @ApiModelProperty(value = "上部结构缺损状况")
    private List<WordCheckResults> SUP;
    @ApiModelProperty(value = "下部结构病害位置图")
    private List<PhotoUrl> totalSUB;
    @ApiModelProperty(value = "下部结构缺损状况")
    private List<WordCheckResults> SUB;
    @ApiModelProperty(value = "桥梁养护等级")
    private String bridgeLevel;
    @ApiModelProperty(value = "桥面系完好状况评估结果")
    private List<WordEvaluateResults> BDEvaluateResults;
    @ApiModelProperty(value = "上部结构完好状况评估结果")
    private List<WordEvaluateResults> SUPEvaluateResults;
    @ApiModelProperty(value = "下部结构完好状况评估结果")
    private List<WordEvaluateResults> SUBEvaluateResults;
    @ApiModelProperty(value = "路径前缀")
    private String file;
    @ApiModelProperty(value = "项目前缀")
    private String context;
    @ApiModelProperty(value = "桥面系权重")
    private Float BDWeight;
    @ApiModelProperty(value = "上部结构权重")
    private Float SUPWeight;
    @ApiModelProperty(value = "下部结构权重")
    private Float SUBWeight;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Float getBDWeight() {
        return BDWeight;
    }

    public void setBDWeight(Float BDWeight) {
        this.BDWeight = BDWeight;
    }

    public Float getSUPWeight() {
        return SUPWeight;
    }

    public void setSUPWeight(Float SUPWeight) {
        this.SUPWeight = SUPWeight;
    }

    public Float getSUBWeight() {
        return SUBWeight;
    }

    public void setSUBWeight(Float SUBWeight) {
        this.SUBWeight = SUBWeight;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectSite() {
        return projectSite;
    }

    public void setProjectSite(String projectSite) {
        this.projectSite = projectSite;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
    }

    public String getDetectionBase() {
        return DetectionBase;
    }

    public void setDetectionBase(String detectionBase) {
        DetectionBase = detectionBase;
    }

    public String getBciScore() {
        return BciScore;
    }

    public void setBciScore(String bciScore) {
        BciScore = bciScore;
    }

    public String getBciLevel() {
        return BciLevel;
    }

    public void setBciLevel(String bciLevel) {
        BciLevel = bciLevel;
    }

    public List<PhotoUrl> getPhotoManage() {
        return photoManage;
    }

    public void setPhotoManage(List<PhotoUrl> photoManage) {
        this.photoManage = photoManage;
    }


    public List<WordCheckResults> getBD() {
        return BD;
    }

    public void setBD(List<WordCheckResults> BD) {
        this.BD = BD;
    }

    public List<WordCheckResults> getSUP() {
        return SUP;
    }

    public void setSUP(List<WordCheckResults> SUP) {
        this.SUP = SUP;
    }

    public List<WordCheckResults> getSUB() {
        return SUB;
    }

    public void setSUB(List<WordCheckResults> SUB) {
        this.SUB = SUB;
    }


    public List<PhotoUrl> getTotalBD() {
        return totalBD;
    }

    public void setTotalBD(List<PhotoUrl> totalBD) {
        this.totalBD = totalBD;
    }

    public List<PhotoUrl> getTotalSUP() {
        return totalSUP;
    }

    public void setTotalSUP(List<PhotoUrl> totalSUP) {
        this.totalSUP = totalSUP;
    }

    public List<PhotoUrl> getTotalSUB() {
        return totalSUB;
    }

    public void setTotalSUB(List<PhotoUrl> totalSUB) {
        this.totalSUB = totalSUB;
    }

    public String getBridgeLevel() {
        return bridgeLevel;
    }

    public void setBridgeLevel(String bridgeLevel) {
        this.bridgeLevel = bridgeLevel;
    }

    public List<WordEvaluateResults> getBDEvaluateResults() {
        return BDEvaluateResults;
    }

    public void setBDEvaluateResults(List<WordEvaluateResults> BDEvaluateResults) {
        this.BDEvaluateResults = BDEvaluateResults;
    }

    public List<WordEvaluateResults> getSUPEvaluateResults() {
        return SUPEvaluateResults;
    }

    public void setSUPEvaluateResults(List<WordEvaluateResults> SUPEvaluateResults) {
        this.SUPEvaluateResults = SUPEvaluateResults;
    }

    public List<WordEvaluateResults> getSUBEvaluateResults() {
        return SUBEvaluateResults;
    }

    public void setSUBEvaluateResults(List<WordEvaluateResults> SUBEvaluateResults) {
        this.SUBEvaluateResults = SUBEvaluateResults;
    }
}
