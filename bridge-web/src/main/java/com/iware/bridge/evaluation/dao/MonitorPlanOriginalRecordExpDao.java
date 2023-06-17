package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.OriginalRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author WJP
 * @date 2021-8-16
 */
@Repository
public interface MonitorPlanOriginalRecordExpDao {

    /** 查看结构物的原始记录是否存在 **/
    public Integer selectOriginalRecordByStructure(@Param("id") Integer id);

    /** 查询计划结构物id的项目结构物和原始记录信息 **/
    public OriginalRecord selectRecordByStructureId(@Param("structureId") Integer structureId);

    /** 判断记录编号是否存在 **/
    public Integer existRecordNumber(@Param("recordNumber")String recordNumber);

    /** 保存 **/
    public Integer insertRecord(@Param("model") OriginalRecord originalRecord);

    /** 根据线路id获取原始记录 */
    public OriginalRecord selectOriginalRecordByRoadId(@Param("id") Integer id);

    /** 根据线路ID判断是否生成记录 **/
    public Integer existRecord(@Param("roadId")Long roadId);
}
