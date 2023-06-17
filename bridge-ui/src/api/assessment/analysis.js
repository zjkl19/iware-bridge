import request from '@/utils/request';

//获取BCIBSI评分
export function getBCIBSI(data) {
  return request({
    url: `/evaluation/monitorAnalysis/getBCIBSI`,
    method: 'post',
    data
  });
}

//获取构件病害类型统计
export function getComponentDiseaseType(data) {
  return request({
    url: `/evaluation/monitorAnalysis/getComponentDiseaseType`,
    method: 'post',
    data
  });
}

//获取页面信息
export function getPageInfo(data) {
  return request({
    url: `/evaluation/monitorAnalysis/getPageInfo`,
    method: 'post',
    data
  });
}

//获取最新的项目/结构物/时间数据
export function getNewProject(data) {
  return request({
    url: `/evaluation/monitorAnalysis/newProject`,
    method: 'post',
    data
  });
}

//获取最新的项目/结构物/时间数据
export function getStructureTimeList(id, id2) {
  return request({
    url: `/evaluation/monitorAnalysis/getStructureTimeList/${id}/${id2}`,
    method: 'get'
  });
}
