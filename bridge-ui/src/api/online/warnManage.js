import request from '@/utils/request';

//获取预警列表
export function getWarnList(data) {
  return request({
    url: `/online/warning/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//处理/批处理预警
export function warnProcessing(data) {
  return request({
    url: `/online/warning/processing`,
    method: 'put',
    data
  });
}

//获取预警信息点附近折线数据
export function getSensorData(data) {
  return request({
    url: `/online/warning/getSensorData`,
    method: 'post',
    data
  });
}
