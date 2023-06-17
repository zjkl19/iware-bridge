import request from '@/utils/request';

//创建公告
export function addAnnoIns(data) {
  return request({
    url: '/announcement',
    method: 'post',
    data
  });
}
//获取公告列表
export function getAnnouncementList(data) {
  return request({
    url: `/announcement/list?pageNum=${data.pageNum}&pageSize=${data.pageSize}`,
    method: 'post',
    data
  });
}

//删除一条公告
export function delAnnouncement(id) {
  return request({
    url: `/announcement/${id}`,
    method: 'delete'
  });
}

//修改公告信息
export function updateAnnouncement(data) {
  return request({
    url: `/announcement/${data.id}`,
    method: 'put',
    data
  });
}

//发布公告
export function annoPublish(id, type) {
  return request({
    url: `/announcement/publish/${id}/${type}`,
    method: 'put'
  });
}
