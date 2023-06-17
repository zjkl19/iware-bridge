import request from '@/utils/request';

// 获取图片列表
export function listByPage(pageInfo, id, type) {
  return request({
    url: `/photo/list/${id}/${type}`,
    method: 'get',
    params: {
      pageNum: pageInfo.pageNum,
      pageSize: pageInfo.pageSize
    }
  });
}

export function downloadFile(id) {
  return request({
    url: `/photo/download/${id}`,
    method: 'get',
    responseType: 'blob'
  });
}

// 上传图片
export function uploadFiles(data, type) {
  return request({
    url: `/photo/upload/${type}`,
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  });
}

// 删除图片
export function deletePhoto(id) {
  return request({
    url: `/photo/${id}`,
    method: 'delete'
  });
}
