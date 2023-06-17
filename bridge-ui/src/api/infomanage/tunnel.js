import request from '@/utils/request';

//根据条件获取隧道数据
export function getTunnelList(data) {
  return request({
    url: '/tunnel/list?pageNum=' + data.pageNum + '&pageSize=' + data.pageSize,
    method: 'post',
    data
  });
}

//添加隧道
export function addTunnel(data) {
  return request({
    url: '/tunnel',
    method: 'post',
    data
  });
}

//修改隧道基础信息
export function updTunnel(data) {
  return request({
    url: `/tunnel/${data.id}`,
    method: 'put',
    data
  });
}

//修改隧道基础表和详情表信息
export function updateTunnelDetail(data) {
  return request({
    url: `/tunnel/detail/${data.id}`,
    method: 'put',
    data
  });
}

//删除隧道
export function delTunnel(id) {
  return request({
    url: `/tunnel/${id}`,
    method: 'delete'
  });
}

//查看一条桥梁的详细信息
export function getTunnelDetailById(id) {
  return request({
    url: `/tunnel/detail/${id}`,
    method: 'get'
  });
}

//查询全部附件信息
export function getAnnexList(pageInfo, structureId) {
  return request({
    url: `/tunnel/getAnnexList/${structureId}`,
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
