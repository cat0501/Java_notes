# 写在前面

## 推荐

### 视频

- [尚硅谷宋红康JVM全套教程（详解java虚拟机）](https://www.bilibili.com/video/BV1PJ411n7xZ?p=1)
  - 内存与垃圾回收篇
  - 字节码与类的加载篇
  - 性能监控与调优篇




### 书籍

- 《offer 来了》

- 《深入理解java虚拟机》（周志明）
  - Java内存区域与内存溢出异常

### 博客

- [宋红康JVM语雀笔记📒](https://www.yuque.com/u21195183/jvm/lep6m9)



## 总结（核心点简述）





# 内存与垃圾回收篇（上）

## 1 JVM 与 Java 体系结构

### 1.1 前言

你是否也遇到过这些问题？

- 运行着的**线上系统突然卡死，系统无法访问**，甚至直接 **OOM**

- 想解决线上 **JVM GC问题**，但却无从下手
- 新项目上线，对**各种 JVM 参数设置**一脸茫然，直接默认吧然后就JJ了
- 每次**面试**之前都要重新背一遍JVM的一些原理概念性的东西，然而面试官却经常问你在实际项目中如何调优VM参数，如何解决GC、OOM等问题，一脸懵逼

大部分Java开发人员，除会在项目中使用到与Java平台相关的各种高精尖技术，对于Java技术的核心Java虚拟机了解甚少。

<br>

我们为什么要学习 `JVM`？

- 面试的需要（BATJ、TMD，PKQ等面试都爱问）
- 中高级程序员必备技能
  - 项目管理、调优的需求
- 追求极客的精神
  - 比如：垃圾回收算法、JIT、底层原理



垃圾收集机制为我们打理了很多繁琐的工作，大大提高了开发的效率。但是，垃圾收集也不是万能的，懂得JVM内部的内存结构、工作机制，是设计高扩展性应用和诊断运行时问题的基础，也是Java工程师进阶的必备能力。

### 1.2 参考书目

《深入理解Java 虚拟机》



### 1.3 Java及 JVM 简介

**TIOBE语言热度排行榜：**[**index | TIOBE - The Software Quality Company**](https://tiobe.com/tiobe-index/)

| Programming Language | 2021 | 2016 | 2011 | 2006 | 2001 | 1996 | 1991 | 1986 |
| -------------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| C                    | 1    | 2    | 2    | 2    | 1    | 1    | 1    | 1    |
| Java                 | 2    | 1    | 1    | 1    | 3    | 26   | -    | -    |
| Python               | 3    | 5    | 6    | 8    | 27   | 19   | -    | -    |
| C++                  | 4    | 3    | 3    | 3    | 2    | 2    | 2    | 8    |
| C#                   | 5    | 4    | 5    | 7    | 13   | -    | -    | -    |
| Visual Basic         | 6    | 13   | -    | -    | -    | -    | -    | -    |
| JavaScript           | 7    | 8    | 10   | 9    | 10   | 32   | -    | -    |
| PHP                  | 8    | 6    | 4    | 4    | 11   | -    | -    | -    |
| SQL                  | 9    | -    | -    | -    | -    | -    | -    | -    |
| R                    | 10   | 17   | 31   | -    | -    | -    | -    | -    |
| Lisp                 | 34   | 27   | 13   | 14   | 17   | 7    | 4    | 2    |
| Ada                  | 36   | 28   | 17   | 16   | 20   | 8    | 5    | 3    |
| (Visual) Basic       | -    | -    | 7    | 6    | 4    | 3    | 3    | 5    |

世界上没有最好的编程语言，只有最适用于具体应用场景的编程语言。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605121547540.png)



字节码

- 我们平时说的 java字节码，指的是用 java语言编译成的字节码。准确的说任何能在 jvm 平台上执行的字节码格式都是一样的。所以应该统称为：jvm字节码。

如何真正搞懂 JVM？

- Java虚拟机非常复杂，要想真正理解它的工作原理，最好的方式就是自己动手编写一个！
- 天下事有难易乎？ 为之，则难者亦易矣；不为，则易者亦难矣。
- 推荐书籍《自己动手写Java虚拟机》

### 1.4 Java发展的重大事件



![img](./img/jdk.png)



### 1.5 虚拟机与Java虚拟机



Java虚拟机

- Java技术的核心就是 Java虚拟机（JVM，Java Virtual Machine），因为所有的Java程序都运行在Java虚拟机内部。
- 作用：Java虚拟机就是二进制字节码的运行环境，负责**装载**字节码到其内部，**解释/编译**为对应平台上的机器指令执行。每一条 Java 指令，Java虚拟机规范中都有详细定义，如怎么取操作数，怎么处理操作数，处理结果放在哪里。

- 特点
  - 一次编译，到处运行
  - 自动内存管理
  - 自动垃圾回收功能



### 1.6 JVM 的整体结构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605131230324.png)







- `HotSpot VM` 是目前市面上高性能虚拟机的代表作之一。

- 它采用解释器与即时编译器并存的架构。

- 在今天，Java 程序的运行性能早已脱胎换骨，已经达到了可以和  C/C++ 程序一较高下的地步。



### 1.7 Java代码执行流程 ⭐️

### 1.8 JVM的架构模型

### 1.9 JVM的生命周期

#### 虚拟机的启动

Java 虚拟机的启动是通过引导类加载器（bootstrap class loader）创建一个初始类（initial class）来完成的，这个类是由虚拟机的具体实现指定的。

#### 虚拟机的运行

- 一个运行中的Java虚拟机有着一个清晰的任务：执行Java程序。

- 程序开始执行时他才运行，程序结束时他就停止。

- **执行一个所谓的Java程序的时候，真真正正在执行的是一个叫做Java虚拟机的进程。**

#### 虚拟机的退出

有如下的几种情况：

- 程序正常执行结束；

- 程序在执行过程中遇到了异常或错误而异常终止；

- 由于操作系统出现错误而导致Java虚拟机进程终止；

- 某线程调用Runtime类或system类的exit方法，或Runtime类的halt方法，并且Java安全管理器也允许这次exit或halt操作；

- 除此之外，JNI（Java Native Interface）规范描述了用JNI Invocation API来加载或卸载 Java虚拟机时，Java虚拟机的退出情况。

### 1.10 JVM的发展历程

后续补充。。。



主要以Oracle HotSpot VM为默认虚拟机。



## 2 类加载子系统

### 2.1 内存结构概述

- Class文件
- 类加载子系统
- 运行时数据区 （五大部分）
  - 方法区
  - 堆
  - 程序计数器
  - 虚拟机栈
  - 本地方法栈
- 执行引擎
- 本地方法接口
- 本地方法库



如果自己想手写一个Java虚拟机的话，主要考虑哪些结构呢？

- 类加载器

- 执行引擎

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605131230324.png)

### 2.2 类加载器与类的加载过程

#### 类加载器子系统作用

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605132610321.png)



