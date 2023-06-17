import request from '@/utils/request';

//获取计划列表
export function getPlanList(data) {
  return request({
    url: `/maintain/plan/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//新增维养计划
export function addPlan(data) {
  return request({
    url: `/maintain/plan`,
    method: 'post',
    data
  });
}

//修改维养计划
export function updPlan(data) {
  return request({
    url: `/maintain/plan/${data.id}`,
    method: 'put',
    data
  });
}

//删除维养计划
export function delPlan(id) {
  return request({
    url: `/maintain/plan/${id}`,
    method: 'delete'
  });
}

//获取维养计划详情列表
export function getPlanDetailList(data) {
  return request({
    url: `/maintain/plan/detail/list?pageSize=1000&pageNum=1`,
    method: 'post',
    data
  });
}

//获取桥梁最后一次巡查病害
export function getLastInspectionDisease(data) {
  return request({
    url: `/maintain/plan/inspectionDisease/structure/${data.id}`,
    method: 'post',
    data
  });
}

//导出维养计划
export function exportPlan(id) {
  return request({
    url: `/maintain/plan/download/${id}`,
    method: 'get',
    responseType: 'blob'
  });
}

//获取指派时间段
export function getreceiveTime(projectId, isInspection) {
  return request({
    url: `/maintain/plan/receiveTime/${projectId}/${isInspection}`,
    method: 'get'
  });
}

//删除单条细项
export function delOneDatail(planDetailId) {
  return request({
    url: `/maintain/plan/detail/${planDetailId}`,
    method: 'delete'
  });
}
//添加单条细项
export function addOneDatail(data) {
  return request({
    url: `/maintain/plan/detail`,
    method: 'post',
    data
  });
}

//移除单条细项
export function removeDisease(id) {
  return request({
    url: `/maintain/plan/disease/update/${id}`,
    method: 'put'
  });
}
