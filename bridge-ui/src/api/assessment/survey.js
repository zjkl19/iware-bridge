import request from '@/utils/request';
import { param } from 'jquery';

// 获取桥梁结构树形图
export function getBridgeSurveyDetail(params) {
  return request({
    url: '/evaluation/record/getBridgeSurveyDetail',
    method: 'post',
    data: params
  });
}

// 获取桥梁结构信息
export function getDiseaseDetail(data) {
  return request({
    url: '/evaluation/record/getDiseaseDetail',
    method: 'post',
    data
  });
}

// 获取桥梁结构图片
export function getTreeImgList(data) {
  return request({
    url: '/evaluation/record/getPictures',
    method: 'post',
    data
  });
}

// 上传桥梁结构图片
export function getUpLoad(data) {
  return request({
    url: '/evaluation/record/uploadPicture',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  });
}

// 删除图片
export function deleteImg(id) {
  return request({
    url: `/evaluation/record/pictures/${id}`,
    method: 'delete'
  });
}

// 下载结构图片
export function downLoadPhoto(data) {
  return request({
    url: '/evaluation/record/pictures/download',
    method: 'post',
    data
  });
}

// 选择默认图片
export function selectDefaultPicture(data) {
  return request({
    url: '/evaluation/record/updatePictureStatus/' + data.id,
    method: 'put',
    data
  });
}
