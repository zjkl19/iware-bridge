package com.iware.bridge.app.inspection.service;

import com.iware.bridge.inspection.vo.DiseaseInstanceExpVo;
import com.iware.bridge.inspection.vo.PlanDetailVO;
import com.iware.bridge.inspection.vo.PlanFilter;
import com.iware.bridge.inspection.vo.PlanVO;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface AppInspectionService {
    public void updPlanDetail(PlanDetailVO planDetailVO, ArrayList<MultipartFile> files);

    public void addDisease(InspectionDiseaseInstance instance);

    public InspectionDiseaseInstance selDisease(Integer maintainItemId);

    public void delDisease(Integer diseaseInstanceId);

    public void updDisease(InspectionDiseaseInstance instance);

    public void uploadFiles(Photo photo, MultipartFile[] files);

    /** 计划列表 */
    public List<PlanVO> listPlan(PlanFilter filter);

    public PlanDetailVO selectLastInspection(Integer planDetailId);

    public List<DiseaseInstanceExpVo> selectDiseaseByDetailId(Integer planDetailId);
}
