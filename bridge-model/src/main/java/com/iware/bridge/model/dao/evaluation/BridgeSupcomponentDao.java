package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.BridgeSupcomponent;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * BridgeSupcomponentDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface BridgeSupcomponentDao {

	/** 保存 */
	public void save(BridgeSupcomponent bridgeSupcomponent);

	/** 根据ID删除BridgeSupcomponent */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(BridgeSupcomponent bridgeSupcomponent);

	/** 根据ID查询BridgeSupcomponent */
	BridgeSupcomponent findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<BridgeSupcomponent> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<BridgeSupcomponent> findAll();

    /** 根据条件查询 */
    public List<BridgeSupcomponent> query(BridgeSupcomponent bridgeSupcomponent);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<BridgeSupcomponent> queryByLike(BridgeSupcomponent bridgeSupcomponent);

}
