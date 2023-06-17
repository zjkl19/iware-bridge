package com.iware.bridge.app.assess.vo.offline.offlineData;

import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;

public class MonitorPlanStructureRelVo extends MonitorPlanStructureRel {

    private boolean idReal=false;//id是否为数据库真实id，或需要将id置空令数据库自动生成

    public boolean isIdReal() {
        return idReal;
    }

    public void setIdReal(boolean idReal) {
        this.idReal = idReal;
    }
}
