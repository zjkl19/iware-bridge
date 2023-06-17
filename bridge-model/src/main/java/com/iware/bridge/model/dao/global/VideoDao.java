package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.Video;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * VideoDAO
 * 
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface VideoDao {

	/** 保存 */
	public void save(Video video);
	
	/** 根据ID删除Video */
	public void deleteById(@Param("id") Integer id);
	
	/** 更新 */
	public void update(Video video);
	
	/** 根据ID查询Video */
	Video findById(@Param("id") Integer id);
	
	/** 批量添加 */
	public void batchSave(@Param("list") List<Video> list);
	
	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);
	
	/** 查询所有 */
	public List<Video> findAll();
    
    /** 根据条件查询 */
    public List<Video> query(Video video);
    
    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Video> queryByLike(Video video);
	
}