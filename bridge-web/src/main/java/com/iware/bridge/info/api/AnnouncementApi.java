package com.iware.bridge.info.api;


import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.AnnouncementService;
import com.iware.bridge.info.vo.AnnouncementNotice;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.global.Announcement;
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
 * @date 2021-7-5
 */

@RestController
@Api(value = "公告管理", tags="公告管理",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/announcement",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AnnouncementApi implements AbstractBaseController {

	@Autowired
	private AnnouncementService announcementService;

	@GetMapping(value = "/notice/list")
	@ApiOperation(notes="首页获取公告消息列表",value="首页获取公告消息列表",httpMethod="GET")
	@ApiImplicitParams({})
	public AnnouncementNotice getNoticeList() {
		return announcementService.getNoticeList();
	}

	@PutMapping(value = "/notice/{announcementId}/{status}")
	@ApiOperation(notes="",value="修改公告读取状态",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "announcementId", value = "公告id", dataType = "integer", paramType = "path", required = true),
			@ApiImplicitParam(name = "status", value = "状态 0:未读 1:已读", dataType = "integer", paramType = "path", required = true),
	})
	public void updateNoticeStatus(@PathVariable("announcementId") Integer announcementId,
											@PathVariable("status") Integer status) {
		announcementService.updateNoticeStatus(announcementId, status);
	}

	@PostMapping(value = "/list")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="获取公告列表",value="获取公告列表",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
			@ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "InfoFilter")
	})
	public PageInfo<Announcement> listAnnouncement(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
													  @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
													  @RequestBody InfoFilter filter) {
		return announcementService.listAnnouncement(pageNum, pageSize, filter);
	}

	@PostMapping(value = "")
	@CheckRepeat
	@Permission(actionType = ActionTypeEnum.ACTION_ADD)
	@ApiOperation(notes="新增一条公告",value="新增一条公告",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "announcement", value = "公告", required = true, paramType = "body", dataType = "Announcement")
	})
	public void addAnnouncement(@RequestBody Announcement announcement) {

		announcementService.addAnnouncement(announcement);
	}

	@PutMapping(value = "/{announcementId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="修改一条公告",value="修改一条公告",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "announcementId", value = "公告id", required = true, paramType = "path", dataType = "integer"),
			@ApiImplicitParam(name = "announcement", value = "公告", required = true, paramType = "body", dataType = "Announcement")
	})
	public void updAnnouncement(@PathVariable("announcementId") Integer announcementId,
			@RequestBody Announcement announcement) {
		announcement.setId(announcementId);
		announcementService.updAnnouncement(announcement);
	}

	@PutMapping(value = "/publish/{announcementId}/{status}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="修改发布状态",value="修改发布状态",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "announcementId", value = "公告id", required = true, paramType = "path", dataType = "integer"),
			@ApiImplicitParam(name = "status", value = "1：发布，0：下架", required = true, paramType = "path", dataType = "integer")
	})
	public void publish(@PathVariable("announcementId") Integer announcementId,
						@PathVariable("status") Integer status) {

		announcementService.publish(announcementId, status);
	}

	@DeleteMapping(value = "/{announcementId}")
	@Permission(actionType = ActionTypeEnum.ACTION_DEL)
	@ApiOperation(notes="删除一条公告",value="删除一条公告",httpMethod="DELETE")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "announcementId", value = "公告id", required = true, paramType = "path", dataType = "integer")
	})
	public void delAnnouncement(@PathVariable("announcementId") Integer announcementId) {
		announcementService.deleteById(announcementId);
	}

}