- 类加载器子系统负责从文件系统或者网络中**加载 `Class` 文件**，Class 文件在**文件开头有特定的文件标识**。

- `ClassLoader` 只负责 class 文件的加载，至于它是否可以运行，则由 `Execution Engine` 决定。

- 加载的类信息存放于一块称为方法区的内存空间。除了类的信息外，方法区中还会存放运行时常量池信息，可能还包括字符串字面量和数字常量（这部分常量信息是Class文件中常量池部分的内存映射）

#### 类加载器 ClasLoader角色（快递员角色）

- class file 存在于本地硬盘上
- class file 加载到 JVM 中，被称为 DNA元数据模板，放在方法区。
- 实例化出多个实例

#### 加载阶段

#### 链接阶段

#### 初始化阶段

### 2.3 类加载器分类

JVM支持两种类型的类加载器 ，引导类加载器（Bootstrap ClassLoader）和自定义类加载器（User-Defined ClassLoader）。

无论类加载器的类型如何划分，在程序中我们最常见的类加载器始终只有3个，如下所示：



四者之间的关系是包含关系。不是上层下层，也不是子父类的继承关系。



#### 虚拟机自带的加载器⭐️

##### 启动类加载器（引导类加载器，Bootstrap ClassLoader）

- 并不继承自ava.lang.ClassLoader，没有父加载器。
- 出于安全考虑，Bootstrap启动类加载器只加载包名为 `java`、`javax`、`sun`等开头的类



##### 扩展类加载器（Extension ClassLoader）

- 父类加载器为启动类加载器

##### 应用程序类加载器（系统类加载器，AppClassLoader）

- 父类加载器为扩展类加载器

- 该类加载器是程序中默认的类加载器，一般来说，Java应用的类都是由它来完成加载



### 2.4 ClassLoader 使用说明

ClassLoader 类是一个抽象类，其后所有的类加载器都继承自 ClassLoader（不包括启动类加载器）





### 2.5 双亲委派机制 ⭐️

Java虚拟机对 class 文件采用的是**按需加载**的方式，也就是说当需要使用该类时才会**将它的 class 文件加载到内存生成 class 对象**。

而且**加载某个类的 class 文件时**，Java虚拟机采用的是双亲委派模式，即**把请求交由父类处理**，它是一种任务委派模式。

#### 工作原理

- 1）如果一个类加载器收到了类加载请求，它并不会自己先去加载，而是**把这个请求委托给父类的加载器去执行**；

- 2）如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归，**请求最终将到达顶层的启动类加载器**；

- 3）如果父类加载器可以完成类加载任务，就成功返回，倘若父类加载器无法完成此加载任务，子加载器才会尝试自己去加载，这就是双亲委派模式。
  - 父类加载器加载完成了，就不会向下委托



#### 优势

- 避免类的重复加载

- 保护程序安全，防止核心 API 被随意篡改 
  - 自定义类：java.lang.String
  - 自定义类：java.lang.ShkStart（报错：阻止创建 java.lang开头的类）

#### 沙箱安全机制

自定义 String 类，但是在加载自定义 String类的时候会率先使用引导类加载器加载，而引导类加载器在加载的过程中会先加载 jdk 自带的文件（rt.jar包中java\lang\String.class），报错信息说没有 main方法，就是因为加载的是 `rt.jar` 包中的 String类。

这样可以保证对 java核心源代码的保护，这就是沙箱安全机制。



## 3 运行时数据区之程序计数器（PC寄存器）

程序计数器(Program Counter Register)是一块较小的内存空间，可以看作是当前线程所执行的字节码的行号指示器。字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令。

为了线程切换后能恢复到正确的执行位置，每条线程都需要有一个独立的程序计数器，各线程计数器互不影响独立存储，称这类内存区域为“线程私有”的内存。

此内存区域是唯一一个在《Java虚拟机规范》中没有规定任何 `OutOfMemoryError` 情况的区域。

![](./img/PC寄存器.png)





