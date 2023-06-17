import request from '@/utils/request';

//构件病害数排行榜
export function getCompDiseaseRank(id) {
  return request({
    url: `/inspection/analyse/compDiseaseRank/${id}`,
    method: 'get'
  });
}

//构件病害月频率
export function getDiseaseMonthFrequency(id, month) {
  return request({
    url: `/inspection/analyse/diseaseMonthFrequency/${id}/${month}`,
    method: 'get'
  });
}

//构件病害报修情况
export function getDiseaseRepair(id) {
  return request({
    url: `/inspection/analyse/diseaseRepair/${id}`,
    method: 'get'
  });
}

//近五年构件病害数对比
export function getDiseaseYearRank(id) {
  return request({
    url: `/inspection/analyse/diseaseYearRank/${id}`,
    method: 'get'
  });
}

//病害类型统计
export function getDiseaseType(id) {
  return request({
    url: `/inspection/analyse/getDiseaseType/${id}`,
    method: 'get'
  });
}
