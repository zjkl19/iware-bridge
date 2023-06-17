package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("ExportFilterVo")
public class ExportFilterVo {

    @ApiModelProperty("是否全部导出")
    private Integer exportAll;
    @ApiModelProperty("全部导出条件")
    private PlanRecordFilter filter;
    @ApiModelProperty("部分导出条件")
    private List<Integer> ids;

    public Integer getExportAll() {
        return exportAll;
    }

    public void setExportAll(Integer exportAll) {
        this.exportAll = exportAll;
    }

    public PlanRecordFilter getFilter() {
        return filter;
    }

    public void setFilter(PlanRecordFilter filter) {
        this.filter = filter;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
