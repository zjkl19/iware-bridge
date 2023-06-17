package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.PropertyOptionRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PropertyOptionRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PropertyOptionRelDao {

	/** 保存 */
	public void save(PropertyOptionRel propertyOptionRel);

	/** 根据ID删除PropertyOptionRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(PropertyOptionRel propertyOptionRel);

	/** 根据ID查询PropertyOptionRel */
	PropertyOptionRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<PropertyOptionRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<PropertyOptionRel> findAll();

    /** 根据条件查询 */
    public List<PropertyOptionRel> query(PropertyOptionRel propertyOptionRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<PropertyOptionRel> queryByLike(PropertyOptionRel propertyOptionRel);

}
