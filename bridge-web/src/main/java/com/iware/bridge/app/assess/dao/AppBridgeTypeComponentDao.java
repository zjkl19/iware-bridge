package com.iware.bridge.app.assess.dao;

import com.iware.bridge.app.assess.vo.weight.BridgeTypeComponentFilterVo;
import com.iware.bridge.model.entity.evaluation.BridgeTypeComponentRel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppBridgeTypeComponentDao {
	public List<BridgeTypeComponentRel> getBirdgeTypeCompList(BridgeTypeComponentFilterVo filter);
}
