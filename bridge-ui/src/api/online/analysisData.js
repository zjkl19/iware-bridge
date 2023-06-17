import request from '@/utils/request';

//查询结构物
export function getSensorStructure(type) {
  return request({
    url: `/online/weightSensor/sensorStructure?type=${type}`,
    method: 'get'
  });
}
//查询结构物下的测点/车道
export function getSensorWeightCarNo(structureId, type) {
  return request({
    url: `/online/weightSensor/sensorWeightCarNo/${structureId}/${type}`,
    method: 'get'
  });
}

/*-----------------------------------车辆荷载分析--------------------------------------*/
//获取车型、车速分布
export function getDistribute(data) {
  return request({
    url: `/online/weightSensor/distribute`,
    method: 'post',
    data
  });
}

//获取车流量时程
export function getTrafficFlow(data) {
  return request({
    url: `/online/weightSensor/trafficFlow`,
    method: 'post',
    data
  });
}

//获取最大车重
export function getMaxAlexWeight(data) {
  return request({
    url: `/online/weightSensor/maxAlexWeight`,
    method: 'post',
    data
  });
}

//获取车重分布
export function getCarWeight(data) {
  return request({
    url: `/online/weightSensor/carWeight`,
    method: 'post',
    data
  });
}

//重车统计
export function getCountCarWeight(data) {
  return request({
    url: `/online/weightSensor/countCarWeight`,
    method: 'post',
    data
  });
}

//超重车统计
export function getTransfiniteCar(data) {
  return request({
    url: `/online/weightSensor/transfiniteCar`,
    method: 'post',
    data
  });
}

/*-----------------------------------振动频谱分析、拉索索力分析--------------------------------------*/
//查询左侧图数据
export function getFrequency(data) {
  return request({
    url: '/online/sensorData/frequency',
    method: 'post',
    data
  });
}

//查询右侧图数据
export function getFrequencyPoint(data) {
  return request({
    url: '/online/sensorData/frequencyPoint',
    method: 'post',
    data
  });
}

/*-----------------------------------相关性分析--------------------------------------*/
//查询相关性分析
export function getCorrelationAnalysis(data) {
  return request({
    url: '/online/sensorData/correlationAnalysis',
    method: 'post',
    data
  });
}

/*-----------------------------------箱线图分析--------------------------------------*/
