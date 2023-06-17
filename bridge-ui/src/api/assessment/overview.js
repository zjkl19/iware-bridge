import request from '@/utils/request';

//获取检测项目数和检测桥隧数
export function getCount() {
  return request({
    url: `/evaluation/overview/getCount`,
    method: 'get'
  });
}

//获取构建病害类型统计
export function getDiseaseCount(data) {
  return request({
    url: `/evaluation/overview/getDiseaseCount`,
    method: 'post',
    data
  });
}

//获取桥隧评分排名
export function getListBridgeRank(data) {
  return request({
    url: `/evaluation/overview/listBridgeRank`,
    method: 'post',
    data
  });
}

//列出桥隧技术状况统计
export function getListTechnologyStatus() {
  return request({
    url: `/evaluation/overview/listTechnologyStatus`,
    method: 'get'
  });
}

//列出桥型类型
export function getListBridgeType() {
  return request({
    url: `/evaluation/overview/listBridgeType`,
    method: 'get'
  });
}

//查询构件列表
export function getComponent(id) {
  return request({
    url: `/evaluation/overview/getComponent/${id}`,
    method: 'get'
  });
}
