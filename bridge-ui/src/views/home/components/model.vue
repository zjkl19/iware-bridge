<template>
  <div class="homeModel">
    <div id="loading" class="loading-page">
      <div class="counter">
        <p class="loading_txt">模型加载中...</p>
        <h1 id="percent">0%</h1>
      </div>
    </div>
    <div id="container" class="model"></div>
    <div id="iconBox"></div>
    <div id="contentBox" class="contentBox">
      <div class="contentItem first">
        <span class="textGraYellow">表面式应变计-5001</span>正常<span></span>
      </div>
      <div class="contentItem second">
        <div class="secItem">
          <span class="textGraGreen">28.3℃</span><span>温度值</span>
        </div>
        <div class="secItem">
          <span class="textGraGreen">3855.1 με</span><span>应变值</span>
        </div>
        <div class="secItem">
          <span class="textGraGreen">13.9 με</span><span>应变差值</span>
        </div>
        <div class="secItem">
          <span class="textGraGreen">1039 hz</span><span>频率值</span>
        </div>
      </div>
      <div class="contentItem third">
        <modelLine :echartData="modelData"></modelLine>
      </div>
      <div class="contentItem fourth">
        <span>最新预警</span><span>2020-12-23 16:00:03</span
        ><span class="textGraGreen">三级</span>
      </div>
    </div>
    <div id="videoBox" class="videoBox">
      <video src=""></video>
    </div>
  </div>
