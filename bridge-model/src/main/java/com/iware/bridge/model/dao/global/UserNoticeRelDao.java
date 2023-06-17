package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.UserNoticeRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserNoticeRelDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface UserNoticeRelDao {

    /** 保存 */
    public void save(UserNoticeRel userNoticeRel);

    /** 根据ID删除UserNoticeRel */
    public void deleteById(@Param("id") Integer id);

    /** 更新 */
    public void update(UserNoticeRel userNoticeRel);

    /** 根据ID查询UserNoticeRel */
    UserNoticeRel findById(@Param("id") Integer id);

    /** 批量添加 */
    public void batchSave(@Param("list") List<UserNoticeRel> list);

    /** 批量删除 */
    public void batchDelete(@Param("ids") List<Integer> ids);

    /** 查询所有 */
    public List<UserNoticeRel> findAll();

    /** 根据条件查询 */
    public List<UserNoticeRel> query(UserNoticeRel userNoticeRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<UserNoticeRel> queryByLike(UserNoticeRel userNoticeRel);

}