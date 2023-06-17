import request from '@/utils/request';

//获取传感器列表
export function getSensorList(data) {
  return request({
    url: `/online/sensor/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//获取传感器类型/原理/产商/构件
export function getSelList() {
  return request({
    url: `/online/sensor/select/list`,
    method: 'get'
  });
}

//根据传感器类型获取细项
export function getDetailList(sensorTypeId) {
  return request({
    url: `/online/sensor/details/${sensorTypeId}`,
    method: 'get'
  });
}

//新增一条传感器
export function addSensor(data) {
  return request({
    url: `/online/sensor`,
    method: 'post',
    data
  });
}

//修改一条传感器
export function updSensor(data, id) {
  return request({
    url: `/online/sensor/${id}`,
    method: 'put',
    data
  });
}

//删除一条传感器
export function delSensor(id) {
  return request({
    url: `/online/sensor/${id}`,
    method: 'delete'
  });
}

/* ----------------维护记录-------------------- */
//获取维护记录列表
export function getRecord(data) {
  return request({
    url: `/online/record/${data.meansPointId}?pageSize=10&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//新增一条维护记录
export function addRecord(data) {
  return request({
    url: `/online/record`,
    method: 'post',
    data
  });
}

//修改一条维护记录
export function updRecord(data) {
  return request({
    url: `/online/record/${data.id}`,
    method: 'put',
    data
  });
}

//获取维护记录列表
export function delRecord(id) {
  return request({
    url: `/online/record/${id}`,
    method: 'delete'
  });
}
