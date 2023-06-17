import request from '@/utils/request';

//添加用户
export function addUser(data) {
  return request({
    url: '/user',
    method: 'post',
    data
  });
}

//修改用户基础信息
export function editUser(data) {
  return request({
    url: `/user/${data.id}`,
    method: 'put',
    data
  });
}

//修改用户状态
export function editUserStatus(id, status) {
  return request({
    url: `/user/updStatus/${id}/${status}`,
    method: 'put'
  });
}

//删除用户
export function delUser(userId) {
  return request({
    url: `/user/${userId}`,
    method: 'delete'
  });
}

//查询用户
export function getUserList(data) {
  return request({
    url: `/user/list?pageNum=${data.pageNum}&pageSize=${data.pageSize}`,
    method: 'post',
    data
  });
}

//修改密码
export function changePassword(model) {
  return request({
    url: `/user/changePwd/${model.id}`,
    method: 'put',
    data: model
  });
}

//获取选择单位列表
export function getUserUnitList() {
  return request({
    url: `/unit/getUserUnitList`,
    method: 'get'
  });
}

// APP用户设备解绑
export function removeBindDevice(id) {
  return request({
    url: `/user/removeBindDevice/${id}`,
    method: 'put'
  });
}
