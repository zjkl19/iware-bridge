package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.PlanDetail;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PlanDetailDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PlanDetailDao {

	/** 保存 */
	public void save(PlanDetail planDetail);

	/** 根据ID删除PlanDetail */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(PlanDetail planDetail);

	/** 根据ID查询PlanDetail */
	PlanDetail findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<PlanDetail> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<PlanDetail> findAll();

    /** 根据条件查询 */
    public List<PlanDetail> query(PlanDetail planDetail);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<PlanDetail> queryByLike(PlanDetail planDetail);

}
