package com.iware.bridge.info.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.ProjectService;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.ProjectAppointVO;
import com.iware.bridge.info.vo.ProjectVO;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.user.Power;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
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
 * @date 2021-7-6
 */

@RestController
@Api (value = "项目管理接口", tags = "项目管理接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping (value="/project",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProjectApi {

    @Autowired
    private ProjectService projectService;

    @GetMapping (value = "/listStructureByUnit/{unitId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="获取单位结构物",value="获取单位结构物",httpMethod="GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "unitId", value = "单位id", dataType = "Integer", paramType = "body", required = true)
	})
    public List<Structure> listStructureByUnit(@PathVariable Integer unitId) {
        return projectService.listStructureByUnit(unitId);
    }

    @PostMapping (value = "/list")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="查询项目列表",value="查询项目列表",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query",  required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "filter", value = "过滤条件", required = true, paramType = "body", dataType = "InfoFilter")
	})
    public PageInfo<ProjectVO> listProjectByPage (@RequestParam(value = "pageNum") Integer pageNum,
												  @RequestParam(value = "pageSize") Integer pageSize,
												  @RequestBody InfoFilter filter) {
    	return projectService.listProjectByPage(pageNum, pageSize, filter);
    }

    @PostMapping (value = "")
	@CheckRepeat
	@Permission(actionType = ActionTypeEnum.ACTION_ADD)
	@ApiOperation(notes="添加项目",value="添加项目",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectVO", value = "项目", required = true, paramType = "body", dataType = "ProjectVO")
	})
    public void addProject(@RequestBody ProjectVO projectVO) {
        projectService.addProject(projectVO);
    }

	@PutMapping (value = "/{projectId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(notes="编辑项目",value="编辑项目",httpMethod="PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectVO", value = "项目", required = true, paramType = "body", dataType = "ProjectVO")
	})
	public void updateProject(@PathVariable("projectId")Integer projectId,
                              @RequestBody ProjectVO projectVO) {
		projectVO.setId(projectId);
		projectService.updateProject(projectVO);
	}

    @DeleteMapping (value = "/{projectId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
	@ApiOperation(notes="删除项目",value="删除项目",httpMethod="DELETE")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "path", dataType = "Integer")
	})
    public void deleteProject(@PathVariable("projectId")Integer projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping(value = "/appoint/list/{projectId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="查询用户当前项目指派信息",value="查询用户当前项目指派信息",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, paramType = "path", dataType = "Integer")
    })
    public PageInfo<ProjectAppointVO> getAppointList(@RequestParam(value = "pageNum") Integer pageNum,
													 @RequestParam(value = "pageSize") Integer pageSize,
													 @PathVariable("projectId") Integer projectId) {
        return projectService.getAppointList(projectId, pageNum, pageSize);
    }

	@PostMapping(value = "/appoint")
	@CheckRepeat
	@Permission(actionType = ActionTypeEnum.ACTION_APT)
    @ApiOperation(notes="新增一条指派记录",value="新增一条指派记录",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointVO", value = "指派信息", dataType = "ProjectAppointVO", paramType = "data"),
    })
	public void addAppoint(@RequestBody ProjectAppointVO appointVO) {
		projectService.addAppoint(appointVO);
	}

    @DeleteMapping (value = "/appoint/{appointId}")
    @Permission(actionType = ActionTypeEnum.ACTION_APT)
    @ApiOperation(notes="删除一条指派记录",value="删除一条指派记录",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointId", value = "指派记录id", dataType = "Integer", paramType = "path"),
    })
    public void delAppoint(@PathVariable("appointId") Integer appointId) {
        projectService.delAppoint(appointId);
    }

	@GetMapping(value = {"/getBusiness", "/getBusiness/{projectId}"})
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="获取业务",value="获取业务",httpMethod="GET")
	public List<Power> getBusiness(@PathVariable(value = "projectId", required = false) Integer projectId) {
		return projectService.getBusiness(projectId);
	}

}
