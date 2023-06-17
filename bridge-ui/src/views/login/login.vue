<template>
  <div class="login">
    <div class="image animate__animated animate__fadeIn">
      <img src="@/assets/images/login/bg-login1.png" alt="" />
    </div>
    <div class="loginForm">
      <div class="title animate__animated animate__backInRight">
        <img src="@/assets/images/logo2.png" alt="" />
        <span>登录</span>
      </div>
      <div class="text animate__animated animate__backInRight">
        桥隧监测及维养信息一体化管理平台
      </div>
      <div class="form animate__animated animate__backInRight">
        <el-input v-model="username" placeholder="请输入账号" clearable>
          <i slot="prefix" class="iconfont icon-yonghu"></i>
        </el-input>
        <el-input
          v-model="password"
          type="password"
          placeholder="请输入密码"
          auto-complete="new-password"
          clearable
          show-password
        >
          <i slot="prefix" class="iconfont icon-mima"></i>
        </el-input>
        <div class="confirm">
          <el-input
            ref="confirm"
            v-model="confirmValue"
            type="text"
            placeholder="请输入验证码"
            class="confirmInput"
          >
            <i slot="prefix" class="iconfont icon-yanzhengma"></i>
          </el-input>
          <div class="confirmImage">
            <canvas
              id="canvas"
              :width="canvasWidth"
              :height="canvasHeight"
              class="codeImg"
            ></canvas>
            <div class="change" @click="createCode">换一张</div>
          </div>
        </div>
        <!-- <div class="tips">{{ errorMsg }}</div> -->
        <el-button @click="loginBtn">登录</el-button>

        <div class="qrLine">
          <el-popover
            placement="right-start"
            trigger="click"
            class="qrCode"
            popper-class="qrPopo"
          >
            <div class="qrBox">
              <div ref="qrCodeUrl" class="qrImg"></div>
              <span>巡查APP下载</span>
              <span>（浏览器扫描）</span>
            </div>
            <div class="qrtxt" slot="reference">
              <span>巡查APP下载</span
              ><span class="iconfont icon-erweima"></span>
            </div>
          </el-popover>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import md5 from 'js-md5';
