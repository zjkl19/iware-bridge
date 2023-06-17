import request from '@/utils/request';

//获取报表列表
export function getReportList(data) {
  return request({
    url: `/report/list?pageSize=${data.pageSize}&pageNum=${data.pageNum}`,
    method: 'post',
    data
  });
}

//上传报告
export function addReport(data) {
  return request({
    url: `/report/3`,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  });
}

//修改报告
export function updReport(data) {
  return request({
    url: `/report/${data.id}`,
    method: 'put',
    data
  });
}

//删除报告
export function delReport(id) {
  return request({
    url: `/report/${id}`,
    method: 'delete'
  });
}

//下载报告
export function downLoadReport(id) {
  return request({
    url: `/report/download/${id}`,
    method: 'get'
  });
}
