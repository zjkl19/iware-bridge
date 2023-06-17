import store from '../store';
import router from '../router';
import { message } from '@/utils/resetMessage';

// 过滤查询对象
export function filterModel(obj) {
  const filterObj = {};
  for (const k in obj) {
    if (obj[k] !== '' && obj[k] !== null) {
      filterObj[k] = obj[k];
    }
  }
  return filterObj;
}

//日期格式化(yyy-MM-dd hh:mm:ss)
export function Dateformat(data) {
  Date.prototype.Format = function (fmt) {
    var o = {
      'M+': this.getMonth() + 1, //月份
      'd+': this.getDate(), //日
      'h+': this.getHours(), //小时
      'm+': this.getMinutes(), //分
      's+': this.getSeconds() //秒
    };
    if (/(y+)/.test(fmt)) {
      //根据y的长度来截取年
      fmt = fmt.replace(
        RegExp.$1,
        (this.getFullYear() + '').substr(4 - RegExp.$1.length)
      );
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          RegExp.$1.length == 1
            ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length)
        );
    }
    return fmt;
  };
  return data.Format('yyyy-MM-dd hh:mm:ss');
}

//日期格式化(yyy-MM-dd)
export function Dateformat1(data) {
  Date.prototype.Format = function (fmt) {
    var o = {
      'M+': this.getMonth() + 1, //月份
      'd+': this.getDate(), //日
      'h+': this.getHours(), //小时
      'm+': this.getMinutes(), //分
      's+': this.getSeconds() //秒
    };
    if (/(y+)/.test(fmt)) {
      //根据y的长度来截取年
      fmt = fmt.replace(
        RegExp.$1,
        (this.getFullYear() + '').substr(4 - RegExp.$1.length)
      );
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          RegExp.$1.length == 1
            ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length)
        );
    }
    return fmt;
  };
  return data.Format('yyyy-MM-dd');
}

//查询页面权限
export function getAuthPage(vue, id) {
  let authList = store.getters.getRouterList;
  let path = router.history.current.path;
  let opArry = ['addOpt', 'deleteOpt', 'updateOpt'];
  opArry.map((child) => {
    vue[child] = false;
  });
  authList.map((item) => {
    if ((item.parentId == id || item.routerUrl === path) && item.active === 1) {
      if (!!item.type) {
        if (vue.opList) {
          vue.opList.map((child) => {
            if (child.type == item.type) {
              child.opShow = true;
            }
          });
        }
        vue[item.type] = true;
      }
    }
  });
  if (!vue.checkOpt) {
    router.replace({
      name: '暂无权限',
      params: {
        noPer: true
      }
    });
  }
}

//下载文件
export function downloadFile(filePath, fileName) {
  let ajax = new XMLHttpRequest();
  ajax.open('GET', filePath, true);
  ajax.setRequestHeader('Cache-Control', 'no-cache');
  ajax.setRequestHeader('Access-Control-Allow-Origin', '*');
  ajax.responseType = 'blob';
  ajax.onload = (e) => {
    let res = e.target.response;
    // let blob = new Blob([res],{type: "application/pdf;charset=UTF-8"}) // 这里的res为后端返回的流数据
    let blob = new Blob([res]);
    let aLink = document.createElement('a');
    aLink.download = fileName; // 下载文件的名字
    aLink.href = URL.createObjectURL(blob);
    aLink.click();
  };
  ajax.send();
  URL.revokeObjectURL(url); // 释放内存
}

//下载文件流
export function downloadBlob(res, fileName) {
  let tempBlob = new Blob([res], { type: 'application/json' });
  let reader = new FileReader();
  reader.onload = (e) => {
    let res1 = e.target.result;
    try {
      let json = JSON.parse(res1);
      message({
        message: json.msg,
        type: 'error',
        showClose: true,
        duration: 2000
      });
    } catch (err) {
      let url = URL.createObjectURL(new Blob([res]));
      let aLink = document.createElement('a');
      aLink.download = fileName; // 下载文件的名字
      aLink.href = url;
      aLink.click();
      URL.revokeObjectURL(url); // 释放内存
    }
  };
  reader.readAsText(tempBlob);
}

//获取当月天数(lastDay:是否返回本月最后一天日期 Boolean)
export function getMonthDay(date, lastDay) {
  let year = Number(date.split('-')[0]);
  let month = Number(date.split('-')[1]);
  let thity = [4, 6, 9, 11];
  let thityOne = [1, 3, 5, 7, 8, 10, 12];
  let day = null;
  if (month != 2) {
    if (thity.includes(month)) {
      day = 30;
    }
    if (thityOne.includes(month)) {
      day = 31;
    }
  } else {
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
      day = 29;
    } else {
      day = 28;
    }
  }
  if (month < 10) {
    month = '0' + month;
  }
  if (lastDay) return year + '-' + month + '-' + day;
  else return day;
}

export default {
  Dateformat,
  Dateformat1,
  getAuthPage,
  downloadFile,
  downloadBlob,
  getMonthDay
};
