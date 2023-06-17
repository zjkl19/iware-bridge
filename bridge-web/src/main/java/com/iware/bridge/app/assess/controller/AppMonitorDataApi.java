package com.iware.bridge.app.assess.controller;

import com.iware.bridge.app.assess.dao.AppOfflineExpDao;
import com.iware.bridge.app.assess.service.AppMonitorFileService;
import com.iware.bridge.app.assess.service.AppPropertiesService;
import com.iware.bridge.app.assess.service.AppUnzipService;
import com.iware.bridge.app.assess.service.AppZipService;
import com.iware.bridge.model.dao.evaluation.MonitorPlanStructureRelDao;
import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;
import com.iware.common.exception.BusinessException;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuqiang
 */
@Api(value = "APP端计划数据相关", tags = "APP端计划数据相关")
@RestController
@RequestMapping(value = "/app/monitordata")
public class AppMonitorDataApi {

	@Autowired
	private AppPropertiesService appPropertyServ;

	@Autowired
	private AppZipService appZipService;

	@Autowired
	private AppUnzipService appUnzipService;

	@Value("${file.upload-path}")
	private String path;

	@Resource
	private AppMonitorFileService appMonitorFileServ;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Resource
	private AppOfflineExpDao appOfflineExpDao;

	@Resource
	private MonitorPlanStructureRelDao monitorPlanStructureRelDao;

	/**
	 * @author zhuqiang
	 */
	@GetMapping(value = "/downloadProperty")
    @ApiOperation(value = "下载属性配置相关压缩文件 ", notes = "下载属性配置相关压缩文件", httpMethod = "GET")
    @ApiImplicitParams({
    })
	public ResultBody downloadPropertyData() {
		appPropertyServ.setPropertyPath(path+"/tmp/property");
		String zipPath = appPropertyServ.runBuild();
		return ResultUtil.success(zipPath.replace(path,"/bridge/static"));

	}


	@GetMapping(value = "/downloadOfflineData/{id}")
	@ApiOperation(value = "下载项目离线文件 ", notes = "下载项目离线文件", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", dataType = "int64", paramType = "path",defaultValue="1",required=true),

	})
	public ResultBody downloadOfflineData(@PathVariable("id") int planId){
		String fileZipPath=appZipService.getPlanZip(planId,path+"/tmp/offlineFile",false);
		return ResultUtil.success(fileZipPath.replace(path,"/bridge/static"));
	}

	@PostMapping(value = "/uploadPartfile")
    @ApiOperation(value = "上传数据文件", notes = "上传数据分片文件", httpMethod = "POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "monitorId", value = "检测计划id", dataType = "int", paramType = "form")
    })

	public ResultBody uploadMinitorPartFile(@RequestParam("file") MultipartFile file,@RequestParam("monitorId") Integer monitorId) {
		Boolean flag=getLock("unzip:"+monitorId); //设置redis并发锁，时间1分钟
		if(!flag){
			throw new BusinessException("数据更新中，请稍后");
		}else{
			redisTemplate.expire("unzip:"+monitorId,(long)1*60, TimeUnit.SECONDS);
		}
		appMonitorFileServ.uploadPartFile(file, monitorId);
		return ResultUtil.success("上传成功");
	}

	@PostMapping(value = "/mergefile")
    @ApiOperation(value = "合并上传数据文件", notes = "合并上传数据文件", httpMethod = "POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "monitorId", value = "检测计划id", dataType = "int", paramType = "form"),
    	@ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String", paramType = "form")
    })
	public ResultBody mergeMinitorFile(@RequestParam("monitorId") Integer monitorId,@RequestParam("fileName") String fileName) {
		String filePath = appMonitorFileServ.mergePartFile(monitorId, fileName);
		Logger logger= LoggerFactory.getLogger(AppMonitorDataApi.class);
		logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"mergeMinitorFile:"+"monitorId:"+monitorId+
				",fileName:"+fileName);
		/**********下面为文件数据导入数据库相关操作的内容************/
		File file = new File(filePath);
		String zipFilePath=file.getParentFile().getPath();
		String lastFilePath=path+"/tmp/offlineFile/"+monitorId+"/";
		String imageFilePath=path+"/tmp/imageSave";
		List<MonitorPlanStructureRel> result=appUnzipService.correctDatabase(zipFilePath,file.getName().replace(".zip",""),lastFilePath,imageFilePath);
		return ResultUtil.success("上传成功",result);
	}

	@GetMapping("/testUnzip")
	public ResultBody UnzipFileAndCorrect(int planId){
		String zipFilePath=path+"/tmp/monitor/file/"+planId;
		String lastFilePath=path+"/tmp/offlineFile/"+planId+"/";
		String imageFilePath=path+"/tmp/imageSave";
		List<MonitorPlanStructureRel> result=appUnzipService.correctDatabase(zipFilePath,""+planId,lastFilePath,imageFilePath);
		return ResultUtil.success("上传成功",result);
	}

	@GetMapping("/testResortDiseaseInstance/{monitorPlanId}")
	@ApiOperation(value = "测试重排序检测病害", notes = "测试重排序检测病害", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "monitorPlanId", value = "检测计划id", dataType = "int", paramType = "path")
	})
	public void testResortDiseaseInstance(@PathVariable("monitorPlanId") Integer monitorPlanId){
		MonitorPlanStructureRel condition=new MonitorPlanStructureRel();
		condition.setMonitorPlanId(monitorPlanId);
		for(MonitorPlanStructureRel rel:monitorPlanStructureRelDao.query(condition)){
			appOfflineExpDao.updateDiseaseInstanceSortByModifyTime(rel.getId());
		}
	}

	public boolean getLock(String lockId) {
		Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, "lock");
		return success != null && success;
	}

	public long getExpire(String lockId){
		return redisTemplate.getExpire(lockId);
	}

	public void releaseLock(String lockId) {
		redisTemplate.delete(lockId);
	}

}
