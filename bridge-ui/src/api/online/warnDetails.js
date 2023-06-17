import request from '@/utils/request';

//分类获取传感器列表
export function getListByType(id, type, all = 0) {
  return request({
    url: `/online/sensor/listByType/${id}/${type}/${all}`,
    method: 'get'
  });
}

//传感器历史数据
export function getHistory(data) {
  return request({
    url: '/online/sensorData/history',
    method: 'post',
    data
  });
}

//传感器频谱数据
export function getFrequency(data) {
  return request({
    url: '/online/sensorData/frequency',
    method: 'post',
    data
  });
}

//获取车型列表
export function getAxleType() {
  return request({
    url: '/online/sensorData/list/axleType',
    method: 'get'
  });
}

//获取实时值称重前十条
export function getLastTen(data) {
  return request({
    url: '/online/sensorData/getLastTen',
    method: 'post',
    data
  });
}

//箱型图分析
export function boxAnalyse(data) {
  return request({
    url: '/online/sensorData/boxAnalyse',
    method: 'post',
    data
  });
}

//获取数据列表
export function getList(data) {
  return request({
    url: `/online/sensorData/list?pageSize=10&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//导出数据列表
export function exportExcell(data) {
  return request({
    url: `/online/sensorData/batch/export`,
    method: 'post',
    responseType: 'blob',
    data
  });
}

//获取传感器列表
export function getSensorList(data) {
  return request({
    url: `/online/sensor/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//根据id获取传感器
export function getSensor(id) {
  return request({
    url: `/online/sensor/${id}`,
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
export function updSensor(data) {
  return request({
    url: `/online/sensor/${data.id}`,
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
