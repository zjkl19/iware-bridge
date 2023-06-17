import request from '@/utils/request';

//查询结构物在线、离线传感器数/今日预警数/今日待处理
export function getCount(id) {
  return request({
    url: `/online/monitor/getCount/${id}`,
    method: 'get'
  });
}

//传感器类型
export function getSensorList(id) {
  return request({
    url: `/online/monitor/sensorList/${id}`,
    method: 'get'
  });
}

//预警统计
export function getStatistics(id, type) {
  return request({
    url: `/online/warning/statistics/${id}/${type}`,
    method: 'get'
  });
}

//测点预警排行
export function getWarningRank(id, type) {
  return request({
    url: `/online/monitor/warningRank/${id}/${type}`,
    method: 'get'
  });
}

//预警变化趋势
export function getWarningTend(type, id) {
  return request({
    url: `/online/monitor/warningTend/${type}/${id}`,
    method: 'get'
  });
}
