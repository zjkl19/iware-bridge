package com.iware.bridge.evaluation.service;


import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.entity.evaluation.Attachment;
import com.iware.bridge.model.entity.evaluation.BridgeRoad;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-8-16
 */

public interface EvaluationRecordService {

    /** 查询检测列表 **/
    public List<AssessRecordVO> getRecordList(AssessRecordFilter assessRecordFilter);

    /** 获取BCI评价列表 **/
    public List<BCIEvaluation> getBCIEvaluationList(AssessRecordFilter assessRecordFilter);

    /** 获取桥梁概况的树形结构 **/
    public List<BridgeSurveyFirstTierVO> getBridgeSurveyDetail(BridgeSurveyDetailListFilter bridgeSurveyDetailListFilter);

    /** 计划结构物是否导出原始记录 **/
    public boolean isOriginalRecordByStructure(Integer id);

    /** 获取结构图片 */
    public List<StructPhoto> getBridgeStructPhoto(StructPhotoFilter structPhotoFilter);

    /** 获取病害数据 **/
    public List<DiseaseBridge> getDiseaseList(DiseaseBridgeListFilterVO diseaseBridgeListFilterVO);

    /** 添加结构图片 */
    public Attachment addStructPhoto(MultipartFile file, Integer type, Integer partType, Integer targetId);

    /** 获取照片数据 **/
    List<PhotoUrl> getPhotoUrl(Integer targetId);

    /** 添加照片地址 */
    public void addPhotoUrl(Map<String, String> map, MultipartFile file);

    /** 删除照片 **/
    public void deletePhoto(Integer id);

    /** 选择默认图片 */
    public void selectDefaultStructPhoto(Attachment attachment);

    /** 下载图片压缩包 */
    public String downloadStructPhoto(StructPhotoFilter structPhotoFilter);

    /** 修改照片状态 **/
    public void updatePhoto(Attachment attachment);

    /** 获取病害查看记录数据 **/
    public PageInfo<RecordVO> getDiseaseRecordList(RecordListFilter recordListFilter);

    /** 获取回收站数据 **/
    public PageInfo<RecyclingVO> getRecyclingList(RecyclingListFilter recyclingListFilter);

    /** 回收站替换记录 **/
    public void replaceRecord(RecordDetailListFilter recordDetailListFilter);

    /** 获取回收站查看数据详情数据 **/
    public List<DiseaseBridge> getRecordDetailList(RecordDetailListFilter recordDetailListFilter);

    /** 删除图片 */
    public void deleteStrunctPhoto(Integer Id);

    /** 获取检测记录表信息 **/
    public List<ShowManage> getRecordDetail(Integer id);

    /** 获取计划结构物原始记录信息 **/
    public OriginalRecord getOriginalRecord(Integer id);

    /** 导出记录 **/
    public String exportRecord(Integer id);

    /** 生成记录 **/
    public void insertRecord(OriginalRecord originalRecord);

    /** 获取检测报告列表 **/
    public List<RoadBridge> selectRoadList(Integer structureId);

    /** 生成报告 **/
    public Integer generateReport(GenerateReportVO generateReportVO);

    /** 删除结构物计划**/
    public void delMonitorStructure(Integer id);

    /** 查询线路病害 **/
    public List<DiseaseSummary> selectDiseaseSummary(Integer id);

    /** 计算线路下所有病害的扣分值**/
    public void calculateDeduct(Integer roadId);

    /** 下载检测报告**/
    public List<BridgeRoad> downloadTestReport(Integer roadId);

    /** 桥梁概况查询信息 **/
    public Map<String,Object> getBSInformation(BridgeSituationFilter bridgeSituationFilter);

}
