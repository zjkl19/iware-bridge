package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.PropertyGroupOptionRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PropertyGroupOptionRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PropertyGroupOptionRelDao {

	/** 保存 */
	public void save(PropertyGroupOptionRel propertyGroupOptionRel);

	/** 根据ID删除PropertyGroupOptionRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(PropertyGroupOptionRel propertyGroupOptionRel);

	/** 根据ID查询PropertyGroupOptionRel */
	PropertyGroupOptionRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<PropertyGroupOptionRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<PropertyGroupOptionRel> findAll();

    /** 根据条件查询 */
    public List<PropertyGroupOptionRel> query(PropertyGroupOptionRel propertyGroupOptionRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<PropertyGroupOptionRel> queryByLike(PropertyGroupOptionRel propertyGroupOptionRel);

}