## 4 虚拟机栈 ⭐️⭐️⭐️⭐️⭐️

### 4.1 虚拟机栈概述

与程序计数器一样，Java虚拟机栈也是**线程私有的**，生命周期与线程相同。

虚拟机栈描述的是Java方法执行的线程内存模型：每个方法被执行的时候，Java虚拟机都会同步创建一个栈帧。每一个方法被调用直至执行完毕，对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。

Java 内存区域笼统地分为堆（Heap）内存和栈（Stack）内存。

- 这种划分方式直接继承自传统的C、C++程序的内存布局结构。
- Java语言里，这种说法不全面。但确实是最重要的2部分。



#### 虚拟机栈出现的背景

由于跨平台性的设计，Java的指令都是根据栈来设计的。不同平台CPU架构不同，所以不能设计为基于寄存器的。

**优点是跨平台，指令集小，编译器容易实现，缺点是性能下降，实现同样的功能需要更多的指令。**

#### 内存中的栈与堆

栈是运行时的单位，而堆是存储的单位。

- 栈解决程序的运行问题，即程序如何执行，或者说如何处理数据。

- 堆解决的是数据存储的问题，即数据怎么放，放哪里。

#### 虚拟机栈基本内容

（1）Java虚拟机栈是什么？

- Java虚拟机栈（Java Virtual Machine Stack），早期也叫 Java 栈。

- 每个线程在创建时都会创建一个虚拟机栈，其内部保存一个个的**栈帧**（Stack Frame），对应着一次次的Java方法调用，是线程私有的。

（2）生命周期

- 生命周期和线程一致

（3）作用

- 主管Java程序的运行，它保存方法的局部变量、部分结果，并参与方法的调用和返回。

（4）栈的特点

- JVM直接对Java栈的操作只有两个：

  - 每个方法执行，伴随着进栈（入栈、压栈）

  - 执行结束后的出栈工作

- 对于栈来说不存在垃圾回收问题（栈存在溢出的情况）



（5）面试题：开发中遇到哪些异常？



#### 栈中可能出现的异常

Java 虚拟机规范允许 **Java栈的大小是动态的或者是固定不变的**。

- 如果采用固定大小的Java虚拟机栈，那每一个线程的Java虚拟机栈容量可以在线程创建的时候独立选定。如果线程请求分配的栈容量超过Java虚拟机栈允许的最大容量，Java虚拟机将会抛出一个**StackOverflowError** 异常。 

- 如果Java虚拟机栈可以动态扩展，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存去创建对应的虚拟机栈，那Java虚拟机将会抛出一个 **OutOfMemoryError** 异常。 

```java
public static void main(String[] args) {
    test();
}
public static void test() {
    test();
}
//抛出异常：Exception in thread"main"java.lang.StackoverflowError
//程序不断的进行递归调用，而且没有退出条件，就会导致不断地进行压栈。
```



**设置栈内存大小**

我们可以使用参数 `-Xss` 选项来设置线程的最大栈空间，栈的大小直接决定了函数调用的最大可达深度。

```java
public class StackDeepTest{ 
    private static int count=0; 
  
    public static void recursion(){
        count++; 
        recursion(); 
    }
  
    public static void main(String args[]){
        try{
            recursion();
        } catch (Throwable e){
            System.out.println("deep of calling="+count); 
            e.printstackTrace();
        }
    }
}
```



### 4.2 栈的存储单位

#### 栈中存储什么？

每个线程都有自己的栈，栈中的数据都是以栈帧（Stack Frame）的格式存在。

在这个线程上正在执行的每个方法都各自对应一个栈帧（Stack Frame）。

栈帧是一个内存区块，是一个数据集，维系着方法执行过程中的各种数据信息。



#### 栈运行原理

JVM直接对 Java 栈的操作只有两个，就是对**栈帧的压栈和出栈，遵循“先进后出”/“后进先出”原则**。



如果在该方法中调用了其他方法，对应的新的栈帧会被创建出来，放在栈的顶端，成为新的当前帧。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605152854132.png)

不同线程中所包含的栈帧是不允许存在相互引用的，即不可能在一个栈帧之中引用另外一个线程的栈帧。

如果当前方法调用了其他方法，方法返回之际，当前栈帧会传回此方法的执行结果给前一个栈帧，接着，虚拟机会丢弃当前栈帧，使得前一个栈帧重新成为当前栈帧。

Java方法有两种返回函数的方式，一种是正常的函数返回，使用return指令；另外一种是抛出异常。不管使用哪种方式，都会导致栈帧被弹出。

```java
public class CurrentFrameTest{
    public void methodA(){
        system.out.println("当前栈帧对应的方法->methodA");
        methodB();
        system.out.println("当前栈帧对应的方法->methodA");
    }
    public void methodB(){
        System.out.println("当前栈帧对应的方法->methodB");
    }
}
```



#### 栈帧的内部结构

每个栈帧中存储着：

- 局部变量表（Local Variables）

- 操作数栈（operand Stack）（或表达式栈）
- 动态链接（DynamicLinking）（或指向运行时常量池的方法引用）
- 方法返回地址（Return Address）（或方法正常退出或者异常退出的定义）
- 一些附加信息

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605154248787.png)



### 4.3 局部变量表 (Local Variables)

局部变量表也被称之为 局部变量数组或本地变量表。

