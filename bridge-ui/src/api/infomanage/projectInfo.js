import request from '@/utils/request';

//获取业主用户的结构物
export function listStructureByUnit(id) {
  return request({
    url: `/project/listStructureByUnit/${id}`,
    method: 'get'
  });
}

// 获取项目list
export function listProjectByPage(data) {
  return request({
    url: '/project/list?pageNum=' + data.pageNum + '&pageSize=' + data.pageSize,
    method: 'post',
    data
  });
}

// 添加项目
export function addProject(data) {
  return request({
    url: '/project',
    method: 'post',
    data
  });
}

// 修改项目
export function updateProject(data) {
  return request({
    url: `/project/${data.id}`,
    method: 'put',
    data
  });
}

// 删除项目
export function deleteProject(projectId) {
  return request({
    url: `/project/${projectId}`,
    method: 'delete'
  });
}

//获取指派信息
export function getAppointList(projectId, pageInfo) {
  return request({
    url: `/project/appoint/list/${projectId}`,
    method: 'get',
    params: {
      pageNum: pageInfo.pageNum,
      pageSize: pageInfo.pageSize
    }
  });
}

//添加指派记录
export function addAppoint(data) {
  return request({
    url: '/project/appoint',
    method: 'post',
    data
  });
}

//删除指派记录
export function delAppoint(recordId) {
  return request({
    url: `/project/appoint/${recordId}`,
    method: 'delete'
  });
}

/**
 * 获取指派业务(业主)
 */
export function getBusiness() {
  return request({
    url: '/project/getBusiness',
    method: 'get'
  });
}

/**
 * 获取指派业务(承接单位主用户)
 */
export function getAppointItem(projectId) {
  return request({
    url: `/project/getBusiness/${projectId}`,
    method: 'get'
  });
}
