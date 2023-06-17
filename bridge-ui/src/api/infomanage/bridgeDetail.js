import request from '@/utils/request';

// 获取日常巡查记录
export function getInspectionRecord(structureId, pageNum) {
  return request({
    url: `/bridgeManage/inspectionRecord/${structureId}?pageSize=10&pageNum=${pageNum}`,
    method: 'get'
  });
}

// 获取维修养护记录
export function getMaintainRecord(structureId, pageNum) {
  return request({
    url: `/bridgeManage/maintainRecord/${structureId}?pageSize=10&pageNum=${pageNum}`,
    method: 'get'
  });
}

// 获取检测计划记录
export function getMonitorRecord(structureId, pageNum) {
  return request({
    url: `/bridgeManage/monitorRecord/${structureId}?pageSize=10&pageNum=${pageNum}`,
    method: 'get'
  });
}
