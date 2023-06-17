package com.iware.bridge.app.assess.service;

import java.util.List;
import java.util.Map;


public interface AppCompWeightCalcService {
	

	public void setBridgeType(Integer type);
	/**
	 * 获取桥面系构件实际权重
	 * @param list
	 * @param isInitial 是否是原始权重
	 * @return
	 */
	public <T> Map<Integer,Double> getBridgeDeckCompWeightList(List<T> list,Boolean isInitial);
	/**
	 * 获取上部结构构件实际权重
	 * @param list
	 * @param isInitial 是否是原始权重
	 * @return
	 */
	public <T> Map<Integer,Double> getBridgeSupCompWeightList(List<T> list,Boolean isInitial);
	/**
	 * 获取下部结构构件实际权重
	 * @param list
	 * @param isInitial 是否是原始权重
	 * @return
	 */
	public <T> Map<Integer,Double> getBridgeSubCompWeightList(List<T> list,Boolean isInitial);


}
