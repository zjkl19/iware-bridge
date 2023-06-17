import Vue from 'vue';
import store from '@/store';
import Router from 'vue-router';
import { message } from '@/utils/resetMessage';

Vue.use(Router);

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

const getComponent = (component) => {
  return (resolve) => {
    import(`@/${component}.vue`).then((module) => {
      resolve(module);
    });
  };
};

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: getComponent('views/login/login')
  },
  {
    path: '/bridgeModel',
    name: 'bridgeModel',
    component: getComponent('views/home/bridgeModel')
  },
  {
    path: '/',
    name: '',
    component: getComponent('components/header'),
    children: [
      //首页
      {
        path: '/home',
        name: '首页总览',
        component: getComponent('views/home/home')
      },
      {
        path: '/normal/bridgeBriefing',
        name: '桥梁巡查简报',
        component: getComponent('views/normal/bridgeBriefing')
      },
      {
        path: '/normal/tunnelBriefing',
        name: '隧道巡查简报',
        component: getComponent('views/normal/tunnelBriefing')
      },
      {
        path: '/maintain/briefReport',
        name: '维修简报',
        component: getComponent('views/maintain/briefReport')
      },
      {
        path: '/noPermissiont',
        name: '暂无权限',
        component: getComponent('views/home/noPermission')
      },
      {
        path: '/',
        name: '',
        component: getComponent('components/secondMenu'),
        children: [
          //在线监测
          {
            path: '/online',
            redirect: '/online/online'
          },
          {
            path: '/online/online',
            name: '监测总览',
            component: getComponent('views/online/online')
          },
          {
            path: '/online/onebridgeMonitoring',
            name: '结构物监测',
            component: getComponent('views/online/onebridgeMonitoring')
          },
          {
            path: '/online/earlyWarningManagement',
            name: '预警管理',
            component: getComponent('views/online/earlyWarningManagement')
          },
          {
            path: '/online/warningDetails',
            name: '时程数据',
            component: getComponent('views/online/warningDetails')
          },
          {
            path: '/online/analysisData',
            name: '数据分析',
            component: getComponent('views/online/analysisData')
          },
          {
            path: '/online/stateAnalysis',
            name: '结构状态分析',
            component: getComponent('views/online/stateAnalysis')
          },
          {
            path: '/online/tableManage',
            name: '报表管理',
            component: getComponent('views/online/tableManage')
          },
          {
            path: '/online/diary',
            name: '维护日志',
            component: getComponent('views/online/diary')
          },
          {
            path: '/online/warningSetting',
            name: '传感器设置',
            component: getComponent('views/online/warningSetting')
          },
          //日常巡检
          {
            path: '/normal',
            redirect: '/normal/normal'
          },
          {
            path: '/normal/normal',
            name: '巡查任务总览',
            component: getComponent('views/normal/normal')
          },
          {
            path: '/normal/checkAnalysis',
            name: '巡查结果统计',
            component: getComponent('views/normal/checkAnalysis')
          },
          {
            path: '/normal/plan',
            name: '巡查计划',
            component: getComponent('views/normal/plan')
          },
          {
            path: '/normal/record',
            name: '巡查记录',
            component: getComponent('views/normal/record')
          },
          {
            path: '/normal/report',
            name: '巡查报告',
            component: getComponent('views/normal/report')
          },
          //维修养护
          {
            path: '/maintain',
            redirect: '/maintain/maintain'
          },
          {
            path: '/maintain/maintain',
            name: '维养任务总览',
            component: getComponent('views/maintain/maintain')
          },
          {
            path: '/maintain/checkAnalysis',
            name: '维养效果分析',
            component: getComponent('views/maintain/checkAnalysis')
          },
          {
            path: '/maintain/planManagement',
            name: '维养计划',
            component: getComponent('views/maintain/planManagement')
          },
          {
            path: '/maintain/wordRecord',
            name: '维养记录',
            component: getComponent('views/maintain/wordRecord')
          },
          {
            path: '/maintain/report',
            name: '维养报告',
            component: getComponent('views/maintain/report')
          },
          //检测评估
          {
            path: '/assessment',
            redirect: '/assessment/overview'
          },
          {
            path: '/assessment/overview',
            name: '检测总览',
            component: getComponent('views/assessment/overview')
          },
          {
            path: '/assessment/detection',
            name: '结构物检测',
            component: getComponent('views/assessment/detection')
          },
          {
            path: '/assessment/plan',
            name: '检测计划',
            component: getComponent('views/assessment/plan')
          },
          {
            path: '/assessment/record',
            name: '检测记录',
            component: getComponent('views/assessment/record')
          },
          {
            path: '/assessment/analysis',
            name: '检测数据分析',
            component: getComponent('views/assessment/analysis')
          },
          //视频观察
          {
            path: '/video',
            name: '视频观察',
            component: getComponent('views/video/video')
          },
          //信息管理
          {
            path: '/infoManage',
            redirect: '/infoManage/bridgeManage'
          },
          {
            path: '/infoManage/bridgeManage',
            name: '桥梁信息管理',
            component: getComponent('views/infoManage/bridgeManage')
          },
          {
            path: '/infoManage/bridgeManageDetial',
            name: '桥梁信息详情',
            component: getComponent('views/infoManage/bridgeManageDetial')
          },
          {
            path: '/infoManage/tunnelManage',
            name: '隧道信息管理',
            component: getComponent('views/infoManage/tunnelManage')
          },
          {
            path: '/infoManage/tunnelManageDetial',
            name: '隧道信信息详情',
            component: getComponent('views/infoManage/tunnelManageDetial')
          },
          {
            path: '/infoManage/unitManage',
            name: '单位管理',
            component: getComponent('views/infoManage/unitManage')
          },
          {
            path: '/infoManage/userManage',
            name: '用户管理',
            component: getComponent('views/infoManage/userManage')
          },
          {
            path: '/infoManage/announcement',
            name: '公告管理',
            component: getComponent('views/infoManage/announcement')
          },
          {
            path: '/infoManage/projectManage',
            name: '项目管理',
            component: getComponent('views/infoManage/projectManage')
          }
        ]
      }
    ]
  },
  {
    path: '*', // 错误路由[写在最后一个]
    redirect: '/noPermissiont' //重定向
  }
];

const router = new Router({
  // mode: 'history',
  routes
});

router.beforeEach((to, from, next) => {
  if (to.path != '/login') {
    if (window.performance.navigation.type != 1) {
      //页面不是刷新
      let objLength = Object.keys(store.getters.getUserInfo).length;
      if (objLength > 0) {
        next();
      } else {
        message({
          message: '请先登录！',
          showClose: true,
          type: 'error',
          duration: 3000
        });
        router.replace('/login');
      }
    } else {
      // let store = JSON.parse(sessionStorage.getItem('store'));
      // let objLength = Object.keys(store.userInfo).length;
      // if (objLength > 0) {
      //   next();
      // } else {
      //   message({
      //     message: '登录过期，请重新登录！',
      //     showClose: true,
      //     type: 'error',
      //     duration: 3000
      //   });
      //   router.replace('/login');
      // }
    }
  }
  next();
});

export default router;
