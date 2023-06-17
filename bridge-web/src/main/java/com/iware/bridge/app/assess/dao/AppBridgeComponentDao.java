package com.iware.bridge.app.assess.dao;

import com.iware.bridge.app.assess.vo.bridge.BridgeSubcomponentVos;
import com.iware.bridge.model.entity.evaluation.BridgeDeckComponent;
import com.iware.bridge.model.entity.evaluation.BridgeSubcomponent;
import com.iware.bridge.model.entity.evaluation.BridgeSupcomponent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppBridgeComponentDao {
    //根据id数组获取桥梁桥面系构件
    public List<BridgeDeckComponent> getBridgeDeckComponentListByIds(@Param("ids") List<Integer> ids);
    //根据id数组获取桥梁上部结构构件
    public List<BridgeSupcomponent> getBridgeSupcomponentListByIds(@Param("ids") List<Integer> ids);
    //根据id数组获取桥梁下部结构构件
    public List<BridgeSubcomponent> getBridgeSubcomponentListByIds(@Param("ids") List<Integer> ids);
    public List<BridgeSubcomponentVos> getBridgeSubcomponentVoListByIds(@Param("ids") List<Integer> ids);
}
