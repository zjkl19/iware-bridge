package com.iware.bridge.info.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.TunnelService;
import com.iware.bridge.info.vo.BridgeTunnelDetailVO;
import com.iware.bridge.info.vo.InfoFilter;
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

/**
 * @author ZRB
 * @date 2021-7-6
 */

@RestController
@Api(value = "隧道信息管理", tags="隧道信息管理",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/tunnel",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TunnelApi implements AbstractBaseController {

	@Autowired
	private TunnelService tunnelService;

	@PostMapping(value = "/list")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="查询用户可看隧道列表",value="查询用户可看隧道列表",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "infoFilter", value = "过滤条件", required = true, paramType = "body", dataType = "InfoFilter")
	})
	public PageInfo<Structure> getTunnelList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
											 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
											 @RequestBody InfoFilter infoFilter) {
		return tunnelService.getTunnelList(pageNum, pageSize, infoFilter);
	}

	@PostMapping(value = "")
	@CheckRepeat
	@Permission(actionType = ActionTypeEnum.ACTION_ADD)
	@ApiOperation(notes="新增一条隧道的基础信息",value="隧道的基础信息",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structure", value = "结构物信息", required = true, paramType = "body", dataType = "Structure")
	})
	public void addTunnel(@RequestBody Structure structure) {
		tunnelService.addTunnel(structure);
	}

	@PutMapping(value = "/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="修改一条隧道信息",value="修改一条隧道信息",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "structure", value = "结构物信息", required = true, paramType = "body", dataType = "Structure")
	})
	public void updateTunnel(@PathVariable("structureId") Integer structureId,
							 @RequestBody Structure structure) {
		structure.setId(structureId);
		tunnelService.updateTunnel(structure);
	}

	@PutMapping(value = "/detail/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="修改一条隧道详细信息",value="修改一条隧道详细信息",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "detailVO", value = "结构物信息", required = true, paramType = "body", dataType = "BridgeTunnelDetailVO")
	})
	public void updateTunnelDetail(@PathVariable("structureId") Integer structureId,
								   @RequestBody BridgeTunnelDetailVO detailVO) {
		detailVO.setId(structureId);
		tunnelService.updateTunnelDetail(detailVO);
	}

	@DeleteMapping(value = "/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_DEL)
	@ApiOperation(notes="删除一条隧道的所有关联信息",value="删除一条隧道的所有关联信息",httpMethod="DELETE")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "int"),
	})
	public void delTunnel(@PathVariable("structureId") Integer structureId) {
		tunnelService.delTunnel(structureId);
	}

	@GetMapping(value = "/detail/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "获取隧道详情 ", notes = "获取隧道详情 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "结构物id", dataType = "int", paramType = "path"),
	})
	public BridgeTunnelDetailVO getTunnelDetail(@PathVariable("structureId") Integer structureId) {
		return tunnelService.getTunnelDetail(structureId);
	}

	@GetMapping(value = "/getAnnexList/{structureId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "查询所有附件 ", notes = "查询所有附件 ", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "structureId", value = "对应表的主键id", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
	})
	public PageInfo<Photo> getAnnexList(@PathVariable("structureId") Integer structureId,
										@RequestParam("pageNum") Integer pageNum,
										@RequestParam("pageSize") Integer pageSize) {
		return tunnelService.getAnnexList(structureId, pageNum, pageSize);
	}
}
