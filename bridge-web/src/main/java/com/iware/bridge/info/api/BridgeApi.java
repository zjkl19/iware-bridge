package com.iware.bridge.info.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.vo.MonitorRecord;
import com.iware.bridge.info.service.BridgeService;
import com.iware.bridge.info.vo.BridgeTunnelDetailVO;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.inspection.vo.PlanDetailVO;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.base.AbstractBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-5
 */

@RestController
@Api(value = "桥梁信息管理", tags="桥梁信息管理",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/bridgeManage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BridgeApi implements AbstractBaseController {

	@Autowired
	private BridgeService bridgeService;

	@PostMapping(value = "/list")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="查询用户可看桥梁",value="查询用户可看桥梁",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1"),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10"),
			@ApiImplicitParam(name = "infoFilter", value = "过滤条件", required = true, paramType = "body", dataType = "InfoFilter")
	})
	public PageInfo<Structure> getBridgeList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
											 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
											 @RequestBody InfoFilter infoFilter) {
		return bridgeService.getBridgeList(pageNum, pageSize, infoFilter);
	}

	@GetMapping(value = "/listByProjectId/{projectId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="根据项目id查询桥梁",value="根据项目id查询桥梁",httpMethod="GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "path", dataType = "Integer")
	})
	public List<Structure> getBridgeListByProjectId(@PathVariable("projectId") Integer projectId) {
		return bridgeService.getBridgeListByProjectId(projectId);
	}

	@GetMapping(value = "/detail/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "获取桥梁详情 ", notes = "获取桥梁详情 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "桥梁结构物id", dataType = "int", paramType = "path"),
	})
	public BridgeTunnelDetailVO getBridgeDetail(@PathVariable("structureId") Integer structureId) {
		return bridgeService.getBridgeDetail(structureId);

	}

	@PostMapping(value = "")
	@CheckRepeat
	@Permission(actionType = ActionTypeEnum.ACTION_ADD)
	@ApiOperation(notes="新增一条桥梁的基础信息",value="新增一条桥梁的基础信息",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structure", value = "结构物信息", required = true, paramType = "body", dataType = "Structure")
	})
	public void addBridge(@RequestBody Structure structure) {
		bridgeService.addBridge(structure);
	}

	@PutMapping(value = "/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="修改一条桥梁信息",value="修改一条桥梁信息",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "structure", value = "结构物信息", required = true, paramType = "body", dataType = "Structure")
	})
	public void updateBridge(@PathVariable("structureId") Integer structureId,
								  @RequestBody Structure structure) {
		structure.setId(structureId);
		bridgeService.updateBridge(structure);
	}

	@PutMapping(value = "/detail/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="修改一条桥梁详细信息",value="修改一条桥梁详细信息",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "detailVO", value = "结构物信息", required = true, paramType = "body", dataType = "BridgeTunnelDetailVO")
	})
	public void updateBridgeDetail(@PathVariable("structureId") Integer structureId,
								   @RequestBody BridgeTunnelDetailVO detailVO) {
		detailVO.setId(structureId);
		bridgeService.updateBridgeDetail(detailVO);
	}

	@DeleteMapping(value = "/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_DEL)
	@ApiOperation(notes="删除一条桥梁的所有关联信息",value="删除一条桥梁的所有关联信息",httpMethod="DELETE")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "int"),
	})
	public void deleteBridge(@PathVariable("structureId") Integer structureId) {
		bridgeService.deleteBridge(structureId);
	}

	@GetMapping(value = "/getAnnexList/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "查询所有附件 ", notes = "查询所有附件 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "对应表的主键id", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1"),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10"),
	})
	public PageInfo<Photo> getAnnexList(@PathVariable("structureId") Integer structureId,
										@RequestParam("pageNum") Integer pageNum,
										@RequestParam("pageSize") Integer pageSize) {
		return bridgeService.getAnnexList(structureId, pageNum, pageSize);
	}

	@GetMapping(value = "/monitorRecord/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "详情页获取检测计划记录 ", notes = "详情页获取检测计划记录 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
	})
	public PageInfo<MonitorRecord> getMonitorRecordList(@PathVariable("structureId") Integer structureId,
														@RequestParam(value = "pageNum") Integer pageNum,
														@RequestParam(value = "pageSize") Integer pageSize) {

		return bridgeService.getMonitorRecord(pageNum, pageSize, structureId);
	}

	@GetMapping(value = "/inspectionRecord/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "获取日常巡查记录 ", notes = "获取桥梁日常巡查记录 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", required = true),
	})
	public PageInfo<PlanDetailVO> getInspectionDailyList(@PathVariable("structureId") Integer structureId,
														 @RequestParam(value = "pageNum") Integer pageNum,
														 @RequestParam(value = "pageSize") Integer pageSize) {
		return bridgeService.getInspectionRecordList(pageNum, pageSize, structureId);

	}

	@GetMapping(value = "/maintainRecord/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "获取维修养护记录 ", notes = "获取维修养护记录 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", dataType = "structureId", paramType = "path", required = true),
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", required = true),
	})
	public PageInfo<MaintainItemVO> getMaintainRecordList(@PathVariable("structureId") Integer structureId,
														  @RequestParam(value = "pageNum") Integer pageNum,
														  @RequestParam(value = "pageSize") Integer pageSize) {

		return bridgeService.getMaintainRecordList(pageNum, pageSize, structureId);
	}

}
