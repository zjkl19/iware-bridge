package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.PierAbutment;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PierAbutmentDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PierAbutmentDao {

	/** 保存 */
	public void save(PierAbutment pierAbutment);

	/** 根据ID删除PierAbutment */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(PierAbutment pierAbutment);

	/** 根据ID查询PierAbutment */
	PierAbutment findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<PierAbutment> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<PierAbutment> findAll();

    /** 根据条件查询 */
    public List<PierAbutment> query(PierAbutment pierAbutment);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<PierAbutment> queryByLike(PierAbutment pierAbutment);

}
