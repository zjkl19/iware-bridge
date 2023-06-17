import request from '@/utils/request';

// 获取上传附件记录
export function uploadModel(data, index) {
  return request({
    url: `/photo/model/upload/${index}`,
    method: 'post',
    data
  });
}
// 修改上传附件记录
export function updateModel(data, id) {
  return request({
    url: `/photo/model/update/${id}`,
    method: 'put',
    data
  });
}
