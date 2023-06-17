import axios from 'axios';
import { message } from '@/utils/resetMessage';
import router from '../router';
import { getToken, setToken } from '@/utils/auth';
import store from '../store';

const baseUrl = process.env.NODE_ENV === 'production' ? '/bridge' : '/bridge'; // 开发服务器
// 创建axios实例  process.env.BASE_API在config -> dev.env.js中设置
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 900000, // 请求超时时间
  params: {}
});

// request拦截器
service.interceptors.request.use(
  (config) => {
    const NODE_ENV = baseUrl;
    let path = router.history.current.path;
    if (config.headers['X-URL-CONTEXT']) {
      config.url = '/' + config.headers['X-URL-CONTEXT'] + config.url;
    } else if (config.headers['X-URL-OTHER']) {
      config.url = config.url;
    } else {
      config.url = NODE_ENV + config.url;
    }
    // if (getToken()) {
    //   config.headers['X-AUTH-TOKEN'] = getToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
    // }
    config.headers['X-AUTH-TOKEN'] = store.getters.getToken; // 让每个请求携带自定义token 请根据实际情况自行修改
    config.params['X-TIME'] = new Date().getTime();
    //模型页
    if (path.includes('/bridgeModel')) {
      path = '/home';
    }
    //桥梁详情
    if (path.includes('/infoManage/bridgeManageDetial')) {
      path = '/infoManage/bridgeManage';
    }
    //隧道详情
    if (path.includes('/infoManage/tunnelManageDetial')) {
      path = '/infoManage/tunnelManage';
    }
    //桥梁巡查简报
    if (path.includes('/normal/bridgeBriefing')) {
      path = '/normal/record';
    }
    //隧道巡查简报
    if (path.includes('/normal/tunnelBriefing')) {
      path = '/normal/record';
    }
    //维修简报
    if (path.includes('/maintain/briefReport')) {
      path = '/maintain/wordRecord';
    }
    config.headers['X-ROUTER-URL'] = path;
    return config;
  },
  (error) => {
    // Do something with request error
    console.log(error); // for debug
    Promise.reject(error);
  }
);

// response 拦截器
service.interceptors.response.use(
  (response) => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data;
    if (res instanceof Blob) {
      return response.data;
    }
    let code = ['0000', '6000', '6001', '6101', '6039'];
    if (!code.includes(res.code)) {
      // session过期
      if (res.code === '6102') {
        // NProgress.done()
        message({
          message: res.msg,
          type: 'error',
          showClose: true,
          duration: 2000
        });
        setTimeout(() => {
          router.push('/');
        }, 500);
      } else if (res.code === '403') {
        //暂无权限
        message({
          message: res.msg,
          type: 'error',
          duration: 2000
        });
        router.replace({
          name: '暂无权限',
          params: {
            noPer: true
          }
        });
      } else if (res.code === '9999') {
        message({
          message: res.msg,
          type: 'error',
          duration: 3 * 1000
        });
      } else {
        //错误
        if (res.msg == '' || res.msg == null) {
          //此处主要是为了解决在内存不足的情况之下，请求丢失的问题。
        } else {
          message({
            message: res.msg,
            type: 'error',
            duration: 2000,
            showClose: true
          });
        }
      }
      return Promise.reject('error');
    } else {
      let token = response.headers['X-AUTH-TOKEN'];
      if (token) {
        if (token !== getToken()) {
          setToken(token);
          // store.commit('SET_TOKEN', token)
        }
      }
      return response.data;
    }
  },
  (error) => {
    // window.loading(false)
    console.log('err' + error); // for debug
    // Message({
    //   message: error.message,
    //   type: 'error',
    //   duration: 5 * 1000,
    //   showClose:true,
    // })
    return Promise.reject(error);
  }
);

export default service;
