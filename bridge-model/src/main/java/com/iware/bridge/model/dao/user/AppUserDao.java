package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.AppUser;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AppUserDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface AppUserDao {

	/** 保存 */
	public void save(AppUser appUser);

	/** 根据ID删除AppUser */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(AppUser appUser);

	/** 根据ID查询AppUser */
	AppUser findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<AppUser> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<AppUser> findAll();

    /** 根据条件查询 */
    public List<AppUser> query(AppUser appUser);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<AppUser> queryByLike(AppUser appUser);

}
