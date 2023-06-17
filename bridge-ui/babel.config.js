module.exports = {
  presets: ['@vue/cli-plugin-babel/preset'],
  plugins: [
    // ... 你的其他 babel 插件
    [
      'import',
      {
        libraryName: '@amap/amap-vue',
        libraryDirectory: 'lib',
        camel2DashComponentName: true
      }
    ]
  ]
};
