import request from '@/utils/request';

//查询监测桥隧数/传感器数/摄像头/当日预警数/较昨日预警变化
export function getCount() {
  return request({
    url: `/online/overview/getCount`,
    method: 'get'
  });
}

//预警统计
export function getStatistics(type) {
  return request({
    url: `/online/warning/statistics/${type}`,
    method: 'get'
  });
}

//监测评分排行
export function getScoreRank(id) {
  return request({
    url: `/online/overview/scoreRank/${id}`,
    method: 'get'
  });
}

//传感器统计
export function getSensorList() {
  return request({
    url: `/online/overview/getSensorTypeList`,
    method: 'get'
  });
}