import QRCode from 'qrcodejs2';
import { login } from '@/api/login/login';
import { setToken } from '@/utils/auth';
export default {
  name: 'Login-dzl',
  data() {
    return {
      username: '',
      password: '',
      confirmValue: '',
      errorMsg: '',
      showError: false,
      canvasWidth: window.innerWidth * 0.0625,
      canvasHeight: window.innerWidth * 0.025,
      size: window.innerWidth / 70 / 14
    };
  },
  mounted() {
    let _this = this;
    document.onkeydown = function () {
      var key = window.event.keyCode;
      if (key == 13 && _this.$route.path == '/login') {
        _this.loginBtn();
      }
    };
    this.createCode();
    this.setQRCode(); //生成二维码
  },
  methods: {
    async loginBtn() {
      if (this.username === '') {
        this.$message({
          message: '请输入账号！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      if (this.password === '') {
        this.$message({
          message: '请输入密码！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      if (this.confirmValue.toLowerCase() !== this.logincode.toLowerCase()) {
        this.$message({
          message: '验证码错误！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        this.confirmValue = '';
        this.$refs.confirm.focus();
        this.createCode();
        return;
      }
      let params = {
        username: this.username,
        password: md5(this.password)
      };
      let { code, data, msg } = await login(params);
      if (code == '0000') {
        this.$message({
          showClose: true,
          message: '登录成功!',
          type: 'success',
          duration: 2000
        });
        this.$store.dispatch('asyncUpdateToken', data.token); //用户token
        this.$store.dispatch('asyncUpdateUserInfo', data.userInfo); //用户信息
        this.$store.dispatch('asyncUpdateRoleInfo', data.roleInfo); //角色信息
        this.$store.dispatch('asyncUpdateRouterList', data.powerList); //路由及权限
        this.$store.dispatch('asyncUpdateActiveIndex', data.powerList[0].id); //默认跳转页面
        setToken(data.token);
        this.$nextTick(() => {
          // this.$router.push(data.powerList[0].routerUrl);
          this.$router.push('/home');
        });
      } else {
        this.$message({
          showClose: true,
          message: msg,
          type: 'error',
          duration: 2000
        });
        this.confirmValue = '';
        this.createCode();
        return;
      }
    },

    //生成二维码
    setQRCode() {
      var qrcode = new QRCode(this.$refs.qrCodeUrl, {
        text: 'http://121.36.52.154:9990/bridge/static/app/bridge-app-v1.2.1_20220303.apk', // 需要转换为二维码的内容
        colorDark: '#202D40',
        colorLight: '#ffffff',
        correctLevel: QRCode.CorrectLevel.H
      });
    },
    // 获得验证码
    createCode() {
      var code = '';
      var codeLength = 4; //验证码的长度
      var random = [
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z'
      ]; //随机数
      for (var i = 0; i < codeLength; i++) {
        //循环操作
        var index = Math.floor(Math.random() * 36); //取得随机数的				索引（0~35）
        if (Math.random() > 0.5) {
          code += random[index].toUpperCase(); //根据索引取得随机数加到code上
        } else {
          code += random[index]; //根据索引取得随机数加到code上
        }
      }
      this.logincode = code;
      this.drawPic(code);
    },

    /**----------------------------验证码---------------------------**/
    /**生成一个随机数**/
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    /**生成一个随机色**/
    randomColor(min, max) {
      var r = this.randomNum(min, max);
      var g = this.randomNum(min, max);
      var b = this.randomNum(min, max);
      return 'rgb(' + r + ',' + g + ',' + b + ')';
    },
    /**绘制验证码图片**/
    drawPic(num) {
      var canvas = document.getElementById('canvas');
      var width = canvas.width;
      var height = canvas.height;
      var ctx = canvas.getContext('2d');
      ctx.textBaseline = 'bottom';

      /**绘制背景色**/
      ctx.fillStyle = this.randomColor(200, 240); //颜色若太深可能导致看不清
      ctx.fillRect(0, 0, width, height);
      /**绘制文字**/

      var txt = num;
      ctx.fillStyle = this.randomColor(20, 100); //随机生成字体颜色
      ctx.font = this.randomNum(14 * this.size, 20 * this.size) + 'px SimHei'; //随机生成字体大小
      // var x = 10 * this.size;
      // var y = 18 * this.size;
      // var deg = this.randomNum(-5 * this.size, 4 * this.size);
      //修改坐标原点和旋转角度
      // ctx.translate(x, y);
      // ctx.rotate((deg * Math.PI) / 180);
      ctx.fillText(txt, 10 * this.size, 20 * this.size);
      // //恢复坐标原点和旋转角度
      // ctx.rotate((-deg * Math.PI) / 180);
      // ctx.translate(-x, -y);
      /* } */
      /* /**绘制干扰线**/
      // for (var i = 0; i < 8; i++) {
      //   ctx.strokeStyle = this.randomColor(40, 180);
      //   ctx.beginPath();
      //   ctx.moveTo(this.randomNum(0, width), this.randomNum(0, height));
      //   ctx.lineTo(this.randomNum(0, width), this.randomNum(0, height));
      //   ctx.stroke();
      // }
      /**绘制干扰点**/
      for (let i = 0; i < 30 * this.size; i++) {
        ctx.fillStyle = this.randomColor(0, 255);
        ctx.beginPath();
        ctx.arc(
          this.randomNum(0, width),
          this.randomNum(0, height),
          1,
          0,
          2 * Math.PI
        );
        ctx.fill();
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.login {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  .image {
    width: 1282px;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    img {
      width: 982px;
      height: 588px;
    }
  }
  .loginForm {
    width: 638px;
    height: 100%;
    background: #fcfcfc;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    .title {
      font-size: 44px;
      color: #202d40;
      display: flex;
      align-items: center;
      img {
        margin-right: 10px;
      }
    }
    .text {
      color: #b0bac9;
      font-size: 16px;
      margin: 10px 0 78px;
    }
    .form {
      width: 441px;
      display: flex;
      flex-direction: column;
      align-items: center;
      .tips {
        height: 25px;
        width: 100%;
        text-align: left;
        font-size: 15px;
        font-weight: bold;
        margin-bottom: 10px;
        color: red;
      }
      .confirm {
        width: 100%;
        margin-bottom: 24px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .confirmInput {
          width: 220px;
          margin: 0;
        }
        .confirmImage {
          height: 56px;
          width: 200px;
          margin-left: 20px;
          display: flex;
          align-items: center;
          .codeImg {
            height: 48px;
            width: 120px;
            // cursor: pointer;
          }
          .change {
            flex: 1;
            font-size: 16px;
            padding-left: 10px;
            color: #1890ff;
            opacity: 1;
            cursor: pointer;
            &:hover {
              opacity: 0.8;
            }
          }
        }
      }
      .iconfont {
        font-size: 20px;
      }
      .qrLine {
        width: 100%;
        margin-bottom: 100px;
        display: flex;
        .qrCode {
          cursor: pointer;
          .qrtxt {
            width: 100%;
            color: #202d40;
            display: flex;
            align-items: center;
            span:first-child {
              margin-right: 10px;
            }
          }
        }
      }
      /deep/ .el-input {
        margin-bottom: 24px;
      }
      /deep/ .el-input__inner {
        height: 56px;
        font-size: 16px;
        color: #333;
        padding: 0 60px;
        border-radius: 8px;
        &::placeholder {
          color: #b0bac9;
        }
        &:focus {
          border: 1px solid #202d3e;
          box-sizing: border-box;
          box-shadow: -1px 0px 0px #c6d6f0, 1px 0px 0px #c6d6f0,
            0px -1px 0px #c6d6f0, 0px 1px 0px #c6d6f0;
        }
      }
      /deep/ .el-button {
        width: 100%;
        height: 56px;
        color: #fff;
        background-color: #202d40;
        margin-bottom: 24px;
        // margin: 24px 0;
        border-radius: 8px;
        box-shadow: 0 4px 12px 0 rgba(32, 45, 64, 0.4);
        span {
          font-size: 20px;
        }
        &:hover {
          background-color: rgba(32, 45, 64, 0.9);
        }
      }
      /deep/ .el-input__prefix {
        left: 28px;
        height: 100%;
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
<style lang="scss">
.qrPopo {
  padding: 28px 28px 21px;
  border-radius: 8px;
  .qrBox {
    font-size: 12px;
    color: #202d40;
    display: flex;
    align-items: center;
    flex-direction: column;
    .qrImg {
      width: 105px;
      height: 105px;
      margin-bottom: 20px;
    }
  }
}
</style>
