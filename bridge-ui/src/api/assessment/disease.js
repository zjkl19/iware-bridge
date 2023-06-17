import request from '@/utils/request';

/**
 * 桥梁病害获取数据
 * @param {*} keyword 关键字
 * @param {*} id 结构物计划id
 * @returns
 */
export function getDiseaseList(param) {
  return request({
    url: '/evaluation/record/getDiseaseList',
    method: 'post',
    data: param
  });
}

/**
 * 回收站获取数据
 * @param {*} id 结构物计划id
 * @param {*} pageSize 每页数量
 * @param {*} currentPage 当前页码
 * @returns
 */
export function getRecyclingList(params) {
  return request({
    url: '/evaluation/record/getRecyclingList',
    method: 'post',
    data: params
  });
}

/**
 * 回收站获取页数
 * @param {*} id  结构物计划id
 * @returns
 */
export function getRecyclingCount(id) {
  return request({
    url: '/assess/diseaseBridge/getRecyclingCount',
    method: 'post',
    data: {
      id: id
    }
  });
}

/**
 * 记录详情获取数据
 * @param {*} id 结构物计划id
 * @param {*} creator 创建人
 * @param {*} createTime 创建时间
 * @returns
 */
export function getRecordDetailList(params) {
  return request({
    url: '/evaluation/record/getRecordDetailList',
    method: 'post',
    data: params
  });
}

/**
 * 替换记录
 * @param {*} creator 创建人
 * @param {*} createTime 创建时间
 * @returns
 */
export function replaceRecord(creator, createTime) {
  return request({
    url: '/evaluation/record/replaceRecord',
    method: 'post',
    data: {
      creator: creator,
      createTime: createTime
    }
  });
}

/**
 * 查看记录数据
 * @param {*} currentPage 当前页码
 * @param {*} pageSize 每页数量
 * @param {*} targetId 实例id
 * @param {*} partType 构件类型id
 * @param {*} diseaseId 病害id
 * @returns
 */
export function getRecordList(params) {
  return request({
    url: '/evaluation/record/getDiseaseRecordList',
    method: 'post',
    data: params
  });
}

/**
 * 查看记录数量
 * @param {*} targetId 实例id
 * @param {*} partType 构件类型id
 * @param {*} diseaseId 病害id
 * @returns
 */
export function getRecordCount(targetId, partType, diseaseId) {
  return request({
    url: '/assess/diseaseBridge/getRecordCount',
    method: 'post',
    data: {
      targetId: targetId,
      partType: partType,
      diseaseId: diseaseId
    }
  });
}

/**
 * 照片地址
 * @param {*} targetId 病害实例id
 * @returns
 */
export function getPhotoUrl(targetId) {
  return request({
    url: `/evaluation/record/getPhotoUrl/${targetId}`,
    method: 'get'
    //  data: {
    //     targetId: targetId
    //   }
  });
}

/**
 * 删除照片
 * @param {*} id 主键id
 * @param {*} status 状态id
 * @returns
 */
export function deletePhoto(id, status) {
  return request({
    url: '/evaluation/record/deletePhoto/' + id,
    method: 'delete'
    // data: {
    //   id: id,
    //   status: status
    // }
  });
}

/**
 * 修改检测照片
 * @param {*} targetId 病害实例id
 * @param {*} id 主键id
 * @param {*} staus 状态id
 * @returns
 */
export function updatePhoto(data) {
  return request({
    url: '/evaluation/record/updatePhoto',
    method: 'post',
    data
  });
}

/**
 * 判断当前桥是否有原始记录
 * @param {*} id 结构物计划id
 * @returns
 */
export function isOriginalRecordByStructure(id) {
  return request({
    url: '/evaluation/record/isOriginalRecordByStructure/' + id,
    method: 'get'
  });
}
