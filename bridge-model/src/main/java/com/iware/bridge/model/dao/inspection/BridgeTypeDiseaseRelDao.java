package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.BridgeTypeDiseaseRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * BridgeTypeDiseaseRelDAO
 *
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */
@Repository
public interface BridgeTypeDiseaseRelDao {

	/** 保存 */
	public void save(BridgeTypeDiseaseRel bridgeTypeDiseaseRel);

	/** 根据ID删除BridgeTypeDiseaseRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(BridgeTypeDiseaseRel bridgeTypeDiseaseRel);

	/** 根据ID查询BridgeTypeDiseaseRel */
	BridgeTypeDiseaseRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<BridgeTypeDiseaseRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<BridgeTypeDiseaseRel> findAll();

    /** 根据条件查询 */
    public List<BridgeTypeDiseaseRel> query(BridgeTypeDiseaseRel bridgeTypeDiseaseRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<BridgeTypeDiseaseRel> queryByLike(BridgeTypeDiseaseRel bridgeTypeDiseaseRel);

}
