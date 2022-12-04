

# Nodejs基础

[官网](https://nodejs.org/zh-cn/)



## 初始Nodejs

### 思考

- 前端基础：HTML（结构）、CSS（样式）、Javascript（交互行为）
- 浏览器中的 Javascript 的组成部分

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20221204103959356.png)



- 为什么 JavaScript 可以在浏览器中被执行

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20221204104636509.png)

- 为什么 JavaScript 可以操作 DOM 和 BOM

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20221204104940020.png)

- 浏览器中的 JavaScript 运行环境

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20221204105049799.png)

- JavaScript 能否做后端开发



### 简介

- 什么是 Node.js

> Node.js® is a JavaScript runtime built on Chrome's V8 JavaScript engine
>
> Node.js® 是一个基于 Chrome V8 引擎 的 JavaScript 运行时环境



- Node.js 中的 JavaScript 运行环境

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20221204105556619.png)



- Node.js 可以做什么
  - 基于 [Express 框架](http://www.expressjs.com.cn/)，可以快速构建 Web 应用
  - 基于 [Electron 框架](https://electronjs.org/)，可以构建跨平台的桌面应用
  - 基于 [restify 框架 ](http://restify.com/)，可以快速构建 API 接口项目
  - 读写和操作数据库、创建实用的命令行工具辅助前端开发、etc…
- 学习路径
> 浏览器中的 JavaScript 学习路径：JavaScript 基础语法 + 浏览器内置 API（DOM + BOM） + 第三方库（jQuery、art-template 等）
>
> Node.js 的学习路径：JavaScript 基础语法 + Node.js 内置 API 模块（fs、path、http等）+ 第三方 API 模块（express、mysql 等）

### 环境安装
如果希望通过 Node.js 来运行 Javascript 代码，则必须在计算机上安装 Node.js 环境才行。

```shell
# 可查看已安装的 Node.js 的版本号
node –v

# 执行 JavaScript 代码
node 要执行的js文件的路径
```

## fs 文件系统模块
fs 模块是 Node.js 官方提供的、用来操作文件的模块。它提供了一系列的方法和属性。


### 读取文件

```javascript
fs.readFile(path[, options], callback)
```

`path`：文件路径
`options`：配置选项，若是字符串则指定编码格式
`callback`：回调函数



```javascript
const fs = require('fs')

fs.readFile('./files/1.txt', 'utf-8', function(err, data) => {
  if(err) {
    return console.log('failed!' + err.message)
  }
  console.log('content:' + data)
})

```





### 写入文件

```javascript
fs.writeFile(file, data[, options], callback)
```
`file`：文件路径
`data`：写入内容
`options`：配置选项，包含 `encoding, mode, flag`；若是字符串则指定编码格式
`callback`：回调函数
```javascript
const fs = require('fs')
fs.writeFile('./files/2.txt', 'Hello Nodejs', function (err) {
  if (err) {
    return console.log('failed!' + err.message)
  }
  console.log('success!')
})
```











## path 路径模块



## http 模块





# Express





# 数据库和身份认证





# 大事件后台 API 项目

























