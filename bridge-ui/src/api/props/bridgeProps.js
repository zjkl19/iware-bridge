import request from '@/utils/request'

const getBirdgeTypes=function(){
    return request({
        url: '/brigeProps/getBrigeTypes',
        method: 'get',
        data:{}
      })
}

export {
    getBirdgeTypes
}