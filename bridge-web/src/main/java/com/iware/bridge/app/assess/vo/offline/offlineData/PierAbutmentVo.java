package com.iware.bridge.app.assess.vo.offline.offlineData;

import com.iware.bridge.model.entity.evaluation.PierAbutment;

public class PierAbutmentVo extends PierAbutment {

    private boolean idReal=false;//id是否为数据库真实id，或需要将id置空令数据库自动生成

    public boolean isIdReal() {
        return idReal;
    }

    public void setIdReal(boolean idReal) {
        this.idReal = idReal;
    }
    private Integer falseKey;

    public Integer getFalseKey() {
        return falseKey;
    }

    public void setFalseKey(Integer falseKey) {
        this.falseKey = falseKey;
    }
}
