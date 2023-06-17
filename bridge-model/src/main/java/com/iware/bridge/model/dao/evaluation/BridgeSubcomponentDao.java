package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.BridgeSubcomponent;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * BridgeSubcomponentDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface BridgeSubcomponentDao {

	/** 保存 */
	public void save(BridgeSubcomponent bridgeSubcomponent);

	/** 根据ID删除BridgeSubcomponent */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(BridgeSubcomponent bridgeSubcomponent);

	/** 根据ID查询BridgeSubcomponent */
	BridgeSubcomponent findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<BridgeSubcomponent> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<BridgeSubcomponent> findAll();

    /** 根据条件查询 */
    public List<BridgeSubcomponent> query(BridgeSubcomponent bridgeSubcomponent);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<BridgeSubcomponent> queryByLike(BridgeSubcomponent bridgeSubcomponent);

}
