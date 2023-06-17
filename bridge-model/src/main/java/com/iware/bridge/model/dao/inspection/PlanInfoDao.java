package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.PlanInfo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PlanInfoDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PlanInfoDao {

	/** 保存 */
	public void save(PlanInfo planInfo);

	/** 根据ID删除PlanInfo */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(PlanInfo planInfo);

	/** 根据ID查询PlanInfo */
	PlanInfo findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<PlanInfo> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<PlanInfo> findAll();

    /** 根据条件查询 */
    public List<PlanInfo> query(PlanInfo planInfo);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<PlanInfo> queryByLike(PlanInfo planInfo);

}