- **定义为一个数字数组，主要用于存储方法参数和定义在方法体内的局部变量**，这些数据类型包括各类基本数据类型、对象引用（reference），以及returnAddress类型。 
-  **局部变量表所需的容量大小是在编译期确定下来的**，并保存在方法的Code属性的`maximum local variables`数据项中。在方法运行期间是不会改变局部变量表的大小的。 

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605163146687.png)

-  **方法嵌套调用的次数由栈的大小决定。**一般来说，栈越大，方法嵌套调用次数越多。对一个函数而言，它的参数和局部变量越多，使得局部变量表膨胀，它的栈帧就越大，以满足方法调用所需传递的信息增大的需求。进而函数调用就会占用更多的栈空间，导致其嵌套调用次数就会减少。 

- **局部变量表中的变量只在当前方法调用中有效。**在方法执行时，虚拟机通过使用局部变量表完成参数值到参数变量列表的传递过程。当方法调用结束后，随着方法栈帧的销毁，局部变量表也会随之销毁。 



#### 静态变量与局部变量的对比

我们知道类变量表有两次初始化的机会，第一次是在“准备阶段”，执行系统初始化，对类变量设置零值，另一次则是在“初始化”阶段，赋予程序员在代码中定义的初始值。

和类变量初始化不同的是，局部变量表不存在系统初始化的过程，这意味着一旦定义了局部变量则必须人为的初始化，否则无法使用。

```java
public void test(){
    int i;
    System. out. println(i);
}
```

这样的代码是错误的，没有赋值不能够使用。



#### 补充说明

在栈帧中，与性能调优关系最为密切的部分就是前面提到的局部变量表。在方法执行时，虚拟机使用局部变量表完成方法的传递。

**局部变量表中的变量也是重要的垃圾回收根节点，只要被局部变量表中直接或间接引用的对象都不会被回收。**



### 4.4 操作数栈（Operand Stack）

每一个独立的栈帧除了包含局部变量表以外，还包含一个后进先出（Last-In-First-Out）的 操作数栈，也可以称之为表达式栈（Expression Stack）

操作数栈，在方法执行过程中，根据字节码指令，往栈中写入数据或提取数据，即入栈（push）和 出栈（pop）。

- 某些字节码指令将值压入操作数栈，其余的字节码指令将操作数取出栈。使用它们后再把结果压入栈
- 比如：执行复制、交换、求和等操作



### 4.5 代码追踪

代码

```java
public void testAddOperation(){
    byte i = 15; 
    int j = 8; 
    int k = i + j;
}
```

字节码指令信息

> 使用javap 命令反编译class文件：`javap -v 类名.class`

```shell
public void testAddOperation(); 
    Code:
    0: bipush 15
    2: istore_1 
    3: bipush 8
    5: istore_2 
    6:iload_1 
    7:iload_2 
    8:iadd
    9:istore_3 
    10:return
```

操作数栈，主要用于保存计算过程的中间结果，同时作为计算过程中变量临时的存储空间。



### 栈的相关面试题

- 举例栈溢出的情况？（StackOverflowError） 
  -   通过 -Xss设置栈的大小

- 调整栈大小，就能保证不出现溢出么？
  -   不能保证不溢出

- 分配的栈内存越大越好么？
  -   不是，一定时间内降低了OOM概率，但是会挤占其它的线程空间，因为整个空间是有限的。

- 垃圾回收是否涉及到虚拟机栈？ 
  - 不会
- 方法中定义的局部变量是否线程安全？
  -   具体问题具体分析。如果对象是在内部产生，并在内部消亡，没有返回到外部，那么它就是线程安全的，反之则是线程不安全的。

| 运行时数据区 | 是否存在Error | 是否存在GC |
| ------------ | ------------- | ---------- |
| 程序计数器   | 否            | 否         |
| 虚拟机栈     | 是（SOE）     | 否         |
| 本地方法栈   | 是            | 否         |
| 方法区       | 是（OOM）     | 是         |
| 堆           | 是            | 是         |



## 5 本地方法接口和本地方法栈

本地接口的作用是融合不同的编程语言为Java所用，它的初衷是融合C/C++程序。



**Java虚拟机栈用于管理Java方法（也就是字节码）的调用，而本地方法栈用于管理本地（Native）方法的调用。**

本地方法是使用C语言实现的。

它的具体做法是Native Method Stack中登记native方法，在Execution Engine 执行时加载本地方法库。

在Hotspot JVM中，直接将本地方法栈和虚拟机栈合二为一。



## 6 堆 Heap⭐️⭐️⭐️⭐️⭐️

### 6.1 核心概述

- 一个JVM实例只存在一个堆内存，堆（Java Heap）是**虚拟机管理的内存中最大的一块**。被**所有线程共享**。

- Java 堆区在 JVM 启动的时候即被创建，其空间大小也就确定了。

- 堆是GC（Garbage Collection，垃圾收集器）**执行垃圾回收的重点区域**。故也称为“GC堆”。

- 此内存区域的唯一目的就是**存放对象实例**。“几乎”所有的对象实例都在这里分配内存。

- “新生代、老年代”等概念是一部分垃圾收集器的共同特性或者说是设计风格而已，并非某个Java虚拟机具体实现的固有布局。更不是虚拟机规范里对Java堆的进一步细致划分。

- Java堆既可以被实现成固定大小的，也可以是扩展的。当前主流的Java虚拟机都是按照**可扩展**来实现的（通过参数`-Xmx`和`-Xms`设定）。如果堆中没有内存完成实例分配，并且也无法完成扩展，那将会抛出OOM异常。



#### 堆内存细分

