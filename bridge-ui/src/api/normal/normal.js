import request from '@/utils/request';

//查询总巡查次数/当月巡查次数/巡查病害数/巡查桥隧数
export function getCount() {
  return request({
    url: `/inspection/overview/getCount`,
    method: 'get'
  });
}

//主要病害占比
export function getDiseaseRatio() {
  return request({
    url: `/inspection/overview/diseaseRatio`,
    method: 'get'
  });
}

//巡查病害数变化
export function getDiseaseTrend(year, month) {
  return request({
    url: `/inspection/overview/diseaseTrend/${year}/${month}`,
    method: 'get'
  });
}

//养护等级占比
export function getGradeRatio() {
  return request({
    url: `/inspection/overview/maintainGradeRatio`,
    method: 'get'
  });
}

//日历工作记录表
export function getWorkRecord(year, month) {
  return request({
    url: `/inspection/overview/workRecord/${year}/${month}`,
    method: 'get'
  });
}
