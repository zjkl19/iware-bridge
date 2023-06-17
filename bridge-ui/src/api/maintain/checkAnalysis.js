import request from '@/utils/request';

//项目近五年维修费用变化趋势
export function getAccountTrend(id) {
  return request({
    url: `/maintain/analyse/accountTrend/${id}`,
    method: 'get'
  });
}

//近五年维修趋势
export function getRepairTrend(id) {
  return request({
    url: `/maintain/analyse/repairTrend/${id}`,
    method: 'get'
  });
}

//重复维修项占比及周期分析
export function getItemRepeatAnalyse(id) {
  return request({
    url: `/maintain/analyse/itemRepeatAnalyse/${id}`,
    method: 'get'
  });
}

//构件病害报修情况
export function getMaintainRatio(id) {
  return request({
    url: `/maintain/analyse/maintainRatio/${id}`,
    method: 'get'
  });
}

//近五年维修项目统计
export function getMaintainItemStatistics(id) {
  return request({
    url: `/maintain/analyse/maintainItemStatistics/${id}`,
    method: 'get'
  });
}
