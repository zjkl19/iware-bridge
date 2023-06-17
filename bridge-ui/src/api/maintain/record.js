import request from '@/utils/request';

//获取维养记录列表
export function getRecordList(data) {
  return request({
    url: `/maintain/record/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//下载简报
export function downLoadRecord(id) {
  return request({
    url: `/maintain/record/download/${id}`,
    responseType: 'blob',
    method: 'get'
  });
}

//上传图片
export function uploadPhoto(data) {
  return request({
    url: `/maintain/record/upload`,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    method: 'post',
    data
  });
}

//批量导出记录列表
export function exportRecord(data) {
  return request({
    url: `/maintain/record/batch/export`,
    method: 'post',
    responseType: 'blob',
    data
  });
}

//导入
export function importMaiExcel(data) {
  return request({
    url: `/maintain/record/importMaiExcel`,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  });
}

//查询单条细项
export function getDetail(id) {
  return request({
    url: `/maintain/record/${id}`,
    method: 'get'
  });
}
//获取当天工作记录
export function getDateRecord(date) {
  return request({
    url: `/maintain/record/date/${date}`,
    method: 'get'
  });
}
