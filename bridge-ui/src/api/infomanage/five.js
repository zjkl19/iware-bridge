import request from '@/utils/request'



// 获取维修养护记录
export function getFiveList(pageInfo, model) {
    return request({
      url: `/infoManager/maintin/plainrecord?pageNum=${pageInfo.pageNum}&pageSize=${pageInfo.pageSize}`,
      method: 'post',
      data: model
    })
  }