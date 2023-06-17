package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.UserAnnouncementRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * UserAnnouncementRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface UserAnnouncementRelDao {

	/** 保存 */
	public void save(UserAnnouncementRel userAnnouncementRel);

	/** 根据ID删除UserAnnouncementRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(UserAnnouncementRel userAnnouncementRel);

	/** 根据ID查询UserAnnouncementRel */
	UserAnnouncementRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<UserAnnouncementRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<UserAnnouncementRel> findAll();

    /** 根据条件查询 */
    public List<UserAnnouncementRel> query(UserAnnouncementRel userAnnouncementRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<UserAnnouncementRel> queryByLike(UserAnnouncementRel userAnnouncementRel);

}
