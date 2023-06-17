import request from '@/utils/request';

//登陆
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  });
}

//登出
export function logout() {
  return request({
    url: '/logout',
    method: 'delete'
  });
}

//获取传感器设置、预警管理红点
export function getOnlineNotice() {
  return request({
    url: `/online/warning/getOnlineNotice`,
    method: 'get'
  });
}

//首页获取公告消息列表
export function getNoticeList() {
  return request({
    url: `/announcement/notice/list`,
    method: 'get'
  });
}

//修改公告读取状态
export function updNoticeStatus(announcementId, status) {
  return request({
    url: `/announcement/notice/${announcementId}/${status}`,
    method: 'put'
  });
}
