package com.iware.bridge.app.assess.vo.offline.offlineData;

import com.iware.bridge.model.entity.evaluation.BridgeSupstructure;

import java.util.ArrayList;

public class BridgeSupstructureVo extends BridgeSupstructure {

    private ArrayList<AttachmentVo> attachments=new ArrayList<>();

    public ArrayList<AttachmentVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<AttachmentVo> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(AttachmentVo a){
        attachments.add(a);
    }

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
