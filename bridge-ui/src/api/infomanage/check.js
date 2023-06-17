import request from '@/utils/request';



// 获取检测记录
export function getMonitorRecord(pageInfo, structureId) {
    return request({
      url: `/infoManager/monitorRecord/${structureId}`,
      method: 'get',
      params: {
        pageNum: pageInfo.pageNum,
        pageSize: pageInfo.pageSize
      }
    })
  }
