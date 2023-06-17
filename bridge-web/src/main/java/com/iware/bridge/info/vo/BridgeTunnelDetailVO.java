package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.TunnelInfo;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(value = "BridgeTunnelDetailVO",description = "桥隧详细信息")
public class BridgeTunnelDetailVO extends Structure implements Serializable {

    private static final long serialVersionUID = 1163456120970511032L;

    private BridgeInfo bridgeInfo;
    private TunnelInfo tunnelInfo;

    public BridgeInfo getBridgeInfo() {
        return bridgeInfo;
    }

    public void setBridgeInfo(BridgeInfo bridgeInfo) {
        this.bridgeInfo = bridgeInfo;
    }

    public TunnelInfo getTunnelInfo() {
        return tunnelInfo;
    }

    public void setTunnelInfo(TunnelInfo tunnelInfo) {
        this.tunnelInfo = tunnelInfo;
    }
}
