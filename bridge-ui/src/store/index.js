import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const state = {
  token: '', //用户token
  userInfo: {}, //用户信息
  roleInfo: {}, //权限信息
  activeIndex: null, //当前页面id
  routerList: [], //路由页面
  secondMenuList: [],
  dotWarn: 0, //预警管理红点
  dotSensor: 0, //传感器设置红点
  noticeState: 0 //公告状态
};

const getters = {
  getToken(state) {
    return state.token;
  },
  getUserInfo(state) {
    return state.userInfo;
  },
  getRoleInfo(state) {
    return state.roleInfo;
  },
  getActiveIndex(state) {
    return state.activeIndex;
  },
  getRouterList(state) {
    return state.routerList;
  },
  getSecondMenuList(state) {
    return state.secondMenuList;
  },
  getDotWarn(state) {
    return state.dotWarn;
  },
  getDotSensor(state) {
    return state.dotSensor;
  },
  getNoticeState(state) {
    return state.noticeState;
  }
};

const mutations = {
  updateToken(state, token) {
    state.token = token;
  },
  updateUserInfo(state, userInfo) {
    state.userInfo = userInfo;
  },
  updateRoleInfo(state, roleInfo) {
    state.roleInfo = roleInfo;
  },
  updateActiveIndex(state, activeIndex) {
    state.activeIndex = activeIndex;
  },
  updateRouterList(state, routerList) {
    state.routerList = routerList;
  },
  updateSecondMenuList(state, secondMenuList) {
    state.secondMenuList = secondMenuList;
  },
  updateDotWarn(state, dotWarn) {
    state.dotWarn = dotWarn;
  },
  updateDotSensor(state, dotSensor) {
    state.dotSensor = dotSensor;
  },
  updateNoticeState(state, noticeState) {
    state.noticeState = noticeState;
  }
};

const actions = {
  asyncUpdateToken(context, token) {
    context.commit('updateToken', token);
  },
  asyncUpdateUserInfo(context, userInfo) {
    context.commit('updateUserInfo', userInfo);
  },
  asyncUpdateRoleInfo(context, roleInfo) {
    context.commit('updateRoleInfo', roleInfo);
  },
  asyncUpdateActiveIndex(context, activeIndex) {
    context.commit('updateActiveIndex', activeIndex);
  },
  asyncUpdateRouterList(context, routerList) {
    context.commit('updateRouterList', routerList);
  },
  asyncUpdateSecondMenuList(context, secondMenuList) {
    context.commit('updateSecondMenuList', secondMenuList);
  },
  asyncUpdateDotWarn(context, dotWarn) {
    context.commit('updateDotWarn', dotWarn);
  },
  asyncUpdateDotSensor(context, dotSensor) {
    context.commit('updateDotSensor', dotSensor);
  },
  asyncUpdateNoticeState(context, noticeState) {
    context.commit('updateNoticeState', noticeState);
  }
};

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
});
