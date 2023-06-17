import request from '@/utils/request';

//获取计划列表
export function getPlanList(data) {
  return request({
    url: `/evaluation/plan/list?pageSize=10&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//添加计划
export function addPlan(data) {
  return request({
    url: `/evaluation/plan`,
    method: 'post',
    data
  });
}

//修改计划
export function updPlan(data, id) {
  return request({
    url: `/evaluation/plan/${id}`,
    method: 'put',
    data
  });
}

//删除计划
export function delPlan(id) {
  return request({
    url: `/evaluation/plan/${id}`,
    method: 'delete'
  });
}

//获取计划详情
export function getPlanStructure(id) {
  return request({
    url: `/evaluation/plan/getStructureRel/${id}`,
    method: 'get'
  });
}

//下载
export function downloadPlan(id) {
  return request({
    url: `/evaluation/plan/download/${id}`,
    responseType: 'blob',
    method: 'get'
  });
}

//获取项目的指派时间
export function getAppointTime(projectId) {
  return request({
    url: `/evaluation/plan/getAppointTime/${projectId}`,
    method: 'get'
  });
}