Java 7及之前堆内存逻辑上分为三部分：新生区 + 养老区 + **永久区**

- Young Generation Space 新生区 Young/New 
  - 又被划分为Eden区和Survivor区

- Tenure generation space 养老区 Old/Tenure

- Permanent Space 永久区 Perm

<br>

Java 8及之后堆内存逻辑上分为三部分：新生区 + 养老区 + **元空间**

- Young Generation Space 新生区 Young/New 
  - 又被划分为Eden区和Survivor区

- Tenure generation space 养老区 Old/Tenure

- Meta Space 元空间 Meta

约定：新生区（代）<=>年轻代 、 养老区<=>老年区（代）、 永久区<=>永久代





### 6.2 设置堆内存大小与OOM

#### 堆空间大小设置

Java堆区用于存储Java对象实例，堆的大小在JVM启动时就已经设定好了，大家可以通过选项"-Xmx"和"-Xms"来进行设置。

通常会将-Xms和-Xmx两个参数配置相同的值，其目的是**为了能够在 Java垃圾回收机制清理完堆区后不需要重新分隔计算堆区的大小，从而提高性能**。

默认情况下

- 初始内存大小：物理电脑内存大小 / 64

- 最大内存大小：物理电脑内存大小 / 4



```java
public class HeapTest {
    public static void main(String[] args) {
        // Java虚拟机中的堆内存容量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        // Java虚拟机中的最大堆内存容量
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms: " + initialMemory + "M");
        System.out.println("-Xmx: " + maxMemory + "M");
    }
}
```

查看设置的参数

- 方式1：jps / jstat -gc 进程id
- 方式2：-XX:+PrintFCDetails

#### OutOfMemory举例

```java
public class OOMTest {
    public static void main(String[]args){
        ArrayList<Picture> list = new ArrayList<>();
        while(true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024*1024)));
        }
    }
}
```

打印结果

```java
Exception in thread "main" java.lang.OutofMemoryError: Java heap space
    at com.atguigu. java.Picture.<init>(OOMTest. java:25)
    at com.atguigu.java.O0MTest.main(OOMTest.java:16)
```



### 6.3 年轻代与老年代

存储在JVM中的Java对象可以被划分为两类：

- 一类是生命周期较短的瞬时对象，这类对象的创建和消亡都非常迅速

- 另外一类对象的生命周期却非常长，在某些极端的情况下还能够与JVM的生命周期保持一致



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605185004793.png)

- 默认`-XX:NewRatio=2`，表示新生代占1，老年代占2，新生代占整个堆的1/3

<br>

**几乎所有的 Java对象都是在Eden区被new出来的**。绝大部分的Java对象的销毁都在新生代进行了。



### 6.4 图解对象分配过程

```bash
1.  new的对象先放伊甸园区。此区有大小限制。 
2.  当伊甸园的空间填满时，程序又需要创建对象，JVM的垃圾回收器将对伊甸园区进行垃圾回收（MinorGC），将伊甸园区中的不再被其他对象所引用的对象进行销毁。再加载新的对象放到伊甸园区。 
3.  然后将伊甸园中的剩余对象移动到幸存者0区。 
4.  如果再次触发垃圾回收，此时上次幸存下来的放到幸存者0区的，如果没有回收，就会放到幸存者1区。 
5.  如果再次经历垃圾回收，此时会重新放回幸存者0区，接着再去幸存者1区。 

6.  啥时候能去养老区呢？可以设置次数。默认是15次。 
  ○ 可以设置参数：进行设置-Xx:MaxTenuringThreshold= N
7.  在养老区，相对悠闲。当养老区内存不足时，再次触发GC：Major GC，进行养老区的内存清理 
8.  若养老区执行了Major GC之后，发现依然无法进行对象的保存，就会产生OOM异常。 
```



![](./img/GC.png)

- 伊甸园区的对象先往to区放（空的）

- 年龄计数器达到15晋升老年代

- 总结
  - 针对幸存者s0，s1区的总结：复制之后有交换，谁空谁是to
  - 关于垃圾回收：频繁在新生区收集，很少在老年代收集，几乎不再永久代和元空间进行收集



流程图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605193658143.png)



**常用调优工具（在JVM下篇：性能监控与调优篇会详细介绍）**

- JDK命令行

- Eclipse:Memory Analyzer Tool

- Jconsole

- VisualVM

- Jprofiler

- Java Flight Recorder

- GCViewer

- GC Easy



### 6.5 Minor GC、MajorGC、Full GC

JVM在进行GC时，并非每次都对上面三个内存区域一起回收的，大部分时候回收的都是指新生代。

针对Hotspot VM的实现，它里面的GC按照回收区域又分为两大种类型：一种是部分收集（Partial GC），一种是整堆收集（FullGC）

- 部分收集：不是完整收集整个Java堆的垃圾收集。其中又分为： 

- - 新生代收集（Minor GC / Young GC）：只是新生代的垃圾收集

- - 老年代收集（Major GC / Old GC）：只是老年代的圾收集。 

- - - 目前，只有CMSGC会有单独收集老年代的行为。

- - - 注意，很多时候Major GC会和Full GC混淆使用，需要具体分辨是老年代回收还是整堆回收。

- - 混合收集（MixedGC）：收集整个新生代以及部分老年代的垃圾收集。 

- - - 目前，只有G1 GC会有这种行为

- 整堆收集（Full GC）：收集整个java堆和方法区的垃圾收集。



### 6.6 堆空间分代思想

分代的唯一理由就是优化GC性能。

