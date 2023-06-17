package com.iware.bridge.app.assess.dao;

import com.iware.bridge.app.assess.vo.offline.property.BridgeTypeComponentVo;
import com.iware.bridge.app.assess.vo.offline.property.BridgeTypeVo;
import com.iware.bridge.app.assess.vo.offline.property.ComponentDiseaseVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppPropertiesDao {
	/**
	 * 获取桥梁类型部件关联
	 * @return
	 */
	public List<BridgeTypeComponentVo> getBridgeTypeComponentAllList();
	
	/**
	 * 获取部件病害关联
	 * @return
	 */
	public List<ComponentDiseaseVo> getComponentDiseaseAllList();
	
	/**
	 * 获取桥梁属性
	 * @return
	 */
	public List<BridgeTypeVo> getBridgeTypeOptionsList();
	/**
	 * 获取桥梁类型以及对应底下的组件
	 * @return
	 */
	public List<BridgeTypeVo> getBridgeTypeAndComponentAllList();
	
	
}
