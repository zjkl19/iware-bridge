package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.AppNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AppNoticeDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface AppNoticeDao {

    /** 保存 */
    public void save(AppNotice appNotice);

    /** 根据ID删除AppNotice */
    public void deleteById(@Param("id") Integer id);

    /** 更新 */
    public void update(AppNotice appNotice);

    /** 根据ID查询AppNotice */
    AppNotice findById(@Param("id") Integer id);

    /** 批量添加 */
    public void batchSave(@Param("list") List<AppNotice> list);

    /** 批量删除 */
    public void batchDelete(@Param("ids") List<Integer> ids);

    /** 查询所有 */
    public List<AppNotice> findAll();

    /** 根据条件查询 */
    public List<AppNotice> query(AppNotice appNotice);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<AppNotice> queryByLike(AppNotice appNotice);

}