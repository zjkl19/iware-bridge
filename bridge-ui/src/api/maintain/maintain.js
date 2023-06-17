import request from '@/utils/request';

// 查询维养桥隧数/待处理病害数/已处理病害数/年度维养总金额
export function getCount() {
  return request({
    url: `/maintain/overview/getCount`,
    method: 'get'
  });
}

// 日历工作记录表
export function getWorkRecord(year, month) {
  return request({
    url: `/maintain/overview/workRecord/${year}/${month}`,
    method: 'get'
  });
}

// 维修病害数
export function getdiseaseCount() {
  return request({
    url: `/maintain/overview/diseaseCount`,
    method: 'get'
  });
}

//维修类型分布
export function gettypeRatio() {
  return request({
    url: `/maintain/overview/typeRatio`,
    method: 'get'
  });
}

// 维修费用趋势
export function accountTrend(year, month) {
  return request({
    url: `/maintain/overview/accountTrend/${year}/${month}`,
    method: 'get'
  });
}
