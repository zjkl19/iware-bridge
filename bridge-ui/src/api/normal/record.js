import request from '@/utils/request';

//获取巡查计划列表
export function getRecordList(data) {
  return request({
    url: `/inspection/record/list/${data.projectId}/${data.year}`,
    method: 'post'
  });
}

//获取巡查记录列表
export function getDetailList(data) {
  return request({
    url: `/inspection/record/detail/list`,
    method: 'post',
    data
  });
}

//获取病害情况
export function getDiseaseList(planDetailId, data) {
  return request({
    url: `/inspection/record/disease/list/${planDetailId}`,
    method: 'post',
    data
  });
}
//获取简报病害列表
export function getInspectionDisease(id) {
  return request({
    url: `/inspection/record/inspectionDisease/${id}`,
    method: 'get'
  });
}
//导出病害列表
export function exportDisease(data) {
  return request({
    url: `/inspection/record/batch/excel/export`,
    method: 'post',
    responseType: 'blob',
    data
  });
}

//批量导出记录列表
export function exportExcel(data) {
  return request({
    url: `/inspection/record/batch/export`,
    method: 'post',
    responseType: 'blob',
    data
  });
}

//导入
export function importInsExcel(data) {
  return request({
    url: `/inspection/record/importInsExcel`,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  });
}

//下载简报
export function download(id) {
  return request({
    url: `/inspection/record/download/${id}`,
    method: 'get',
    responseType: 'blob'
  });
}

// 获取当天工作记录
export function getDateRecord(date) {
  return request({
    url: `/inspection/record/date/${date}`,
    method: 'get'
  });
}
