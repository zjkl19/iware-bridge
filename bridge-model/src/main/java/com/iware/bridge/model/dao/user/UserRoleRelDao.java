package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.UserRoleRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * UserRoleRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface UserRoleRelDao {

	/** 保存 */
	public void save(UserRoleRel userRoleRel);

	/** 根据ID删除UserRoleRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(UserRoleRel userRoleRel);

	/** 根据ID查询UserRoleRel */
	UserRoleRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<UserRoleRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<UserRoleRel> findAll();

    /** 根据条件查询 */
    public List<UserRoleRel> query(UserRoleRel userRoleRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<UserRoleRel> queryByLike(UserRoleRel userRoleRel);

}
