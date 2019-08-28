const path = require('path');

const resolve = dir => path.join(__dirname, dir)

module.exports = {
    publicPath: process.env.NODE_ENV === 'production' ? '/' : '/',
    lintOnSave: false,
    chainWebpack: config => {
        config.resolve.alias
            .set('@', resolve('src'))
            .set('_c', resolve('src/components'))
    },
    devServer: {
        port: 3000,
        open: true
    },
    chainWebpack(config) {
        config.module
            .rule('svg')
            .exclude.add(resolve('src/icons'))
            .end();
        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(resolve('src/icons'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            })
            .end();
    }
    // ,devServer: {
    //   proxy: {
    //     '/json': {
    //       target: 'http://localhost:9000',
    //       ws: true,
    //       changeOrigin: true
    //     }
    //   }
    // }
};