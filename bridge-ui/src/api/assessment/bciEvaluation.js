import request from '@/utils/request';

/**
 * 查询线路病害
 * @param {*} id 线路id
 * @returns
 */
export function getDiseaseSummaryList(id) {
  return request({
    url: '/evaluation/record/getDiseaseSummaryList/' + id,
    method: 'get'
  });
}

/**
 * 查询BCI评价列表
 */
export function getBCIEvaluationList(data) {
  return request({
    url: '/evaluation/record/getBCIEvaluationList',
    method: 'post',
    data
  });
}

/**
 * 查询BCI开始评价
 */
export function selBCIEvaluationDetail(data) {
  return request({
    url: '/evaluation/record/selBCIEvaluationDetail',
    method: 'post',
    data
  });
}

/**
 * BCI重新计算
 */
export function countBCI(data) {
  return request({
    url: '/evaluation/record/countBCI',
    method: 'post',
    data
  });
}

/**
 * 计算病害扣分值
 */
export function calculateDeduct(data) {
  return request({
    url: '/evaluation/record/calculateDeduct/' + data,
    method: 'get'
  });
}
