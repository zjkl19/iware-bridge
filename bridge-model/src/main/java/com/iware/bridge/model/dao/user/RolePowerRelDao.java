package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.RolePowerRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * RolePowerRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface RolePowerRelDao {

	/** 保存 */
	public void save(RolePowerRel rolePowerRel);

	/** 根据ID删除RolePowerRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(RolePowerRel rolePowerRel);

	/** 根据ID查询RolePowerRel */
	RolePowerRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<RolePowerRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<RolePowerRel> findAll();

    /** 根据条件查询 */
    public List<RolePowerRel> query(RolePowerRel rolePowerRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<RolePowerRel> queryByLike(RolePowerRel rolePowerRel);

}
