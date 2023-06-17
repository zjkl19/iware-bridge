import request from '@/utils/request';

//根据条件获取桥梁列表
export function getBridgeList(data) {
  return request({
    url:
      '/bridgeManage/list?pageNum=' +
      data.pageNum +
      '&pageSize=' +
      data.pageSize,
    method: 'post',
    data
  });
}

//根据项目id获取桥梁
export function getBridgeListByProjectId(projectId) {
  return request({
    url: `/bridgeManage/listByProjectId/${projectId}`,
    method: 'get'
  });
}

//查看一条桥梁的详细信息
export function getBridgeDetailById(id) {
  return request({
    url: `/bridgeManage/detail/${id}`,
    method: 'get'
  });
}

//添加桥梁
export function addBridge(data) {
  return request({
    url: '/bridgeManage',
    method: 'post',
    data: data
  });
}

//修改桥梁基础信息
export function updbridge(data) {
  return request({
    url: `/bridgeManage/${data.id}`,
    method: 'put',
    data
  });
}

//修改桥梁基础表和详情表信息
export function updateBridgeDetail(data) {
  return request({
    url: `/bridgeManage/detail/${data.id}`,
    method: 'put',
    data
  });
}

//删除桥梁
export function delBridge(id) {
  return request({
    url: `/bridgeManage/${id}`,
    method: 'delete'
  });
}

//查询全部附件信息
export function getAnnexList(pageInfo, structureId) {
  return request({
    url: `/bridgeManage/getAnnexList/${structureId}`,
    method: 'get',
    params: {
      pageNum: pageInfo.pageNum,
      pageSize: pageInfo.pageSize
    }
  });
}

//删除一个附件
export function delAnnexById(id) {
  return request({
    url: `/bridgeManage/delAnnexById/${id}`,
    method: 'delete'
  });
}
