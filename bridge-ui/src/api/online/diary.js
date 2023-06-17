import request from '@/utils/request';

//获取维护日志列表
export function getSensorLog(data) {
  return request({
    url: `/online/log/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//新增一条维护日志
export function addSensorLog(data) {
  return request({
    url: `/online/log`,
    method: 'post',
    data
  });
}

//修改一条维护日志
export function updSensorLog(data) {
  return request({
    url: `/online/log/${data.id}`,
    method: 'put',
    data
  });
}

//删除一条维护日志
export function delSensorLog(id) {
  return request({
    url: `/online/log/${id}`,
    method: 'delete'
  });
}
