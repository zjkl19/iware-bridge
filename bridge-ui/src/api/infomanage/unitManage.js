import request from '@/utils/request';

//添加单位
export function addUnit(data) {
  return request({
    url: '/unit',
    method: 'post',
    data
  });
}

//修改单位基础信息
export function editUnit(data) {
  return request({
    url: `/unit/${data.id}`,
    method: 'put',
    data
  });
}

//删除单位
export function delUnit(unitId) {
  return request({
    url: `/unit/${unitId}`,
    method: 'delete'
  });
}

//查询单位列表
export function getUnitList(data) {
  return request({
    url: `/unit/list?pageNum=${data.pageNum}&pageSize=${data.pageSize}`,
    method: 'post',
    data
  });
}

//获取权限勾选列表
export function unitPowerList() {
  return request({
    url: '/power/list',
    method: 'get'
  });
}

//获取要编辑单位权限
export function getUnitPowers(userId) {
  return request({
    url: `/power/${userId}`,
    method: 'get'
  });
}

//查询单位可选择的角色
export function getRoleDefaultPower(type) {
  return request({
    url: `/power/getRoleDefaultPower/${type}`,
    method: 'get'
  });
}

//根据单位角色获取单位
export function getUnitByRole(roleId, parentId = 0) {
  return request({
    url: `/unit/getUnitByRole/${roleId}/${parentId}`,
    method: 'get'
  });
}

//获取页面模块ids
export function getPagePowerIds() {
  return request({
    url: `/power/getPagePowerIds`,
    method: 'get'
  });
}