</template>
<script>
import * as THREE from 'three';
import { OrbitControls } from 'assets/js/OrbitControls2';
import $ from 'jquery';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import { OBJLoader, MTLLoader } from 'assets/js/objmtlLoader';
import modelLine from './modelLine';
export default {
  components: { modelLine },
  data() {
    return {
      camera: null,
      scene: null,
      controls: null,
      renderer: null,
      percent: 0,
      ObjectName: [{ path: 'Tongshan', name: 'bridge' }],
      TerrainName: [{ path: 'Terrain', name: 'Terrain' }],
      waterName: [{ path: 'River', name: 'River' }],
      //模型加载时间
      loadtime: 0,
      group: null,
      percentMock: null,
      raycaster: new THREE.Raycaster(),
      mouse: new THREE.Vector2(),
      iconList: [
        {
          id: 1,
          name: 'speed',
          imgUrl: require('@/assets/images/home/icon_加速度1.png'),
          xAxis: 0,
          yAxis: 0.1,
          zAxis: 0,
          type: 1
        },
        {
          id: 2,
          name: 'video',
          imgUrl: require('@/assets/images/home/icon_video.png'),
          xAxis: -5,
          yAxis: 0.1,
          zAxis: 2,
          type: 2
        }
      ],
      modelData: {
        xData: [
          '16:40:44',
          '16:42:44',
          '16:44:44',
          '16:46:44',
          '16:40:44',
          '16:48:44',
          '16:50:44',
          '16:52:44',
          '16:54:44',
          '16:56:44'
        ],
        data: [-0.3, 0.2, -0.1, 0.6, -0.3, 0.3, -0.25, 0.05, -0.2, 0.1]
      },
      showInfoBox: false,
      showVideoBox: false
    };
  },
  methods: {
    //初始化，构建场景等
    init() {
      let _this = this;
      let container = document.getElementById('container');

      //加载场景
      this.scene = new THREE.Scene();
      this.scene.background = new THREE.TextureLoader().load(
        require('@/assets/images/home/tiankong.png')
      );

      //加载相机
      this.camera = new THREE.PerspectiveCamera(
        7,
        window.innerWidth / window.innerHeight,
        1,
        10000
      );
      this.camera.position.set(0, 50, 120);
      this.camera.lookAt(0, 0, 0);
      this.scene.add(this.camera);

      //加载坐标轴
      // let axis = new THREE.AxesHelper(3);
      // this.scene.add(axis);

      //创建地形
      this.createTerrain2(this.TerrainName[0]);
      this.createTerrain2(this.waterName[0]);
      this.group = new THREE.Group();
      //创建模型
      this.createTerrain(this.ObjectName[0]);

      /**
       * 光源设置
       */
      // 点光源
      let point = new THREE.PointLight(0xffffff, 0.8);
      // let point2 = new THREE.DirectionalLight(0xffffff)  //平行光
      point.position.set(0, 100, 130); //点光源位置
      this.camera.add(point); //点光源添加到相机中
      // this.scene.add(point); //点光源添加到场景中
      //环境光
      let ambient = new THREE.AmbientLight(0xffffff);
      this.scene.add(ambient);
      // this.scene.add(this.camera)

      //渲染
      this.renderer = new THREE.WebGLRenderer({
        antialias: true, // 是否开启反锯齿，设置为true开启反锯齿
        alpha: false, //是否可以设置背景色透明
        precision: 'lowp', //着色精度选择。 highp/mediump/lowp
        premultipliedAlpha: true, //表示是否可以设置像素深度（用来度量图像的分辨率）
        preserveDrawingBuffer: true, // 表示是否保存绘图缓冲
        maxLights: 3, // 最大灯光数
        stencil: false //表示是否使用模板字体或图案
      });
      this.renderer.setClearColor('rgb(237,237,237)', 1.0); //设置背景颜色
      this.renderer.setPixelRatio(window.devicePixelRatio); // 设置分辨率
      this.renderer.setSize(window.innerWidth, window.innerHeight);
      // renderer.setClearColor(0xb9d3ff,1);//设置背景颜色
      container.appendChild(this.renderer.domElement); //body元素中插入canvas对象

      //控制器
      this.controls = new OrbitControls(this.camera, this.renderer.domElement);
      // 如果使用animate方法时，将此函数删除
      this.controls.addEventListener('change', this.render);
      // 使动画循环使用时阻尼或自转 意思是否有惯性
      this.controls.enableDamping = true;
      //动态阻尼系数 就是鼠标拖拽旋转灵敏度
      this.controls.dampingFactor = 0.25;
      //是否可以缩放
      this.controls.enableZoom = true;
      //是否自动旋转
      this.controls.autoRotate = false;
      //设置相机距离原点的最近距离
      // this.controls.minDistance = 1;
      //设置相机距离原点的最远距离
      // this.controls.maxDistance = 1000;
      //是否开启右键拖拽
      this.controls.enablePan = true;
      //设置可旋转范围为上半球
      this.controls.minPolarAngle = Math.PI * 0; // radians
      this.controls.maxPolarAngle = Math.PI * 0.8; // radians

      // let iconGroup = new THREE.Group();
      for (let i = 0; i < this.iconList.length; i++) {
        this.addIcon(this.iconList[i]);
      }

      // 自适应监听
      let cavWidth = $('#loading').width();
      let cavHeight = $('#loading').height();
      window.addEventListener(
        'resize',
        function () {
          _this.camera.aspect = cavWidth / cavHeight;
          _this.camera.updateProjectionMatrix();
          _this.renderer.setSize(cavWidth, cavHeight);
          _this.render();
        },
        false
      );
    },
    //创建地形
    createTerrain(data) {
      let _this = this;
      let path = data.path;
      let name = data.name;
      let manager = new THREE.LoadingManager();
      let mtlLoader = new MTLLoader(manager);
      mtlLoader.setTexturePath('/static/model/');
      mtlLoader.setPath('/static/model/');
      mtlLoader.setCrossOrigin(true);
      mtlLoader.load(path + '.mtl', function (materials) {
        materials.preload();
        let objLoader = new OBJLoader(manager);
        objLoader.setPath('/static/model/');
        objLoader.setMaterials(materials);
        objLoader.load(
          path + '.obj',
          function (object) {
            object.traverse(function (child) {
              if (child instanceof THREE.Mesh) {
                object.name = name;
                object.scale.set(0.03, 0.03, 0.03);
              }
            });
            _this.scene.add(object);
            _this.render();
          },
          function (xhr) {
            //进度加载框
            $('.loading-page').css('display', 'flex');
            $('#container').css('display', 'none');
            if (xhr.lengthComputable) {
              let percentComplete = (xhr.loaded / xhr.total) * 100;
              let percent = Math.round(percentComplete, 2);
              if (percent == 100) {
                $('.loading-page .counter h1').html(percent + '%');
                // 为了给渲染留时间，所以此处设置延时，延时根据不同设备渲染速度不同修改
                setTimeout(function () {
                  $('.loading-page').css('display', 'none');
                  $('#container').css('display', 'block');
                }, 300);
              } else {
                $('.loading-page .counter h1').html(percent + '%');
              }
            }
          },
          function () {}
        );
      });
    },
    createTerrain2(data) {
      let _this = this;
      let path = data.path;
      let name = data.name;
      let manager = new THREE.LoadingManager();
      let mtlLoader = new MTLLoader(manager);
      mtlLoader.setTexturePath('/static/model/');
      mtlLoader.setPath('/static/model/');
      mtlLoader.setCrossOrigin(true);
      mtlLoader.load(path + '.mtl', function (materials) {
        materials.preload();
        let objLoader = new OBJLoader(manager);
        objLoader.setPath('/static/model/');
        objLoader.setMaterials(materials);
        objLoader.load(
          path + '.obj',
          function (object) {
            object.traverse(function (child) {
              if (child instanceof THREE.Mesh) {
                object.name = name;
                object.scale.set(0.03, 0.03, 0.03);
              }
            });
            _this.scene.add(object);
            _this.render();
          },
          () => {},
          () => {}
        );
      });
    },
    //创建模型
    createModel(data) {
      let _this = this;
      let path = data.path;
      let name = data.name;
      // 创建加载器
      let loader = new GLTFLoader();
      // 加载器加载方法，以及加载方法的一系列回调
      loader.load(
        '/static/model/' + path + '.gltf',
        function (gltf) {
          for (let j = 0; j < gltf.scene.children.length; ++j) {
            // let mesh = gltf.scene.children[j];
            let mesh = new THREE.Mesh(
              gltf.scene.children[j].geometry,
              gltf.scene.children[j].material
            );
            mesh.scale.set(0.03, 0.03, 0.03);
            _this.group.add(mesh); // 将模型引入three
          }
          // _this.group.add(gltf.scene);
          _this.group.name = name;
          _this.scene.add(_this.group);
          _this.render();
        },
        (xhr) => {
          $('.loading-page').css('display', 'flex');
          $('#container').css('display', 'none');
          if (xhr.lengthComputable) {
            let percentComplete = (xhr.loaded / xhr.total) * 100;
            let percent = Math.round(percentComplete, 2);
            if (percent == 100) {
              $('.loading-page .counter h1').html(percent + '%');
              // 为了给渲染留时间，所以此处设置延时，延时根据不同设备渲染速度不同修改
              setTimeout(function () {
                $('.loading-page').css('display', 'none');
                $('#container').css('display', 'block');
              }, 300);
            } else {
              $('.loading-page .counter h1').html(percent + '%');
            }
          }
        },
        () => {}
      );
    },
    //显示图标
    setIconState(name, state) {
      // let iconGroup = new THREE.Group();
      // iconGroup.name = name
      if (state) {
        for (let i = 0; i < this.iconList.length; i++) {
          if (this.iconList[i].name == name) {
            this.addIcon(this.iconList[i]);
          }
        }
      } else {
        $('#' + name).remove();
      }
    },
    //添加图标
    addIcon(object) {
      let point = new THREE.Vector3(
        Number(object.xAxis),
        Number(object.yAxis),
        Number(object.zAxis)
      );
      //创建 div
      var div = document.createElement('div');
      //设置 div 属性，如 id
      div.setAttribute('id', object.name);
      div.setAttribute('class', 'iconItemImg');
      div.setAttribute(
        'onClick',
        'iconClick(' +
          object.xAxis +
          ',' +
          object.xAxis +
          ',' +
          object.xAxis +
          ',' +
          object.type +
          ')'
      );
      let html = '<img src="' + object.imgUrl + '">';
      div.innerHTML = html;
      $('#iconBox').append(div);
      let halfWidth = window.innerWidth / 2;
      let halfHeight = window.innerHeight / 2;

      // 逆转相机求出二维坐标
      let vector = point.clone().project(this.camera);
      $('#' + object.name).css({
        left: vector.x * halfWidth + halfWidth - 20,
        top: -vector.y * halfHeight + halfHeight - 67,
        display: 'flex'
      });
    },
    iconClick(x, y, z, type) {
      if (type == 2) {
        //视频
        this.videoPosition = [x, y, z];
        this.showVideoBox = true;
        let halfWidth = window.innerWidth / 2;
        let halfHeight = window.innerHeight / 2;
        let point = new THREE.Vector3(Number(x), Number(y), Number(z));
        // 逆转相机求出二维坐标
        let vector = point.clone().project(this.camera);
        // 显示模型信息
        $('#videoBox').css({
          // left: vector.x * halfWidth + halfWidth - 221,
          // top: -vector.y * halfHeight + halfHeight - 355,
          left: vector.x * halfWidth + halfWidth + 10,
          top: -vector.y * halfHeight + halfHeight - 60 - 235
        });
      } else {
        //传感器
        this.position = [x, y, z];
        this.showInfoBox = true;
        let halfWidth = window.innerWidth / 2;
        let halfHeight = window.innerHeight / 2;
        let point = new THREE.Vector3(Number(x), Number(y), Number(z));
        // 逆转相机求出二维坐标
        let vector = point.clone().project(this.camera);
        // 显示模型信息
        $('#contentBox').css({
          // left: vector.x * halfWidth + halfWidth - 221,
          // top: -vector.y * halfHeight + halfHeight - 355,
          left: vector.x * halfWidth + halfWidth - 20 - 200,
          top: -vector.y * halfHeight + halfHeight - 67 - 355,
          display: 'flex'
        });
      }

      this.render();
    },
    // 时刻渲染
    animate() {
      if (this.loadtime < 1) {
        requestAnimationFrame(this.animate);
        // controls.update();
        this.renderer.render(this.scene, this.camera);
        this.loadtime += 0.002;
      } else {
        return;
      }
    },
    render() {
      let halfWidth = window.innerWidth / 2;
      let halfHeight = window.innerHeight / 2;
      for (let j = 0; j < this.iconList.length; j++) {
        // 逆转相机求出二维坐标
        let point = new THREE.Vector3(
          Number(this.iconList[j].xAxis),
          Number(this.iconList[j].yAxis),
          Number(this.iconList[j].zAxis)
        );
        let vector = point.clone().project(this.camera);
        $('#' + this.iconList[j].name).css({
          left: vector.x * halfWidth + halfWidth - 20,
          top: -vector.y * halfHeight + halfHeight - 67,
          display: 'flex'
        });
      }
      if (this.showInfoBox) {
        let divBox = new THREE.Vector3(
          Number(this.position[0]),
          Number(this.position[1]),
          Number(this.position[2])
        );
        let vector = divBox.clone().project(this.camera);
        $('#contentBox').css({
          // left: vector.x * halfWidth + halfWidth - 221,
          // top: -vector.y * halfHeight + halfHeight - 355,
          left: vector.x * halfWidth + halfWidth - 20 - 200,
          top: -vector.y * halfHeight + halfHeight - 67 - 355,
          display: 'flex'
        });
      }
      if (this.showVideoBox) {
        let divBox = new THREE.Vector3(
          Number(this.videoPosition[0]),
          Number(this.videoPosition[1]),
          Number(this.videoPosition[2])
        );
        let vector = divBox.clone().project(this.camera);
        $('#videoBox').css({
          // left: vector.x * halfWidth + halfWidth - 221,
          // top: -vector.y * halfHeight + halfHeight - 355,
          left: vector.x * halfWidth + halfWidth + 10,
          top: -vector.y * halfHeight + halfHeight - 60 - 235
        });
      }
      this.renderer.render(this.scene, this.camera);
    }
  },
  mounted() {
    this.init();
  },
  created() {
    window.iconClick = this.iconClick;
  },
  beforeDestroy() {
    window.removeEventListener('resize', {});
  }
};
</script>
<style lang="scss" scoped>
.homeModel {
  height: 100%;
  width: 100%;
}
#percent {
  color: red;
  font-size: 2vw;
  margin-top: 0;
}
.loading_txt {
  color: #fff;
  text-shadow: 2px 2px 2px #000;
}
.loading-page {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  background: url('~@/assets/images/home/tiankong.png') no-repeat;
  background-size: 100% 100%;
  z-index: 9;
}
.loading-page .counter {
  width: 100%;
  height: 100%;
  text-align: center;
  z-index: 9;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.model {
  width: 100%;
  height: 100%;
  position: relative;
  display: none;
}
#iconBox {
  position: absolute;
  top: 0;
  z-index: 99;
  /deep/ .iconItemImg {
    position: absolute;
    width: 40px;
    cursor: pointer;
    img {
      width: 100%;
    }
  }
}
.model /deep/ canvas {
  height: 100% !important;
  width: 100% !important;
  outline: none;
}
.contentBox {
  position: absolute;
  width: 440px;
  height: 32.531vh;
  padding: 1.855vh 0 1.391vh;
  background: url('~@/assets/images/home/divBox.png') no-repeat;
  background-size: 100% 100%;
  display: none;
  flex-direction: column;
  justify-content: space-between;
  .contentItem {
    padding: 0 24px;
    display: flex;
    align-items: center;
  }
  .first {
    font-size: 16px;
    color: #34ddde;
    display: flex;
    align-items: center;
    span {
      padding-right: 15px;
    }
  }
  .second {
    height: 3.337vh;
    padding: 0;
    display: flex;
    align-items: center;
    .secItem {
      width: 25%;
      height: 100%;
      font-size: 12px;
      color: #fff;
      border-right: 1px solid rgba(53, 62, 81, 0.5);
      display: flex;
      align-items: center;
      flex-direction: column;
      justify-content: space-evenly;
      &:last-child {
        border: none;
      }
    }
  }
  .third {
    height: 14.829vh;
  }
  .fourth {
    height: 4.635vh;
    color: rgba(255, 255, 255, 0.85);
    border-top: 1px solid #10424c;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
.videoBox {
  position: absolute;
  width: 408px;
  height: 228px;
  background-color: #07222c;
  border: solid 1px #1bccc1;
  padding: 4px;
  video {
    width: 100%;
    height: 100%;
  }
  &::after {
    position: absolute;
    left: -8px;
    top: 50%;
    content: '';
    width: 14px;
    height: 14px;
    background: #07222c;
    border-top: 1px solid #1bccc1;
    border-left: 1px solid #1bccc1;
    transform: rotate(-45deg);
  }
}
.textGraGreen {
  font-size: 16px;
  font-weight: bold;
  background: linear-gradient(to top, #25f2f5, #fff);
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
}
.textGraYellow {
  font-size: 16px;
  background: linear-gradient(to top, #ff600a, #fff);
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
}
</style>
