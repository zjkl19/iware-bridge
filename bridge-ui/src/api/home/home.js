import request from '@/utils/request';

//首页获取用户桥梁
export function getBridgeList(data) {
  return request({
    url: `/home/listStructure`,
    method: 'post',
    data
  });
}

//获取图片列表
export function getPhotoList(targetId, type) {
  return request({
    url: `/photo/list/${targetId}/${type}?pageSize=10&pageNum=1`,
    method: 'get'
  });
}

//获取基础情况
export function getBaseInfo() {
  return request({
    url: `/home/getBaseInfo`,
    method: 'get'
  });
}

//获取预警统计
export function getWarningInfo(type) {
  return request({
    url: `/home/getWarningInfo/${type}`,
    method: 'get'
  });
}

//获取巡查维养信息
export function getInspMainInfo(type) {
  return request({
    url: `/home/getInspMainInfo/${type}`,
    method: 'get'
  });
}

//获取桥隧综合评价指数
export function getComprehensive(type) {
  return request({
    url: `/home/getComprehensive`,
    method: 'get'
  });
}
