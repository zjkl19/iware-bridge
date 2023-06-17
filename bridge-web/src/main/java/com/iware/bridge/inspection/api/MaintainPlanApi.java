package com.iware.bridge.inspection.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.inspection.serivce.PlanDetailService;
import com.iware.bridge.inspection.serivce.PlanService;
import com.iware.bridge.inspection.serivce.ReceiveTimeService;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseInstanceDao;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

@RestController
@Api(value = "维养计划接口", tags="维养计划接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/maintain/plan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaintainPlanApi {

    @Autowired
    private PlanService planService;
    @Autowired
    private PlanDetailService planDetailService;

    @Resource
    private ReceiveTimeService receiveTimeService;

    @Resource
    private InspectionDiseaseInstanceDao inspectionDiseaseInstanceDao;

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取计划列表",value="获取计划列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanFilter")
    })
    public PageInfo<PlanVO> listPlan(@RequestParam(value = "pageNum") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestBody PlanFilter filter) {
        filter.setType(2);
        return new PageInfo<>(planService.listPlan(pageNum, pageSize, filter));
    }

    @PostMapping(value = "")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes="新增维养计划",value="新增维养计划",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planVO", value = "计划信息", required = true, paramType = "body", dataType = "PlanVO")
    })
    public void addMaintainPlan(@RequestBody PlanVO planVO) {
        planService.addMaintainPlan(planVO);
    }

    @PutMapping(value = "/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes="修改维养计划",value="修改维养计划",httpMethod="PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planVO", value = "计划信息", required = true, paramType = "body", dataType = "PlanVO")
    })
    public void updMaintainPlan(@PathVariable("planId") Integer planId,
                                  @RequestBody PlanVO planVO) {
        planVO.setId(planId);
        planService.updMaintainPlan(planVO);
    }

    @DeleteMapping(value = "/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes="删除维养计划",value="删除维养计划",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "integer", paramType = "path", required = true),
    })
    public void delMaintainPlan(@PathVariable("planId") Integer planId) {
        planService.delMaintainPlan(planId);
    }

    @PostMapping(value = "/detail/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取维养计划详情列表",value="获取维养计划详情列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanDetailFilter")
    })
    public PageInfo<MaintainItemVO> listMaintainItem(@RequestParam(value = "pageNum") Integer pageNum,
                                                   @RequestParam(value = "pageSize") Integer pageSize,
                                                   @RequestBody PlanDetailFilter filter) {
        PageInfo<MaintainItemVO> result=planDetailService.listMaintainItem(pageNum, pageSize, filter,false);
        for(int i = 0; i < result.getList().size(); i++){
            MaintainItemVO vo = result.getList().get(i);
            if(vo.getInspectionDiseaseId() == null){
                continue;
            }
            if(vo.getInspectionDiseaseId() <= 15){
                vo.setShowItem(vo.getQuantity() + "(" + vo.getUnit() + ")");
            }
            if(vo.getInspectionDiseaseId()>15 && vo.getInspectionDiseaseId() <= 23){
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() > 64 && vo.getInspectionDiseaseId() <= 73){    //上下部，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() == 24){
                vo.setShowItem(null);
            }
            if(vo.getInspectionDiseaseId() == 25){
                vo.setShowItem(vo.getOptionName());
            }
            if(vo.getInspectionDiseaseId() == 26){
                result.getList().remove(vo);
                i--;
            }
            if(vo.getInspectionDiseaseId()> 26 && vo.getInspectionDiseaseId() <= 63){    //隧道，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() == 64){    //其他说明
                result.getList().remove(vo);
                i--;
            }
        }
        return result;
    }

    @PostMapping(value = "/inspectionDisease/structure/{structure}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取桥梁最后一次巡查病害",value="获取桥梁最后一次巡查病害",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structure", value = "桥梁id",dataType = "int",paramType = "path",required = true)
    })
    public List<InspectionDiseaseInstanceVO> getInspectionDiseaseByStructureId(@PathVariable Integer structure, @RequestBody KeywordVo keywordVo){
        List<InspectionDiseaseInstanceVO> result = planDetailService.getInspectionDiseaseByStructureId(structure, keywordVo.getKeyword());
        for(int i = 0; i < result.size(); i++){
            InspectionDiseaseInstanceVO vo = result.get(i);
            if(vo.getInspectionDiseaseId() <= 15){    //桥面系，展示病害数量
                vo.setShowItem(vo.getQuantity() + "(" + vo.getUnit() + ")");
            }
            if(vo.getInspectionDiseaseId() > 15 && vo.getInspectionDiseaseId() <= 23){    //上下部，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() == 24){    //桥区施工展示-
                vo.setShowItem(null);
            }
            if(vo.getInspectionDiseaseId() == 25){    //其他病害，展示选项
                vo.setShowItem(vo.getOptionName());
            }
            if(vo.getInspectionDiseaseId() == 26){    //其他说明
                result.remove(vo);
                i--;
            }
            if(vo.getInspectionDiseaseId() > 26 && vo.getInspectionDiseaseId() <= 63){    //隧道，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() == 64){    //其他说明
                result.remove(vo);
                i--;
            }
        }
        return result;
    }

    @GetMapping(value = "/download/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="导出维养计划",value="导出维养计划",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id",dataType = "int",paramType = "path",required = true)
    })
    public void downloadMaintainPlan(@PathVariable("planId") Integer planId, HttpServletResponse response){
        FileUtil.pushToWeb(response, planDetailService.downloadMaintainPlan(planId), "APPLICATION/OCTET-STREAM");
    }

    @GetMapping(value = "/receiveTime/{projectId}/{isInspection}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取指派时间段",value="获取指派时间段",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id",dataType = "int",paramType = "path",required = true),
            @ApiImplicitParam(name = "isInspection", value = "是否巡查(1:巡查,0:维养)",dataType = "int",paramType = "path",required = true)
    })
    public List<ReceiveTime> getReceiveTimeByProjectId(@PathVariable("projectId") Integer projectId,
                                                       @PathVariable("isInspection") Integer isInspection){
        return receiveTimeService.getReceiveTime(projectId, isInspection==1);
    }

    @DeleteMapping(value = "/detail/{planDetailId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes="删除计划细项",value="删除计划细项",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "细项id",dataType = "int",paramType = "path",required = true)
    })
    public void deleteDetailById(@PathVariable("planDetailId") Integer planDetailId){
        planDetailService.deleteMaintainItemById(planDetailId);
    }
    @PostMapping(value = "/detail")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes="增加一条计划细项",value="增加一条计划细项",httpMethod="POST")
    @ApiImplicitParams({
    })
    public void addMaintainItem(@RequestBody MaintainItem maintainItem){
        planDetailService.addMaintainItem(maintainItem);
    }

    @PutMapping(value = "/disease/update/{diseaseInstanceId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes = "修改一个病害实例状态为0",value = "修改病害实例状态为0",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseInstanceId", value = "病害实例id",dataType = "int",paramType = "path",required = true)
    })
    public void updateDiseaseInstanceStatus0(@PathVariable Integer diseaseInstanceId){
        InspectionDiseaseInstance instance=inspectionDiseaseInstanceDao.findById(diseaseInstanceId);
        if(instance.getStatus()!=2){
            instance.setStatus(0);
            inspectionDiseaseInstanceDao.update(instance);
        }else{
            throw new BusinessException("病害已修");
        }
    }

}
