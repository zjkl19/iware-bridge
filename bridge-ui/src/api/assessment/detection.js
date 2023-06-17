import request from '@/utils/request';

//构件病害数排行
export function getDiseaseByComponent(structureId, componentId) {
  return request({
    url: `/evaluation/monitor/getDiseaseByComponent/${structureId}/${componentId}`,
    method: 'get'
  });
}

//检测概况
export function getEvaluationInfo(structureId) {
  return request({
    url: `/evaluation/monitor/getEvaluationInfo/${structureId}`,
    method: 'get'
  });
}

//获取桥梁最近一次评分情况
export function getNewestScore(structureId) {
  return request({
    url: `/evaluation/monitor/getNewestScore/${structureId}`,
    method: 'get'
  });
}

//获取桥梁最近一次评分情况
export function getScoreTrend(structureId, partType) {
  return request({
    url: `/evaluation/monitor/getScoreTrend/${structureId}/${partType}`,
    method: 'get'
  });
}

//查询基本信息和图片
export function getBasic(structureId) {
  return request({
    url: `/evaluation/monitor/getBasic/${structureId}`,
    method: 'get'
  });
}

//查询构件列表
export function getComponent() {
  return request({
    url: `/evaluation/monitor/getComponent`,
    method: 'get'
  });
}