如果没有分代，GC的时候要找到哪些对象没用，就会对堆的所有区域进行扫描。而很多对象都是朝生夕死的，如果分代的话，把新创建的对象放到某一地方，当GC的时候先把这块存储“朝生夕死”对象的区域进行回收，这样就会腾出很大的空间出来。



![](./img/堆78.png)



### 6.7 内存分配策略

针对不同年龄段的对象分配原则如下所示：

- 优先分配到Eden

- 大对象直接分配到老年代（尽量避免程序中出现过多的大对象）

- 长期存活的对象分配到老年代

- 动态对象年龄判断：如果survivor区中相同年龄的所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象可以直接进入老年代，无须等到`MaxTenuringThreshold`中要求的年龄。

- 空间分配担保： `-XX:HandlePromotionFailure`

### 6.8 为对象分配内存：TLAB



### 6.9 小结：堆空间的参数设置

```java
// 详细的参数内容会在JVM下篇：性能监控与调优篇中进行详细介绍，这里先熟悉下
-XX:+PrintFlagsInitial  //查看所有的参数的默认初始值
-XX:+PrintFlagsFinal  //查看所有的参数的最终值（可能会存在修改，不再是初始值）
-Xms  //初始堆空间内存（默认为物理内存的1/64）
-Xmx  //最大堆空间内存（默认为物理内存的1/4）
-Xmn  //设置新生代的大小。（初始值及最大值）
-XX:NewRatio  //配置新生代与老年代在堆结构的占比
-XX:SurvivorRatio  //设置新生代中Eden和S0/S1空间的比例
-XX:MaxTenuringThreshold  //设置新生代垃圾的最大年龄
-XX:+PrintGCDetails //输出详细的GC处理日志
//打印gc简要信息：①-Xx：+PrintGC ② - verbose:gc
-XX:HandlePromotionFalilure：//是否设置空间分配担保
```







### 堆是分配对象的唯一选择么？

在Java虚拟机中，对象是在Java堆中分配内存的，这是一个普遍的常识。

但是，有一种特殊情况，那就是如果经过逃逸分析（Escape Analysis）后发现，一个对象并没有逃逸出方法的话，那么就可能被优化成栈上分配。这样就无需在堆上分配内存，也无须进行垃圾回收了。这也是最常见的堆外存储技术。







## 7 方法区⭐️⭐️⭐️⭐️⭐️

### 7.1 栈、堆、方法区的交互关系

从线程共享与否的角度来看

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605210011901.png)

举个例子🌰



![](./img/栈堆方法区关系.png)



### 7.2 方法区的理解

官方文档：[Chapter 2. The Structure of the Java Virtual Machine (oracle.com)](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.5.4)



所以，**方法区看作是一块独立于Java堆的内存空间。**



- 方法区（Method Area）与Java堆一样，是**各个线程共享**的内存区域。虽然《Java虚拟机规范》中把方法区描述为堆的一个逻辑部分，但它却有一个别名叫做“非堆”，目的是区分Java堆区。
- 方法区在JVM启动的时候被创建，并且它的实际的物理内存空间中和Java堆区一样都可以是不连续的。
- 方法区的大小，跟堆空间一样，可以选择固定大小或者可扩展。
- 方法区的大小决定了系统可以保存多少个类，如果系统定义了太多的类，导致方法区溢出，虚拟机同样会抛出内存溢出错误：`java.lang.OutOfMemoryError: PermGen space` 或者`java.lang.OutOfMemoryError: Metaspace`

- 关闭JVM就会释放这个区域的内存。

#### HotSpot中方法区的演进

在jdk7及以前，习惯上把方法区，称为永久代。jdk8开始，使用元空间取代了永久代。

而到了JDK8，终于完全废弃了永久代的概念，改用与JRockit、J9一样在本地内存中实现的元空间（Metaspace）来代替

元空间的本质和永久代类似，都是对JVM规范中方法区的实现。不过元空间与永久代最大的区别在于：**元空间不在虚拟机设置的内存中，而是使用本地内存。**



### 7.3 设置方法区大小与OOM

方法区的大小不必是固定的，JVM可以根据应用的需要动态调整。





- 元数据区大小可以使用参数 `-XX:MetaspaceSize` 和 `-XX:MaxMetaspaceSize`指定





### 7.4 方法区的内部结构

#### 方法区（Method Area）存储什么？

它用于存储已被虚拟机加载的类型信息、常量、静态变量、即时编译器编译后的代码缓存等。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220605211827276.png)



#### 方法区的内部结构

##### （1）类型信息

对每个加载的类型（类class、接口interface、枚举enum、注解annotation），JVM必须在方法区中存储以下类型信息：

- 这个类型的完整有效名称（全名=包名.类名）
- 这个类型直接父类的完整有效名（对于interface或是java.lang.object，都没有父类）
- 这个类型的修饰符（public，abstract，final的某个子集）
- 这个类型直接接口的一个有序列表

##### （2）域（Field）信息

域的相关信息包括：域名称、域类型、域修饰符（public，private，protected，static，final，volatile，transient的某个子集）

##### （3）方法（Method）信息

JVM必须保存所有方法的以下信息，同域信息一样包括声明顺序：

- 方法名称
- 方法的返回类型（或void）
- 方法参数的数量和类型（按顺序）
- 方法的修饰符（public，private，protected，static，final，synchronized，native，abstract的一个子集）
- 方法的字节码（bytecodes）、操作数栈、局部变量表及大小（abstract和native方法除外）
- 异常表（abstract和native方法除外） 
  -   每个异常处理的开始位置、结束位置、代码处理在程序计数器中的偏移地址、被捕获的异常类的常量池索引



