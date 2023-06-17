import request from '@/utils/request';

//获取综合评价指数
export function getComprehensive(id) {
  return request({
    url: `/home/getComprehensive/${id}`,
    method: 'get'
  });
}

//获取历史检测维养
export function getMainEvaHistory(id) {
  return request({
    url: `/home/detail/getMainEvaHistory/${id}`,
    method: 'get'
  });
}

//获取监测概况
export function getSensorInfo(id) {
  return request({
    url: `/home/detail/getSensorInfo/${id}`,
    method: 'get'
  });
}

//获取检测概况
export function getEvaluationInfo(id) {
  return request({
    url: `/home/detail/getEvaluationInfo/${id}`,
    method: 'get'
  });
}

//获取巡查维养概况
export function getMaintainInfo(id) {
  return request({
    url: `/home/detail/getMaintainInfo/${id}`,
    method: 'get'
  });
}
