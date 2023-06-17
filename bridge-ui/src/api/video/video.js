import request from '@/utils/request';

//查询所有结构物列表
export function getVideoStructureList(id) {
  return request({
    url: `/video/getStructureList/${id}`,
    method: 'get'
  });
}

//新增视频点
export function saveVideo(data) {
  return request({
    url: '/video',
    method: 'post',
    data
  });
}
//编辑视频点信息
export function videoEdit(data) {
  return request({
    url: `/video/${data.id}`,
    method: 'put',
    data
  });
}
//删除视频点
export function videoDelete(id) {
  return request({
    url: `/video/${id}`,
    method: 'delete'
  });
}

//获取萤石云的视频播放token
export function getYingshiyunAccessToken() {
  return request({
    url: `/video/getAccessToken`,
    method: 'get'
  });
}
