import request from '@/utils/request';

// 获取电子档案列表
export function getElectronicList(data) {
  return request({
    url: `/electronic/list?pageNum=${data.pageNum}&pageSize=10`,
    method: 'post',
    data: data
  });
}

// 添加电子档案
export function addElectronic(data) {
  return request({
    url: `/electronic`,
    method: 'post',
    data
  });
}

// 修改电子档案
export function updElectronic(data, id) {
  return request({
    url: `/electronic/${id}`,
    method: 'put',
    data
  });
}

// 删除电子档案
export function delElectronic(id) {
  return request({
    url: `/electronic/${id}`,
    method: 'delete'
  });
}

// 下载电子档案
export function downloadElectronic(id) {
  return request({
    url: `/electronic/download/${id}`,
    responseType: 'blob',
    method: 'get'
  });
}
