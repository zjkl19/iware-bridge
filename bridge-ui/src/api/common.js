import request from '@/utils/request';

/* 通用api接口 */

//获取项目列表（全部）
export function getProjectList() {
  return request({
    url: `/common/project/listByUser`,
    method: 'get'
  });
}

//根据id获取结构物(type 0：不要图片视频、1：要图片视频、2：要图片视频和基本信息)
export function getStructureById(structureId, type) {
  return request({
    url: `/common/getStructureById/${structureId}/${type}`,
    method: 'get'
  });
}

//获取项目列表（根据需求获取）
export function getProjectListByPowerId(powerId) {
  return request({
    url: `/common/project/listByUser/${powerId}`,
    method: 'get'
  });
}

//获取用户有传感器/巡查/维养/检测计划的项目
export function getProjectListByModel(powerId) {
  return request({
    url: `/common/project/listByModel/${powerId}`,
    method: 'get'
  });
}

//获取用户在指派之间内的的项目
export function getProjectListByOnTime(powerId) {
  return request({
    url: `/common/project/listByOnTime/${powerId}`,
    method: 'get'
  });
}

//获取结构物列表
export function getStructureList(data) {
  return request({
    url: `/common/structure/listByUnit`,
    method: 'post',
    data
  });
}

//获取用户有传感器/巡查/维养/检测计划的桥隧
export function getStructureListByModel(data) {
  return request({
    url: `/common/structure/listByModel`,
    method: 'post',
    data
  });
}

//获取报表列表
export function getReportList(data) {
  return request({
    url: `/report/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//添加报告
export function addReport(data, type) {
  return request({
    url: `/report/${type}`,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    method: 'post',
    data
  });
}

//修改报告
export function updReport(data, id) {
  return request({
    url: `/report/${id}`,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    method: 'put',
    data
  });
}

//删除报告
export function delReport(id) {
  return request({
    url: `/report/${id}`,
    method: 'delete'
  });
}

//下载报告
export function downLoadReport(id) {
  return request({
    url: `/report/download/${id}`,
    responseType: 'blob',
    method: 'get'
  });
}

// 获取图片列表
export function listByPage(pageInfo) {
  return request({
    url: `/photo/list/${pageInfo.id}/${pageInfo.type}`,
    method: 'get',
    params: {
      pageNum: pageInfo.pageNum,
      pageSize: pageInfo.pageSize
    }
  });
}

//根据用户角色和模块获取用户列表(首页)
export function getListByRoleHome(roleId) {
  return request({
    url: `/common/unit/listByRoleAndPower/${roleId}`,
    method: 'get'
  });
}

//根据用户角色和模块获取用户列表
export function getListByRoleAndPower(roleId, powerId) {
  return request({
    url: `/common/unit/listByRoleAndPower/${roleId}/${powerId}`,
    method: 'get'
  });
}

//查询用户结构物省市区
export function getArea() {
  return request({
    url: `/common/area/listByUser`,
    method: 'get'
  });
}

//查询用户结构物省市区
export function getAreaByPowerId(powerId) {
  return request({
    url: `/common/area/listByUser/${powerId}`,
    method: 'get'
  });
}

//获取检测评估桥梁类型
export function getBridgeType() {
  return request({
    url: `/common/getBridgeType`,
    method: 'get'
  });
}
