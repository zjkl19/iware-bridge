package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.User;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * UserDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface UserDao {

	/** 保存 */
	public void save(User user);

	/** 根据ID删除User */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(User user);

	/** 根据ID查询User */
	User findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<User> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<User> findAll();

    /** 根据条件查询 */
    public List<User> query(User user);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<User> queryByLike(User user);

}