#### 运行时常量池（Runtime Constant Pool）  VS 常量池

几种常量池内存储的数据类型包括：

- 数量值

- 字符串值

- 类引用

- 字段引用

- 方法引用



常量池、可以看做是一张表，虚拟机指令根据这张常量表找到要执行的类名、方法名、参数类型、字面量等类型

#### 运行时常量池

- 运行时常量池（Runtime Constant Pool）是方法区的一部分。
- **常量池表（Constant Pool Table）是Class文件的一部分，用于存放编译期生成的各种字面量与符号引用，这部分内容将在类加载后存放到方法区的运行时常量池中。**
- 运行时常量池，在加载类和接口到虚拟机后，就会创建对应的运行时常量池。
- 运行时常量池，相对于Class文件常量池的另一重要特征是：具备动态性。



### 7.5 方法区使用举例



```java
public class MethodAreaDemo {
    public static void main(String args[]) {
        int x = 500;
        int y = 100;
        int a = x / y;
        int b = 50;
        System.out.println(a+b);
    }
}
```





### 7.6 方法区的演进细节



| JDK1.6及之前 | 有永久代（permanet），静态变量存储在永久代上                 |
| ------------ | ------------------------------------------------------------ |
| **JDK1.7**   | **有永久代，但已经逐步 “去永久代”，字符串常量池，静态变量移除，保存在堆中** |
| **JDK1.8**   | **无永久代，类型信息，字段，方法，常量保存在本地内存的元空间，但字符串常量池、静态变量仍然在堆中。** |



#### StringTable为什么要调整位置？

jdk7中将StringTable放到了堆空间中。

因为永久代的回收效率很低，在full gc的时候才会触发。而full gc是老年代的空间不足、永久代不足时才会触发。这就导致StringTable回收效率不高。

而我们开发中会有大量的字符串被创建，回收效率低，导致永久代内存不足。**放到堆里，能及时回收内存。**

### 7.7 方法区的垃圾回收





### 总结





### 面试题 🎈

#### 百度：说一下JVM内存模型吧，有哪些区？分别干什么的？



#### 蚂蚁金服：Java8的内存分代改进 JVM内存分哪几个区，每个区的作用是什么？

一面：JVM内存分布/内存结构？栈和堆的区别？堆的结构？为什么两个survivor区？



二面：Eden和survior的比例分配

#### 小米：jvm内存分区，为什么要有新生代和老年代



#### 字节跳动

一面：Java的内存分区



二面：讲讲 jvm运行时数据库区 什么时候对象会进入老年代？

#### 京东

JVM的内存结构，Eden和Survivor比例。

 

JVM内存为什么要分成新生代，老年代，持久代。

 

新生代中为什么要分为Eden和survivor



 

#### 天猫

一面：Jvm内存模型以及分区，需要详细到每个区放什么。

一面：JVM的内存模型，Java8做了什么改

 

#### 拼多多

JVM内存分哪几个区，每个区的作用是什么？

 

#### 美团

java内存分配 jvm的永久代中会发生垃圾回收吗？

一面：jvm内存分区，为什么要有新生代和老年代？





## 8 对象实例化及直接内存

### 对象的创建

语言层面，创建对象通常（例外：复制、反序列化）仅仅是一个 `new` 关键字而已。

Java虚拟机遇到一条字节码 new 指令时，检查指令的参数能否在常量池中定位到类的符号引用，检查是否已经被初始化加载解析，没有就先执行类加载。

类加载检查通过后，就为新生对象分配内存。内存规整的，**指针碰撞**。内存不规整，虚拟机就必须维护一个列表，这种分配方式称为**“空闲列表”（Free List）**（相比指针碰撞复杂）。选择哪种分配方式由Java堆是否规整决定，而堆是否规整又由所采用的垃圾收集器是否带有空间压缩的能力决定。如Serial、ParNew等收集器系统采用的是指针碰撞，既简单又高效，而当使用CMS这种基于清除算法的收集器时，理论上就采用复杂的空闲列表来分配内存。

内存分配完成后，将分配到的内存空间初始化为零值。







创建对象的方式

- new：最常见的方式、Xxx的静态方法，XxxBuilder/XxxFactory的静态方法

- Class的newInstance方法：反射的方式，只能调用空参的构造器，权限必须是public

- Constructor的newInstance(XXX)：反射的方式，可以调用空参、带参的构造器，权限没有要求

- 使用clone()：不调用任何的构造器，要求当前的类需要实现Cloneable接口，实现clone()

- 使用序列化：从文件中、从网络中获取一个对象的二进制流

- 第三方库 Objenesis

### 对象内存布局

HotSpot 虚拟机里，对象在堆内存中的存储布局可分为三个部分：对象头鱼、实例数据、对其填充。





### 对象的访问定位

Java程序通过栈上的reference数据来操作堆上的具体对象。对象访问方式是由虚拟机实现而定的，主流的访问方式主要有使用句柄和直接指针两种。



## 9 执行引擎

## 10 StringTable

## 11 垃圾收集器与内存分配策略

事实上，垃圾收集的历史远比Java久远。

- 哪些内存需要回收？
- 什么时候回收？
- 如何回收？

Java堆和方法区这两个区域有着很显著的不确定性。垃圾收集器所关注的正是这部分内存该如何管理。

