import request from '@/utils/request';

//获取计划列表
export function getPlanList(data) {
  return request({
    url: `/inspection/plan/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//新增巡查计划
export function addPlan(data) {
  return request({
    url: `/inspection/plan`,
    method: 'post',
    data
  });
}

//修改巡查计划
export function updPlan(data) {
  return request({
    url: `/inspection/plan/${data.id}`,
    method: 'put',
    data
  });
}

//删除巡查计划
export function delPlan(id) {
  return request({
    url: `/inspection/plan/${id}`,
    method: 'delete'
  });
}

//获取巡查计划详情列表
export function getPlanDetailList(data) {
  return request({
    url: `/inspection/plan/detail/list?pageSize=${data.pageSize}&pageNum=1`,
    method: 'post',
    data
  });
}

//新增巡查计划细项
export function addPlanDetail(data) {
  return request({
    url: `/inspection/plan/detail`,
    method: 'post',
    data
  });
}

//修改巡查计划细项
export function updPlanDetail(data) {
  return request({
    url: `/inspection/plan/detail`,
    method: 'put',
    data
  });
}

//删除巡查计划细项
export function delPlanDetail(id) {
  return request({
    url: `/inspection/plan/detail/${id}`,
    method: 'delete'
  });
}

//查询单个巡查计划细项
export function getOnePlanDetail(id) {
  return request({
    url: `/inspection/plan/detail/${id}`,
    method: 'get'
  });
}
//获取当月首次巡查时间
export function getfirstDate(data) {
  return request({
    url: `/inspection/plan/firstDate`,
    method: 'post',
    data
  });
}

//已完成修改金额
export function updatePlanAmount(data) {
  return request({
    url: `/inspection/plan/updatePlanAmount`,
    method: 'put',
    data
  });
}
