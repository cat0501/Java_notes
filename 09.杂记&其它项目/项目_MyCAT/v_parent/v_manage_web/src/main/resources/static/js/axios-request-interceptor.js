// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求向header添加jwt
    config.data=JSON.stringify(config.data);
    config.headers={
        'Content-Type':'application/x-www-form-urlencoded'
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});