/* eslint-disable prettier/prettier */
const path = require('path');
const webpack = require('webpack');
// const IS_PROD = ["production", "prod"].includes(process.env.NODE_ENV);

function resolve(dir) {
  return path.join(__dirname, './', dir);
}

module.exports = {
  publicPath: './', // 默认'/'，部署应用包时的基本 URL
  /* 输出文件目录：在npm run build时，生成文件的目录名称 */
  outputDir: 'dist',
  /* 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录 */
  assetsDir: 'assets',
  /* 代码保存时进行eslint检测 */
  lintOnSave: false,
  /* 打包后能看源码 */
  productionSourceMap: false,
  // 图标设置
  pwa: {
    iconPaths: {
      favicon32: 'favicon.ico',
      favicon16: 'favicon.ico',
      appleTouchIcon: 'favicon.ico',
      maskIcon: 'favicon.ico',
      msTileImage: 'favicon.ico'
    }
  },
  runtimeCompiler: true, //关键点在这  原来的 Compiler 换成了 runtimeCompiler
  // 调整内部的 webpack 配置。
  chainWebpack: (config) => {
    // 配置别名
    config.resolve.alias
      .set('@', resolve('src'))
      .set('api', resolve('src/api'))
      .set('assets', resolve('src/assets'))
      .set('components', resolve('src/components'))
      .set('router', resolve('src/router'))
      .set('utils', resolve('src/utils'))
      .set('static', resolve('public/static'))
      .set('store', resolve('src/store'))
      .set('views', resolve('src/views'));
    config.entry.app = ['babel-polyfill', './src/main.js'];
  },
  configureWebpack: {
    //支持jquery
    plugins: [
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        'windows.jQuery': 'jquery'
      })
    ]
  },
  // SASS配置
  css: {
    extract: false, //false表示开发环境,true表示生成环境
    sourceMap: false,
    loaderOptions: {
      // 	sass: {
      // 		prependData: `
      //   @import "@/assets/styles/public.scss";
      // `,
      // 	},
      postcss: {
        plugins: [
          require('postcss-px-to-viewport')({
            unitToConvert: 'px', // 需要转换的单位，默认为"px"
            viewportWidth: 1920, // 视窗的宽度，对应pc设计稿的宽度，一般是1920
            viewportHeight: 1080, // 视窗的高度，对应的是我们设计稿的高度,我做的是大屏监控,高度就是1080
            unitPrecision: 4, // 单位转换后保留的精度
            propList: [
              // 能转化为vw的属性列表
              '*'
            ],
            viewportUnit: 'vw', // 希望使用的视口单位
            fontViewportUnit: 'vw', // 字体使用的视口单位
            selectorBlackList: [], // 需要忽略的CSS选择器，不会转为视口单位，使用原有的px等单位。
            minPixelValue: 2, // 设置最小的转换数值，如果为1的话，只有大于1的值会被转换
            mediaQuery: false, // 媒体查询里的单位是否需要转换单位
            replace: true, // 是否直接更换属性值，而不添加备用属性
            // exclude: [/^node_modules$/], // 忽略某些文件夹下的文件或特定文件，例如 'node_modules' 下的文件
            exclude: []
          })
        ]
      },
      sass: {
        prependData: `@import "@/assets/css/elStyle.scss";`
      }
    }
  },
  // 配置 webpack-dev-server 行为。
  devServer: {
    open: true,
    host: '0.0.0.0',
    port: 8080,
    https: false,
    hotOnly: false,
    proxy: {
      '/bridge': {
        target: 'http://121.36.52.154:9990', //这里写上后台的接口
        /* 允许跨域 */
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    } // string | Object
    // before: app => {}
  }
};
