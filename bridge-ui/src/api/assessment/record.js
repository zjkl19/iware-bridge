import request from '@/utils/request';

//获取检测列表条件
export function getCondition() {
  return request({
    url: '/evaluation/record/getCondition',
    method: 'get',
    data: {}
  });
}

//获取检测列表
export function getRecordList(data) {
  return request({
    url: '/evaluation/record/getRecordList',
    method: 'post',
    data
  });
}

//删除检测列表项
export function delMonitorStructure(id) {
  return request({
    url: '/evaluation/record/delMonitorStructure?id=' + id,
    method: 'get'
  });
}

//获取检测报告表格数据
export function getRoadList(id) {
  return request({
    url: '/evaluation/record/getRoadList/' + id,
    method: 'get'
  });
}

//生成报告
export function generateReport(data) {
  return request({
    url: '/evaluation/record/generateReport',
    method: 'post',
    data
  });
}

//生成记录
export function insertRecord(data) {
  return request({
    url: '/evaluation/record/insertRecord',
    method: 'post',
    data
  });
}

//导出记录
export function exportRecord(data) {
  return request({
    url: '/evaluation/record/exportRecord/'+ data.id,
    method: 'get',
  });
}

//获取结构物原始记录
export function getOriginalRecord(id) {
  return request({
    url: '/evaluation/record/getOriginalRecord/' + id,
    method: 'get'
  });
}

//获取导出数据
export function getExportRecord(id) {
  return request({
    url: '/evaluation/record/getExportRecord/' + id,
    method: 'get'
  });
}

// 下载检测报告
export function downloadTestReport(id) {
  return request({
    url: '/evaluation/record/downloadTestReport/' + id,
    responseType: 'blob',
    method: 'get'
  });
}

// 是否导出原始记录（全局）
export function isOriginalRecordByStructure(id) {
  return request({
    url: `/evaluation/record/isOriginalRecordByStructure/${id}`,
    method: 'get'
  });
}
