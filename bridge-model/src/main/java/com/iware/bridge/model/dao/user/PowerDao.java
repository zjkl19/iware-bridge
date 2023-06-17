package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.Power;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PowerDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PowerDao {

	/** 保存 */
	public void save(Power power);

	/** 根据ID删除Power */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Power power);

	/** 根据ID查询Power */
	Power findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Power> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Power> findAll();

    /** 根据条件查询 */
    public List<Power> query(Power power);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Power> queryByLike(Power power);

}
