package com.iware.bridge.app.assess.service;

import com.iware.bridge.app.assess.dao.AppBridgeComponentDao;
import com.iware.bridge.app.assess.dao.AppBridgeTypeComponentDao;
import com.iware.bridge.app.assess.enums.BridgePartTypeEnum;
import com.iware.bridge.app.assess.vo.bridge.BridgeSubcomponentVos;
import com.iware.bridge.app.assess.vo.weight.BridgeTypeComponentFilterVo;
import com.iware.bridge.model.entity.evaluation.BridgeDeckComponent;
import com.iware.bridge.model.entity.evaluation.BridgeSubcomponent;
import com.iware.bridge.model.entity.evaluation.BridgeSupcomponent;
import com.iware.bridge.model.entity.evaluation.BridgeTypeComponentRel;
import com.iware.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppCompWeightCalcServiceImpl implements AppCompWeightCalcService {

	@Autowired
	private AppBridgeTypeComponentDao appBridgeTypeComponentDao;
	@Autowired
	private AppBridgeComponentDao appBridgeComponentDao;

	private Integer bridgeType;

	/**
	 * 获取桥面系构件实际权重
	 * @param list
	 * @return
	 */
	public <T> Map<Integer,Double> getBridgeDeckCompWeightList(List<T> list,Boolean isInitial) {
		Logger logger= LoggerFactory.getLogger(AppCompWeightCalcServiceImpl.class);
		if(!list.isEmpty()) {
			List<Integer> compIds=new ArrayList<Integer>();
			List<BridgeDeckComponent> compList=new ArrayList<BridgeDeckComponent>();
			if(list.get(0) instanceof BridgeDeckComponent){
				compList= (List<BridgeDeckComponent>) list;
				for(T item :list) {
					if(!compIds.contains(((BridgeDeckComponent) item).getComponentId())){
						compIds.add(((BridgeDeckComponent) item).getComponentId());
					}

				}
			}else{
				List<BridgeDeckComponent> deckCompList = appBridgeComponentDao.getBridgeDeckComponentListByIds((List<Integer>) list);
				compList=deckCompList;
				if(!deckCompList.isEmpty()){
					for(BridgeDeckComponent deckComp:deckCompList){
						if(!compIds.contains(deckComp.getComponentId())){
							compIds.add(deckComp.getComponentId());
						}
					}
				}
			}

			if(!compIds.isEmpty()) {
				BridgeTypeComponentFilterVo filter = new BridgeTypeComponentFilterVo();
				filter.setBridgeTypeId(bridgeType);
				filter.setPartType(BridgePartTypeEnum.BridgeDeckType.getCode());
				filter.setComponentIds(compIds);
				List<BridgeTypeComponentRel> typeCompList = appBridgeTypeComponentDao.getBirdgeTypeCompList(filter);
				if(typeCompList.isEmpty()) {
					logger.debug("bridgeType:"+bridgeType+",PartType:"+BridgePartTypeEnum.BridgeDeckType.getCode()+",ComponentIdsSize:"+compIds.size());
					throw new BusinessException("该桥面系没有权重，请核查");
				}
				if(isInitial){
					return getBridgeInitialWeightList(typeCompList);
				}else{
					return getBridgeCompWeightList(typeCompList);
				}
//				Map<Integer, Double> CompWeightMap = getBridgeCompWeightList(typeCompList);
//				//桥梁组件id权重集合
//				Map<Integer, Double> BridgeCompWeightMap=new HashMap<Integer, Double>();
//				for(BridgeDeckComponent comp:compList){
//					Double myWeight=BridgeCompWeightMap.get(comp.getComponentId());
//					BridgeCompWeightMap.put(comp.getId(),myWeight);
//
//				}
//				return BridgeCompWeightMap;

			}

		}

		return null;
	}


	@SuppressWarnings("unused")
	private  Map<Integer, Double> getBridgeCompWeightList(List<BridgeTypeComponentRel> typeCompList) {
		double weightSum = 0 ;
		Map<Integer,Double> compWeightMap=new HashMap<Integer, Double>();
		for(BridgeTypeComponentRel c:typeCompList) {
			weightSum=weightSum+c.getInitialWeight();
			compWeightMap.put(c.getComponentId(), c.getInitialWeight());
		}
		weightSum=BigDecimal.valueOf(weightSum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		double freeWeight=1-weightSum;
		if(weightSum<1) {
			for (Integer key : compWeightMap.keySet()) {
	            Double value = compWeightMap.get(key);
	            if(weightSum==0){
					compWeightMap.put(key, BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				}else{
					double calValue = (value/weightSum);
					compWeightMap.put(key, calValue);
				}

	        }
		}

		return compWeightMap;
	}

	/**
	 * 获取原始权重
	 * @param typeCompList
	 * @return
	 */
	private Map<Integer, Double> getBridgeInitialWeightList(List<BridgeTypeComponentRel> typeCompList) {
		Map<Integer,Double> compWeightMap=new HashMap<Integer, Double>();
		for(BridgeTypeComponentRel c:typeCompList) {
			compWeightMap.put(c.getComponentId(), c.getInitialWeight());
		}
		return compWeightMap;
	}
	/**
	 * 获取上部结构构件实际权重
	 * @param list
	 * @return
	 */
	public <T> Map<Integer,Double> getBridgeSupCompWeightList(List<T> list,Boolean isInitial) {

		Logger logger= LoggerFactory.getLogger(AppCompWeightCalcServiceImpl.class);
		if(!list.isEmpty()) {
			List<Integer> compIds=new ArrayList<Integer>();
			List<BridgeSupcomponent> compList=new ArrayList<BridgeSupcomponent>();
			if(list.get(0) instanceof BridgeSupcomponent){
				compList= (List<BridgeSupcomponent>) list;
				for(T item :list) {
					if(!compIds.contains(((BridgeSupcomponent) item).getComponentId())){
						compIds.add(((BridgeSupcomponent) item).getComponentId());
					}

				}
			}else{
				List<BridgeSupcomponent> deckCompList = appBridgeComponentDao.getBridgeSupcomponentListByIds((List<Integer>) list);
				compList=deckCompList;
				if(!deckCompList.isEmpty()){
					for(BridgeSupcomponent deckComp:deckCompList){
						if(!compIds.contains(deckComp.getComponentId())){
							compIds.add(deckComp.getComponentId());
						}
					}
				}
			}
			if(!compIds.isEmpty()) {
				BridgeTypeComponentFilterVo filter = new BridgeTypeComponentFilterVo();
				filter.setBridgeTypeId(bridgeType);
				filter.setPartType(BridgePartTypeEnum.SupStructType.getCode());
				filter.setComponentIds(compIds);
				List<BridgeTypeComponentRel> typeCompList = appBridgeTypeComponentDao.getBirdgeTypeCompList(filter);
				if(typeCompList.isEmpty()) {
					logger.debug("bridgeType:"+bridgeType+",PartType:"+BridgePartTypeEnum.SupStructType.getCode()+",ComponentIdsSize:"+compIds.size());
					compIds.forEach(i->{
						logger.debug("componentId..."+i);
					});
					throw new BusinessException("该上部结构没有权重，请核查");
				}
				Map<Integer, Double> compWeightMap=null;
				if(isInitial){
					compWeightMap = getBridgeInitialWeightList(typeCompList);
				}else{
					compWeightMap = getBridgeCompWeightList(typeCompList);
				}

				//桥梁组件id权重集合
				Map<Integer, Double> BridgeCompWeightMap=new HashMap<Integer, Double>();
				for(BridgeSupcomponent comp:compList){
					Double myWeight=compWeightMap.get(comp.getComponentId());
					BridgeCompWeightMap.put(comp.getId(),myWeight);

				}
				return BridgeCompWeightMap;

			}

		}

		return null;
	}
	/**
	 * 获取下部结构构件实际权重
	 * @param list
	 * @return
	 */
	public <T> Map<Integer,Double> getBridgeSubCompWeightList(List<T> list,Boolean isInitial) {

		Logger logger= LoggerFactory.getLogger(AppCompWeightCalcServiceImpl.class);
		if(!list.isEmpty()) {
//			List<Integer> compIds=new ArrayList<Integer>();
			List<Integer> subtructIds=new ArrayList<Integer>();
			List<BridgeSubcomponentVos> compList=new ArrayList<BridgeSubcomponentVos>();
			if(list.get(0) instanceof BridgeSubcomponent){
				List<Integer> subCompIds=new ArrayList<Integer>();
				List<BridgeSubcomponent> subCompList = (List<BridgeSubcomponent>) list;
				for(T item :list) {
//					compIds.add(((BridgeSubcomponent) item).getComponentId());
					subtructIds.add(((BridgeSubcomponent) item).getBridgeSubstructureId());
					subCompIds.add(((BridgeSubcomponent) item).getId());


				}
				List<BridgeSubcomponentVos> deckCompList = appBridgeComponentDao.getBridgeSubcomponentVoListByIds(subCompIds);
				compList=deckCompList;
			}else{
				List<BridgeSubcomponentVos> deckCompList = appBridgeComponentDao.getBridgeSubcomponentVoListByIds((List<Integer>) list);
				compList=deckCompList;
				if(!deckCompList.isEmpty()){
					for(BridgeSubcomponent deckComp:deckCompList){

//						compIds.add(deckComp.getComponentId());
						subtructIds.add(deckComp.getBridgeSubstructureId());

					}
				}
			}
			if(compList.size()>0){
				//桥墩构件id列表
				List<Integer> pierCompIdList=new ArrayList<>();
				//桥台构件id列表
				List<Integer> abutmentCompIdList=new ArrayList<>();
				for(BridgeSubcomponentVos compo : compList){
					Integer isAbutment = compo.getIsAbutment();
					if(isAbutment!=null){
						if(isAbutment==0){
							pierCompIdList.add(compo.getComponentId());
						}else{
							abutmentCompIdList.add(compo.getComponentId());
						}
					}

				}
				BridgeTypeComponentFilterVo filter = new BridgeTypeComponentFilterVo();
				Map<Integer, Double> BridgePierCompWeightMap=new HashMap<Integer, Double>();
				if(!pierCompIdList.isEmpty()){
					//桥墩权重

					filter.setBridgeTypeId(bridgeType);
					filter.setPartType(BridgePartTypeEnum.SubStructType.getCode());
					filter.setComponentIds(pierCompIdList);
					filter.setPierAbutment(0);
					List<BridgeTypeComponentRel> typeCompList = appBridgeTypeComponentDao.getBirdgeTypeCompList(filter);
//				if(typeCompList.isEmpty()) {
//					logger.debug("bridgeType:"+bridgeType+",PartType:"+BridgePartTypeEnum.SubStructType.getCode()+",ComponentIdsSize:"+compIds.size()+"PierAbutment0");
//					throw new BusinessException("该下部结构桥墩没有权重，请核查");
//				}
					Map<Integer, Double> pierCompWeightMap=null;
					if(isInitial){
						pierCompWeightMap=getBridgeInitialWeightList(typeCompList);
					}else{
						pierCompWeightMap= getBridgeCompWeightList(typeCompList);
					}
					//桥梁组件id权重集合

					for(BridgeSubcomponentVos comp:compList){
						Double myWeight=pierCompWeightMap.get(comp.getComponentId());
						if(myWeight!=null &&comp.getIsAbutment()!=null){
							BridgePierCompWeightMap.put(comp.getId(),myWeight);
						}


					}
				}

				Map<Integer, Double> BridgeAbutmentCompWeightMap=new HashMap<Integer, Double>();
				if(!abutmentCompIdList.isEmpty()){
					//桥台权重
					filter.setBridgeTypeId(bridgeType);
					filter.setComponentIds(abutmentCompIdList);
					filter.setPartType(BridgePartTypeEnum.SubStructType.getCode());
					filter.setPierAbutment(1);
					List<BridgeTypeComponentRel> typeCompList  = appBridgeTypeComponentDao.getBirdgeTypeCompList(filter);
//				if(typeCompList.isEmpty()) {
//					logger.debug("bridgeType:"+bridgeType+",PartType:"+BridgePartTypeEnum.SubStructType.getCode()+",ComponentIdsSize:"+compIds.size()+"PierAbutment1");
//					throw new BusinessException("该下部结构桥台没有权重，请核查");
//				}
					Map<Integer, Double> abutmentCompWeightMap=null;
					if(isInitial){
						abutmentCompWeightMap=getBridgeInitialWeightList(typeCompList);
					}else{
						abutmentCompWeightMap= getBridgeCompWeightList(typeCompList);
					}
					//桥梁组件id权重集合

					for(BridgeSubcomponentVos comp:compList){
						Double myWeight=abutmentCompWeightMap.get(comp.getComponentId());
						if(myWeight!=null && comp.getIsAbutment()!=null&&comp.getIsAbutment()==1){
							BridgeAbutmentCompWeightMap.put(comp.getId(),myWeight);
						}


					}
				}

				//桥墩和桥台构件合并为下部结构构件
				Map<Integer, Double> BridgeCompWeightMap=new HashMap<>();
				BridgeCompWeightMap.putAll(BridgePierCompWeightMap);
				BridgeCompWeightMap.putAll(BridgeAbutmentCompWeightMap);
				return BridgeCompWeightMap;
			}

		}
		return null;
	}
	@Override
	public void setBridgeType(Integer type) {
		// TODO Auto-generated method stub
		this.bridgeType=type;
	}
}
