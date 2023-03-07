

飞书云文档笔记：https://f6q9xnlo2k.feishu.cn/docx/QSJGd51Kzob6o3x2whFcpU6wneg





# 导学

分布式：数据库、消息队列、RPC框架

分布式的根基在于网络编程。



## 课程特色

- 重基础：Netty的底层是 NIO，掌握好 NIO
- 重代码：理论往往是天花乱坠的。最好辅以代码证明。
- 无废话：紧凑，实用。
- 案例经典
  - 聊天室案例：自定义协议实现，并加入可扩展的序列化机制。
  - RPC案例：采用代理来封装网络远程调用，并使用 Promise 来处理异步结果。外行看界面，内行看代码。



## 课程内容 157P

分为 5 大部分

- NIO 编程。讲解 NIO 的Selector、ByteBuffer 和 Channel 三大组件。
- Netty 入门学习。介绍 EventLoop、Channel、Future、Pipeline、Handler、ByteBuf 等重要组件。
- Netty 进阶学习。介绍粘包半包的解决办法、协议的设计、序列化知识、使用聊天室案例串联。为了专注Netty编程，没有引入第三方框架，如Spring、websocket等，以免分散注意力。
- Netty 常见参数的学习以及优化。
- 源码分析。侧重 Netty 的服务器启动、建立连接、读取数据、EventLoop处理事件的流程，不牵扯更多组件的源码。



## 收获

- 使用 Netty 开发基本网络应用程序
- 彻底理解阻塞、非阻塞的区别，并跟 Netty、NIO的编码联系起来
- 多路复用在服务器开发时的优势，为什么在此基础上还要加多线程
- Netty 中是如何实现异步的，异步处理的优势是什么
- Netty 中是如何管理线程的，EventLoop 如何运作
- Netty 中是如何管理内存的，ByteBuf 特点和分配时机
- 看源码、调试的一些技巧，会跟源码





# Netty 三大组件

## Channel 与 Buffer（通道与缓冲区）



**通道负责传输，缓冲区负责存储。**

常见的 `Channel` 有以下四种，其中 FileChannel 主要用于文件传输，其余三种用于网络通信。

- FileChannel
- DatagramChannel
- SocketChannel
- ServerSocketChannel

`Buffer` 有以下几种，其中使用较多的是 ByteBuffer

- **ByteBuffer**
  - MappedByteBuffer
  - DirectByteBuffer
  - HeapByteBuffer
- ShortBuffer
- IntBuffer
- LongBuffer
- FloatBuffer
- DoubleBuffer
- CharBuffer



## Selector









# ByteBuffer







