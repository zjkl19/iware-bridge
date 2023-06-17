package com.iware.bridge.app.inspection.api;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.inspection.service.AppInspectionService;
import com.iware.bridge.info.service.PhotoService;
import com.iware.bridge.inspection.dao.InspectionDiseaseExpDao;
import com.iware.bridge.inspection.serivce.InspectionDiseaseService;
import com.iware.bridge.inspection.serivce.PlanDetailService;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseDao;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseOptionDao;
import com.iware.bridge.model.dao.inspection.PlanDetailDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.inspection.InspectionDisease;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseOption;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linbinxiang
 * @date 2021-8-9
 */

@RestController
@Api(value = "app巡查接口", tags="app巡查接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/app/inspection",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppInspectionApi {

    @Resource
    private PlanDetailService planDetailService;

    @Resource
    private InspectionDiseaseService diseaseService;

    @Resource
    private InspectionDiseaseDao inspectionDiseaseDao;

    @Resource
    private InspectionDiseaseOptionDao inspectionDiseaseOptionDao;

    @Resource
    private PhotoService photoService;

    @Resource
    private AppInspectionService appInspectionService;

    @Resource
    private InspectionDiseaseExpDao inspectionDiseaseExpDao;

    @Resource
    private PlanDetailDao planDetailDao;

    @Resource
    private StructureDao structureDao;

    @PostMapping(value = "/plan/list")
    @ApiOperation(notes="获取计划列表",value="获取计划列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanFilter")
    })
    public List<PlanVO> listPlan(@RequestBody PlanFilter filter) {
        filter.setType(1);
        return appInspectionService.listPlan(filter);
    }

    @PostMapping(value = "/plan/detail/list")
    @ApiOperation(notes="获取巡查计划详情列表",value="获取巡查计划详情列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanDetailFilter")
    })
    public PageInfo<PlanDetailVO> listPlanDetail(@RequestParam(value = "pageNum") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize,
                                                 @RequestBody PlanDetailFilter filter) {
        return planDetailService.listPlanDetail(pageNum, pageSize, filter,true);
    }

    @PostMapping(value = "/plan/detail/update")
    @ApiOperation(notes = "修改巡查计划细项",value = "修改巡查计划细项",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "PlanDetailVO类json", required = true, paramType = "form", dataType = "String")
    })
    public void updPlanDetail(@RequestParam("params") String params,
                              @RequestParam("files") MultipartFile[] files, MultipartRequest request){
        PlanDetailVO planDetailVO = JSONObject.parseObject(params,PlanDetailVO.class);
        ArrayList<MultipartFile> images=new ArrayList<>();
        for(int i=0;i<planDetailVO.getPhotoList().size();i++){
            MultipartFile getFile=null;
            if((getFile=request.getFile(planDetailVO.getPhotoList().get(i).getName()))!=null) {
                images.add(getFile);
            }
        }
        appInspectionService.updPlanDetail(planDetailVO,images);
//        appInspectionService.updPlanDetail(planDetailVO,files);
    }

    @PostMapping(value = "/disease/list/{planDetailId}")
    @ApiOperation(notes="获取病害列表",value="获取病害列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "计划详情id", required = true, paramType = "path", dataType = "integer")
    })
    public List<InspectionDiseaseInstanceVO> listDisease(@PathVariable("planDetailId") Integer planDetailId) {
        return diseaseService.listDisease(planDetailId,null,null,null);
    }

    @PostMapping(value = "/disease")
    @ApiOperation(notes="新增病害实例",value="新增病害实例",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instance", value = "新增病害实例", required = true, paramType = "body", dataType = "InspectionDiseaseInstance")
    })
    public void addDisease(@RequestBody InspectionDiseaseInstance instance) {
        appInspectionService.addDisease(instance);
    }

    @PutMapping(value = "/disease")
    @ApiOperation(notes = "修改病害实例",value = "修改病害实例",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instance", value = "修改病害实例", required = true, paramType = "body", dataType = "InspectionDiseaseInstance")
    })
    public void updDisease(@RequestBody InspectionDiseaseInstance instance) {
        appInspectionService.updDisease(instance);
    }

    @GetMapping(value = "/disease/{maintainItemId}")
    @ApiOperation(notes = "根据维养id查询单个病害实例",value = "根据维养id查询单个病害实例",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "maintainItemId", value = "维养细项id", required = true, paramType = "path", dataType = "integer")
    })
    public InspectionDiseaseInstance selDisease(@PathVariable("maintainItemId") Integer maintainItemId) {
        return appInspectionService.selDisease(maintainItemId);
    }

    @DeleteMapping(value = "/disease/{diseaseInstanceId}")
    @ApiOperation(notes = "删除单个病害实例",value = "删除单个病害实例",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseInstanceId", value = "病害实例id", required = true, paramType = "path", dataType = "integer")
    })
    public void delDisease(@PathVariable("diseaseInstanceId") Integer diseaseInstanceId) {
        appInspectionService.delDisease(diseaseInstanceId);
    }

    @GetMapping(value = "/disease/attachment/{diseaseInstanceId}")
    @ApiOperation(notes = "病害图片附件",value = "病害图片附件",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseInstanceId", value = "病害实例id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "5:日常巡检,6:修改前,7:修改中,8:修改后", required = true, paramType = "query", dataType = "integer")
    })
    public List<Photo> getAttachmentByDiseaseInstanceId(@PathVariable("diseaseInstanceId") Integer diseaseInstanceId,
                                                        @RequestParam("type") Integer type) {
        return photoService.getPhotoList(diseaseInstanceId,type);
    }

    @PostMapping(value = "/upload/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "上传文件 ", notes = "上传文件", httpMethod = "POST", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "5:日常巡检,6:修改前,7:修改中,8:修改后",
                    dataType = "Integer", paramType = "path", required = true),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "photo类json", required = true, paramType = "form", dataType = "String")
    })
    public void fileUpload(@PathVariable("type") Integer type, @RequestParam("params") String params,
                           @RequestParam("files") MultipartFile[] files) {
        Photo photo = JSONObject.parseObject(params, Photo.class);
        photo.setType(type);
        appInspectionService.uploadFiles(photo, files);
    }

    @GetMapping(value = "/inspectionDisease/{id}")
    @ApiOperation(notes = "巡检病害表单个查询",value = "巡检病害表单个查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "病害id", required = true, paramType = "path", dataType = "integer")
    })
    public InspectionDisease getInspectionDisease(@PathVariable("id") Integer id) {
        return inspectionDiseaseDao.findById(id);
    }

    @GetMapping(value = "/inspectionDisease")
    @ApiOperation(notes = "桥梁隧道巡查检查项查询",value = "桥梁隧道巡查检查项查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "计划详情id", required = true, paramType = "query", dataType = "integer")
    })
    public List<InspectionDisease> selectAllInspectionDisease(@Param("planDetailId") Integer planDetailId) {
        Structure structure=structureDao.findById(planDetailDao.findById(planDetailId).getStructureId());
        Integer bridgeType=structure.getBridgeType();
        if(structure.getStructureType()==2){
            bridgeType=0;
        }
        return inspectionDiseaseExpDao.selectInspectionDiseaseByStructureBridgeType(bridgeType);
    }


    @GetMapping(value = "/diseaseOption/{id}")
    @ApiOperation(notes = "巡检病害选项表单个查询",value = "巡检病害选项表单个查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "选项id", required = true, paramType = "path", dataType = "integer")
    })
    public InspectionDiseaseOption getDiseaseOption(@PathVariable("id") Integer id) {
        return inspectionDiseaseOptionDao.findById(id);
    }

    @GetMapping(value = "/diseaseOption")
    @ApiOperation(notes = "巡检病害选项表全部查询",value = "巡检病害选项表全部查询",httpMethod = "GET")
    public List<InspectionDiseaseOption> selectAllDiseaseOption() {
        return inspectionDiseaseOptionDao.findAll();
    }

    @GetMapping(value = "/diseaseOption/disease/{diseaseId}")
    @ApiOperation(notes = "巡检病害对应选项",value = "巡检病害对应选项",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "病害id", required = true, paramType = "path", dataType = "integer")
    })
    public List<InspectionDiseaseOption> selectOptionByDiseaseId(@PathVariable("diseaseId") Integer diseaseId) {
        InspectionDiseaseOption condition=new InspectionDiseaseOption();
        condition.setInspectionDiseaseId(diseaseId);
        return inspectionDiseaseOptionDao.query(condition);
    }

    @GetMapping(value = "/detail/last/id/{planDetailId}")
    @ApiOperation(notes = "获取前一次巡查记录",value = "获取前一次巡查记录",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "当次巡查计划id", required = true, paramType = "path", dataType = "integer")
    })
    public PlanDetailVO selectLastInspection(@PathVariable("planDetailId") Integer planDetailId) {
        return appInspectionService.selectLastInspection(planDetailId);
    }

}
