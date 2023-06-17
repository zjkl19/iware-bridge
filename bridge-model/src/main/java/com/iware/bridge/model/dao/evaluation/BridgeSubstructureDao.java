package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.BridgeSubstructure;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * BridgeSubstructureDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface BridgeSubstructureDao {

	/** 保存 */
	public void save(BridgeSubstructure bridgeSubstructure);

	/** 根据ID删除BridgeSubstructure */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(BridgeSubstructure bridgeSubstructure);

	/** 根据ID查询BridgeSubstructure */
	BridgeSubstructure findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<BridgeSubstructure> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<BridgeSubstructure> findAll();

    /** 根据条件查询 */
    public List<BridgeSubstructure> query(BridgeSubstructure bridgeSubstructure);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<BridgeSubstructure> queryByLike(BridgeSubstructure bridgeSubstructure);

}
