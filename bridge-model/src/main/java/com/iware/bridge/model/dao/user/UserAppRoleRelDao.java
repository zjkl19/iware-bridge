package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.UserAppRoleRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * UserAppRoleRelDAO
 *
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */
@Repository
public interface UserAppRoleRelDao {

	/** 保存 */
	public void save(UserAppRoleRel userAppRoleRel);

	/** 根据ID删除UserAppRoleRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(UserAppRoleRel userAppRoleRel);

	/** 根据ID查询UserAppRoleRel */
	UserAppRoleRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<UserAppRoleRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<UserAppRoleRel> findAll();

    /** 根据条件查询 */
    public List<UserAppRoleRel> query(UserAppRoleRel userAppRoleRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<UserAppRoleRel> queryByLike(UserAppRoleRel userAppRoleRel);

}
