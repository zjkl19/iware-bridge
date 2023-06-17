package com.iware.bridge.evaluation.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: wjp
 * @date: 2021年8月13日10:19:06
 * @since 1.0
 */
@ApiModel(value = "BridgeLevelRankFilter",description = "桥隧评分排行条件")
public class BridgeLevelRankFilter {

    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    @ApiModelProperty(value = "获取指定年份数据, 只传入年份")
    private String year;

    @ApiModelProperty(value = "BCI升降序方式，desc与asc")
    private String bciSortType;

    @ApiModelProperty(value = "BSI升降序方式，desc与asc")
    private String bsiSortType;

    @ApiModelProperty(value = "桥梁等级")
    private String bridgeLevel;
    @ApiModelProperty(value = "结构物列表")
    @JsonIgnore
    private List<ScoreSearchVO> list;

    public List<ScoreSearchVO> getList() {
        return list;
    }

    public void setList(List<ScoreSearchVO> list) {
        this.list = list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBciSortType() {
        return bciSortType;
    }

    public void setBciSortType(String bciSortType) {
        this.bciSortType = bciSortType;
    }

    public String getBsiSortType() {
        return bsiSortType;
    }

    public void setBsiSortType(String bsiSortType) {
        this.bsiSortType = bsiSortType;
    }

    public String getBridgeLevel() {
        return bridgeLevel;
    }

    public void setBridgeLevel(String bridgeLevel) {
        this.bridgeLevel = bridgeLevel;
    }
}
