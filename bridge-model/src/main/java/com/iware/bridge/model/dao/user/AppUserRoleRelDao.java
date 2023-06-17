package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.AppUserRoleRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AppUserRoleRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface AppUserRoleRelDao {

	/** 保存 */
	public void save(AppUserRoleRel appUserRoleRel);

	/** 根据ID删除AppUserRoleRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(AppUserRoleRel appUserRoleRel);

	/** 根据ID查询AppUserRoleRel */
	AppUserRoleRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<AppUserRoleRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<AppUserRoleRel> findAll();

    /** 根据条件查询 */
    public List<AppUserRoleRel> query(AppUserRoleRel appUserRoleRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<AppUserRoleRel> queryByLike(AppUserRoleRel appUserRoleRel);

}
