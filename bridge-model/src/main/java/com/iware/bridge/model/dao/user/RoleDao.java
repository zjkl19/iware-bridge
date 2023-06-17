package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.Role;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * RoleDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface RoleDao {

	/** 保存 */
	public void save(Role role);

	/** 根据ID删除Role */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Role role);

	/** 根据ID查询Role */
	Role findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Role> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Role> findAll();

    /** 根据条件查询 */
    public List<Role> query(Role role);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Role> queryByLike(Role role);

}