### 对象已死？

垃圾回收器对堆进行回收前，首先就是要确定这些对象哪些还活着，哪些已经死去（即不可能再被任何途径使用的对象）。

#### 引用计数法

客观地讲，引用计数算法虽然占用了一些额外的内存空间来进行计数，但它的原理简单，判定效率高，大多数情况下都是一个不错的算法。

但是在Java领域，主流的Java虚拟机都没有使用引用计数法来管理内存，主要原因是，这个看似简单的算法有很多例外情况要考虑，必须要配合大量额外处理才能保证正确的工作。如难以解决的对象之间相互循环引用的问题。

#### 可达性分析算法

基本思路是通过一系列称为“GC Roots”的根对象作为起始节点集，从这些节点开始，根据引用关系向下搜索，没有任何引用链相连，则证明此对象是不可能再被使用的。

#### 再谈引用

判定对象是否存活都和“引用”离不开关系。JDK1.2后，将引用分为强引用、软引用、弱引用、虚引用，这四种引用强度依次减弱。

#### 生存还是死亡

判定为不可达对象后，也不是“非死不可”。暂时还处于“缓刑”阶段。

### 垃圾收集算法

标记—清除算法

标记—复制算法

标记—整理算法

### HotSpot 算法细节实现

### 经典垃圾收集器

#### Serial 收集器

最基础、历史最悠久的收集器。曾是HopSpot虚拟机新生代收集器的唯一选择。

单线程工作：垃圾收集时必须暂停其它所有工作线程，直到它收集结束。（Stop the world）

事实上，至今仍是 HotSpot 虚拟机运行在客户端模式下的默认新生代收集器。有着优于其它收集器的地方，即简单而高效。额外内存消耗最小。

#### ParNew 收集器

实质上是的Serial 收集器的多线程并行版本。

直到CMS的出现才巩固了ParNew的地位。

#### Serial Old收集器

Serial 收集器的老年代版本，同样是单线程收集器。主要意义是供客户端模式下的 HotSpot 虚拟机使用。

#### Parallel Old收集器

Parallel Scavenge 收集器的老年代版本。

#### CMS 收集器

以获取最短回收停顿时间为目标的收集器。

基于标记—清除算法实现。

一款优秀的收集器。并发收集、低停顿。第一次成功尝试，但远达不到完美，有3个明显的缺点：对处理器资源非常敏感、无法处理浮动垃圾、大量空间碎片产生。

#### Garbage First（G1） 收集器

里程碑式的成果。

CMS收集器的替换者和继承人。“停顿预测模型”的收集器：能够支持指定在一个长度为M毫秒的时间片段内，消耗在垃圾收集上的时间大概率不超过N毫秒这样的目标。

G1新思想：面向堆内存任何部分来组成回收集进行回收，衡量标准不再是它属于哪个分代，而是哪块内存中存放的垃圾数量最多，回收收益最大。即G1收集器的Mixed GC模式。开创的基于Region的堆内存布局是能够实现这个目标的关键。

一款主要面向服务端应用的垃圾收集器，目的是为了适应现在不断扩大的内存和不断增加的处理器数量，进一步降低暂停时间（pause time），同时兼顾良好的吞吐量。如今已经完全替代CMS垃圾收集器，CMS收集器在JDK9 中被废弃，在JDK 14中被移除。

G1垃圾收集器也是**基于分代收集理论设计**的，但是它的堆内存的布局与其他垃圾收集器的布局有很明显的区别，G1收集器不再按照固定大小以及固定数量的分代区域划分，而是把JAVA堆划分为2048个大小相等的独立的Region，每个Region大小可以通过参数`-XX:G1HeapRegionSize` 设定，取值范围为**1-32MB**，且**必须为2的N次幂**。每一个Region都可以根据需要充当新生代的Eden区、S0和S1区或者老年代。

在一般的垃圾收集中**对于堆中的大对象，默认直接会被分配到老年代**，但是如果它是一个短期存在的大对象，就会对垃圾收集器造成负面影响。为了解决这个问题，**G1划分了一个Humongous区**，它用来专门存放大对象。如果一个H区装不下一个大对象，那么G1会寻找连续的H区来存储。为了能找到连续的H区，有时候不得不启动Full GC。 G1的大多数行为都把H区作为老年代的一部分来看待。当一个对象的大小超过了一个Region容量的一半，即被认为是大对象。

 虽然G1仍然保留新生代和老年代的概念，但新生代和老年代不再是固定的了，而是一系列区域（不需要连续，逻辑连续即可）的动态集合。由于G1这种基于Region回收的方式，可以预测停顿时间。G1会**根据每个Region里面垃圾“价值”的大小**，在后台**维护一个优先级列表**，每次根据用户设定的允许收集停顿的时间（-XX:MaxGCPauseMillis,默认为200毫秒）**优先处理价值收益最大的Region**。

![](./img/G1_run.png)



### 直接内存（Direct Memory）



## 12 虚拟机性能监控、故障处理工具

《深入理解Java虚拟机第4章》

### 基础故障处理工具

jps 虚拟机进程状况工具

jstat 虚拟机统计信息监视工具

jinfo java配置信息工具

jmap java内存映像工具

jstack 堆栈跟踪工具



### 可视化故障处理工具

JConsole java监视与控制台

Java Mission Control 可持续在线的监控工具













# 字节码与类的加载篇（中）

# 性能监控与调优篇（下）











![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/文字图1.jpg)





























