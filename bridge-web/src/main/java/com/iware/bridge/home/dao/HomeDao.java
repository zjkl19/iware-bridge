package com.iware.bridge.home.dao;

import com.iware.bridge.home.vo.BridgeScoreVO;
import com.iware.bridge.home.vo.HomeFilter;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.entity.inspection.PlanDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ZRB
 * @date 2021-8-4
 */

@Repository
public interface HomeDao {

    /** 获取今年之前用户项目 */
    public List<Integer> getLastUnitProjectIds(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId);

    /** 获取各个市桥隧数 */
    public List<EchartMap> sortByArea(@Param("list") List<Integer> projectIds);

    /** 获取巡查概况 */
    public Integer getInspectionInfo(@Param("powerId") Integer powerId,
                                             @Param("type") Integer type,
                                             @Param("filter")HomeFilter filter);

    /** 获取维养概况 */
    public Integer getMaintainInfo(@Param("powerId") Integer powerId,
                                           @Param("type") Integer type,
                                           @Param("filter")HomeFilter filter);

    /** 获取用户桥梁评分（无BCI评分默认按结构物本身等级） 暂时只有桥梁 */
    public List<BridgeScoreVO> getBridgeScore(@Param("roleId")Integer roleId,
                                              @Param("unitId")Integer unitId,
                                              @Param("structureId")Integer structureId);

    /** 获取检测评估所有计划状态 */
    public List<Integer> getEvaluationPlanStatus(@Param("roleId")Integer roleId,
                                                 @Param("unitId")Integer unitId,
                                                 @Param("structureId")Integer structureId);

    /** 获取结构物最新一次评分 */
    public BridgeScoreVO getEvaluationInfo(@Param("roleId")Integer roleId, @Param("unitId")Integer unitId,
                                           @Param("structureId")Integer structureId, @Param("powerId") Integer powerId);

    /** 获取结构物最新一次病害数 */
    public Integer getStructureDisease(@Param("monitorPlanRelId") Integer monitorPlanRelId);

    public PlanDetail getLastPlanDetail(@Param("roleId")Integer roleId, @Param("unitId")Integer unitId,
                                        @Param("structureId")Integer structureId, @Param("powerId") Integer powerId);

    /** 获取详情页结构物巡查病害状况*/
    public Map<String, String> getInspectionLastInfo(@Param("planDetailId")Integer planDetailId);

    /** 获取详情页结构物最后一次巡查的维养状况 */
    public List<EchartMap> getMaintainLastInfo(@Param("planDetailId")Integer planDetailId);

    /** 获取用户结构物历史维养记录 */
    public List<EchartMap> getMainEvaHistory(@Param("roleId")Integer roleId, @Param("unitId")Integer unitId,
                                                @Param("structureId")Integer structureId, @Param("powerId") Integer power);

    /** 获取传感器数据 */
    public Map<String, String> getSensorData(@Param("sensorCoding") String sensorCoding,
                                             @Param("type") Integer type);

    /** 获取所有在自己指派期间内修复的病害 **/
    public Integer getAllRepair(@Param("roleId")Integer roleId, @Param("unitId")Integer unitId,
                                @Param("structureId")Integer structureId, @Param("powerId") Integer powerId);
}
