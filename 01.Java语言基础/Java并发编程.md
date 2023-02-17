

# 为什么需要学习并发编程

例如，Java 里 synchronized、wait()/notify() 相关的知识很琐碎，看懂难，会用更难。

但实际上 synchronized、wait()、notify() 不过是操作系统领域里管程模型的一种实现而已，Java SDK 并发包里的条件变量 Condition 也是管程里的概念，synchronized、wait()/notify()、条件变量这些知识如果单独理解，自然是管中窥豹。

但是如果站在管程这个理论模型的高度，你就会发现这些知识原来这么简单，同时用起来也就得心应手了。

<br>

管程作为一种解决并发问题的模型，是继信号量模型之后的一项重大创新，它与信号量在逻辑上是等价的（可以用管程实现信号量，也可以用信号量实现管程），但是相比之下管程更易用。而且，很多编程语言都支持管程，搞懂管程，对学习其他很多语言的并发编程有很大帮助。然而，很多人急于学习 Java 并发编程技术，却忽略了技术背后的理论和模型，而理论和模型却往往比具体的技术更为重要。

<br>

此外，Java 经过这些年的发展，Java SDK 并发包提供了非常丰富的功能，对于初学者来说可谓是眼花缭乱，好多人觉得无从下手。但是，Java SDK 并发包乃是并发大师 Doug Lea 出品，堪称经典，它内部一定是有章可循的。那它的章法在哪里呢？

**其实并发编程可以总结为三个核心问题：分工、同步、互斥。**

所谓**分工**指的是如何高效地拆解任务并分配给线程，而**同步**指的是线程之间如何协作，**互斥**则是保证同一时刻只允许一个线程访问共享资源。

Java SDK 并发包很大部分内容都是按照这三个维度组织的，例如 Fork/Join 框架就是一种分工模式，CountDownLatch 就是一种典型的同步方式，而可重入锁则是一种互斥手段。

<br>

当把并发编程核心的问题搞清楚，再回过头来看 Java SDK 并发包，你会感觉豁然开朗，它不过是针对并发问题开发出来的工具而已，此时的 SDK 并发包可以任你“盘”了。

而且，这三个核心问题是跨语言的，你如果要学习其他语言的并发编程类库，完全可以顺着这三个问题按图索骥。Java SDK 并发包其余的一部分则是并发容器和原子类，这些比较容易理解，属于辅助工具，其他语言里基本都能找到对应的。

<br>

所以，你说并发编程难学吗？

首先，难是肯定的。因为这其中涉及操作系统、CPU、内存等等多方面的知识，如果你缺少某一块，那理解起来自然困难。其次，难不难学也可能因人而异，就我的经验来看，很多人在学习并发编程的时候，总是喜欢从点出发，希望能从点里找到规律或者本质，最后却把自己绕晕了。

并发编程并不是 Java 特有的语言特性，它是一个通用且早已成熟的领域。Java 只是根据自身情况做了实现罢了，当你理解或学习并发编程的时候，如果能够站在较高层面，系统且有体系地思考问题，那就会容易很多。





整个专栏的知识结构体系如下。



# 并发理论基础

## 01 | 可见性、原子性和有序性问题：并发编程Bug的源头



你我都知道，编写正确的并发程序是一件极困难的事情，并发程序的 Bug 往往会诡异地出现，然后又诡异地消失，很难重现，也很难追踪，很多时候都让人很抓狂。但要快速而又精准地解决“并发”类的疑难杂症，你就要理解这件事情的本质，追本溯源，深入分析这些 Bug 的源头在哪里。





这些年，我们的 CPU、内存、I/O 设备都在不断迭代，不断朝着更快的方向努力。但是，在这个快速发展的过程中，有一个**核心矛盾一直存在，就是这三者的速度差异**。CPU 和内存的速度差异可以形象地描述为：CPU 是天上一天，内存是地上一年（假设 CPU 执行一条普通指令需要一天，那么 CPU 读写内存得等待一年的时间）。内存和 I/O 设备的速度差异就更大了，内存是天上一天，I/O 设备是地上十年。



程序里大部分语句都要访问内存，有些还要访问 I/O，根据木桶理论（一只水桶能装多少水取决于它最短的那块木板），程序整体的性能取决于最慢的操作——读写 I/O 设备，也就是说单方面提高 CPU 性能是无效的。

为了合理利用 CPU 的高性能，平衡这三者的速度差异，计算机体系结构、操作系统、编译程序都做出了贡献，主要体现为：

1. CPU 增加了缓存，以均衡与内存的速度差异；
2. 操作系统增加了进程、线程，以分时复用 CPU，进而均衡 CPU 与 I/O 设备的速度差异；
3. 编译程序优化指令执行次序，使得缓存能够得到更加合理地利用。





现在我们几乎所有的程序都默默地享受着这些成果，但是天下没有免费的午餐，并发程序很多诡异问题的根源也在这里。

### 源头之一：缓存导致的可见性问题



在单核时代，所有的线程都是在一颗 CPU 上执行，CPU 缓存与内存的数据一致性容易解决。因为所有线程都是操作同一个 CPU 的缓存，一个线程对缓存的写，对另外一个线程来说一定是可见的。

例如在下面的图中，线程 A 和线程 B 都是操作同一个 CPU 里面的缓存，所以线程 A 更新了变量 V 的值，那么线程 B 之后再访问变量 V，得到的一定是 V 的最新值（线程 A 写过的值）

<br>

CPU 缓存与内存的关系图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416143737984.png)

一个线程对共享变量的修改，另外一个线程能够立刻看到，我们称为可见性。

<br>

多核时代，每颗 CPU 都有自己的缓存，这时 CPU 缓存与内存的数据一致性就没那么容易解决了，当多个线程在不同的 CPU 上执行时，这些线程操作的是不同的 CPU 缓存。比如下图中，线程 A 操作的是 CPU-1 上的缓存，而线程 B 操作的是 CPU-2 上的缓存，很明显，这个时候线程 A 对变量 V 的操作对于线程 B 而言就不具备可见性了。这个就属于硬件程序员给软件程序员挖的“坑”。

<br>

多核 CPU 的缓存与内存关系图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416143911537.png)

下面我们再用一段代码来验证一下多核场景下的可见性问题。



```java

public class Test {
  
  private long count = 0;
  private void add10K() {
    int idx = 0;
    while(idx++ < 10000) {
      count += 1;
    }
  }
  
  public static long calc() {
    final Test test = new Test();
    // 创建两个线程，执行add()操作
    Thread th1 = new Thread(()->{
      test.add10K();
    });
    Thread th2 = new Thread(()->{
      test.add10K();
    });
    // 启动两个线程
    th1.start();
    th2.start();
    // 等待两个线程执行结束
    th1.join();
    th2.join();
    return count;
  }
  
}
```



直觉告诉我们应该是 20000，因为在单线程里调用两次 add10K() 方法，count 的值就是 20000，但实际上 calc() 的执行结果是个 10000 到 20000 之间的随机数。为什么呢？

我们假设线程 A 和线程 B 同时开始执行，那么第一次都会将 count=0 读到各自的 CPU 缓存里，执行完 count+=1 之后，各自 CPU 缓存里的值都是 1，同时写入内存后，我们会发现内存中是 1，而不是我们期望的 2。之后由于各自的 CPU 缓存里都有了 count 的值，两个线程都是基于 CPU 缓存里的 count 值来计算，所以导致最终 count 的值都是小于 20000 的。这就是缓存的可见性问题。



循环 10000 次 count+=1 操作如果改为循环 1 亿次，你会发现效果更明显，最终 count 的值接近 1 亿，而不是 2 亿。如果循环 10000 次，count 的值接近 20000，原因是两个线程不是同时启动的，有一个时差。





### 源头之二：线程切换带来的原子性问题

由于 IO 太慢，早期的操作系统就发明了多进程，即便在单核的 CPU 上我们也可以一边听着歌，一边写 Bug，这个就是多进程的功劳。

操作系统允许某个进程执行一小段时间，例如 50 毫秒，过了 50 毫秒操作系统就会重新选择一个进程来执行（我们称为“任务切换”），这个 50 毫秒称为“时间片”。

<br>

线程切换示意图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416151049829.png)



在一个时间片内，如果一个进程进行一个 IO 操作，例如读个文件，这个时候该进程可以把自己标记为“休眠状态”并出让 CPU 的使用权，待文件读进内存，操作系统会把这个休眠的进程唤醒，唤醒后的进程就有机会重新获得 CPU 的使用权了。



<br>

这里的进程在等待 IO 时之所以会释放 CPU 使用权，是为了让 CPU 在这段等待时间里可以做别的事情，这样一来 CPU 的使用率就上来了；此外，如果这时有另外一个进程也读文件，读文件的操作就会排队，磁盘驱动在完成一个进程的读操作后，发现有排队的任务，就会立即启动下一个读操作，这样 IO 的使用率也上来了。

是不是很简单的逻辑？但是，虽然看似简单，支持多进程分时复用在操作系统的发展史上却具有里程碑意义，Unix 就是因为解决了这个问题而名噪天下的。

<br>

早期的操作系统基于进程来调度 CPU，不同进程间是不共享内存空间的，所以进程要做任务切换就要切换内存映射地址，而一个进程创建的所有线程，都是共享一个内存空间的，所以线程做任务切换成本就很低了。现代的操作系统都基于更轻量的线程来调度，现在我们提到的“任务切换”都是指“线程切换”。

<br>

Java 并发程序都是基于多线程的，自然也会涉及到任务切换，也许你想不到，任务切换竟然也是并发编程里诡异 Bug 的源头之一。任务切换的时机大多数是在时间片结束的时候，我们现在基本都使用高级语言编程，**高级语言里一条语句往往需要多条 CPU 指令完成**，例如上面代码中的count += 1，至少需要三条 CPU 指令。

- 指令 1：首先，需要把变量 count 从内存加载到 CPU 的寄存器；
- 指令 2：之后，在寄存器中执行 +1 操作；
- 指令 3：最后，将结果写入内存（缓存机制导致可能写入的是 CPU 缓存而不是内存）。

操作系统做任务切换，可以发生在任何一条 **CPU 指令**执行完，是的，是 CPU 指令，而不是高级语言里的一条语句。

对于上面的三条指令来说，我们假设 count=0，如果线程 A 在指令 1 执行完后做线程切换，线程 A 和线程 B 按照下图的序列执行，那么我们会发现两个线程都执行了 count+=1 的操作，但是得到的结果不是我们期望的 2，而是 1。

非原子操作的执行路径示意图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416151740237.png)



我们潜意识里面觉得 count+=1 这个操作是一个不可分割的整体，就像一个原子一样，线程的切换可以发生在 count+=1 之前，也可以发生在 count+=1 之后，但就是不会发生在中间。**我们把一个或者多个操作在 CPU 执行的过程中不被中断的特性称为原子性。**CPU 能保证的原子操作是 CPU 指令级别的，而不是高级语言的操作符，这是违背我们直觉的地方。因此，很多时候我们需要在高级语言层面保证操作的原子性。



### 源头之三：编译优化带来的有序性问题

那并发编程里还有没有其他有违直觉容易导致诡异 Bug 的技术呢？有的，就是有序性。

顾名思义，有序性指的是程序按照代码的先后顺序执行。编译器为了优化性能，有时候会改变程序中语句的先后顺序，例如程序中：“a=6；b=7；”编译器优化后可能变成“b=7；a=6；”，在这个例子中，编译器调整了语句的顺序，但是不影响程序的最终结果。不过有时候编译器及解释器的优化可能导致意想不到的 Bug。



在 Java 领域一个经典的案例就是利用双重检查创建单例对象，例如下面的代码：在获取实例 getInstance() 的方法中，我们首先判断 instance 是否为空，如果为空，则锁定 Singleton.class 并再次检查 instance 是否为空，如果还为空则创建 Singleton 的一个实例。

```java

public class Singleton {
  static Singleton instance;
  
  static Singleton getInstance(){
    if (instance == null) {
      synchronized(Singleton.class) {
        if (instance == null)
          instance = new Singleton();
        }
    }
    return instance;
  }
  
}
```



假设有两个线程 A、B 同时调用 getInstance() 方法，他们会同时发现 instance == null ，于是同时对 Singleton.class 加锁，此时 JVM 保证只有一个线程能够加锁成功（假设是线程 A），另外一个线程则会处于等待状态（假设是线程 B）；线程 A 会创建一个 Singleton 实例，之后释放锁，锁释放后，线程 B 被唤醒，线程 B 再次尝试加锁，此时是可以加锁成功的，加锁成功后，线程 B 检查 instance == null 时会发现，已经创建过 Singleton 实例了，所以线程 B 不会再创建一个 Singleton 实例。

<br>

这看上去一切都很完美，无懈可击，但实际上这个 getInstance() 方法并不完美。问题出在哪里呢？出在 new 操作上，我们以为的 new 操作应该是：

1. 分配一块内存 M；
2. 在内存 M 上初始化 Singleton 对象；
3. 然后 M 的地址赋值给 instance 变量。



但是实际上优化后的执行路径却是这样的：

1. 分配一块内存 M；
2. 将 M 的地址赋值给 instance 变量；
3. 最后在内存 M 上初始化 Singleton 对象。

<br>

优化后会导致什么问题呢？我们假设线程 A 先执行 getInstance() 方法，当执行完指令 2 时恰好发生了线程切换，切换到了线程 B 上；如果此时线程 B 也执行 getInstance() 方法，那么线程 B 在执行第一个判断时会发现 instance != null ，所以直接返回 instance，而此时的 instance 是没有初始化过的，如果我们这个时候访问 instance 的成员变量就可能触发空指针异常。

<br>

双重检查创建单例的异常执行路径

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416152343436.png)





### 总结



要写好并发程序，首先要知道并发程序的问题在哪里，只有确定了“靶子”，才有可能把问题解决，毕竟所有的解决方案都是针对问题的。并发程序经常出现的诡异问题看上去非常无厘头，但是深究的话，无外乎就是直觉欺骗了我们，**只要我们能够深刻理解可见性、原子性、有序性在并发场景下的原理，很多并发 Bug 都是可以理解、可以诊断的。**

在介绍可见性、原子性、有序性的时候，特意提到**缓存**导致的可见性问题，**线程切换**带来的原子性问题，**编译优化**带来的有序性问题，其实缓存、线程、编译优化的目的和我们写并发程序的目的是相同的，都是提高程序性能。但是技术在解决一个问题的同时，必然会带来另外一个问题，所以**在采用一项技术的同时，一定要清楚它带来的问题是什么，以及如何规避**。

我们这个专栏在讲解每项技术的时候，都会尽量将每项技术解决的问题以及产生的问题讲清楚，也希望你能够在这方面多思考、多总结。



<hr>

问题：synchronized修饰的代码块里，会出现线程切换么？我理解的synchronized作用就是同步执行，不会线程切换，请作者给我解答下。

回答：在同步块里，线程也可能被操作系统剥夺cpu的使用权，但是其他线程此时是拿不到锁，所以其他线程不会执行同步块的代码



问题：看完这一篇，觉得我之前看的**《Java高并发编程详解》**和**《深入理解Java虚拟机》**两本书没白看，至少看懂老师说的问题了，看完评论（差不多半小时），原来高手都在评论区😀 😀 😀 



## 02 | Java内存模型：看Java如何解决可见性和有序性问题

如何解决其中的可见性和有序性导致的问题，这也就引出来了今天的主角——**Java 内存模型**。

Java 内存模型这个概念，在职场的很多面试中都会考核到，是一个热门的考点，也是一个人并发水平的具体体现。原因是当并发程序出问题时，需要一行一行地检查代码，这个时候，只有掌握 Java 内存模型，才能慧眼如炬地发现问题。



### 什么是 Java 内存模型？

你已经知道，导致可见性的原因是缓存，导致有序性的原因是编译优化，那解决可见性、有序性最直接的办法就是**禁用缓存和编译优化**，但是这样问题虽然解决了，我们程序的性能可就堪忧了。



合理的方案应该是**按需禁用缓存以及编译优化**。那么，如何做到“按需禁用”呢？对于并发程序，何时禁用缓存以及编译优化只有程序员知道，那所谓“按需禁用”其实就是指按照程序员的要求来禁用。所以，为了解决可见性和有序性问题，只需要提供给程序员按需禁用缓存和编译优化的方法即可。

Java 内存模型是个很复杂的规范，可以从不同的视角来解读，站在我们这些程序员的视角，本质上可以理解为，Java 内存模型规范了 JVM 如何提供按需禁用缓存和编译优化的方法。具体来说，这些方法包括 **volatile**、**synchronized** 和 **final** 三个关键字，以及六项 **Happens-Before** 规则，这也正是本期的重点内容。



### 使用 volatile 的困惑

volatile 关键字并不是 Java 语言的特产，古老的 C 语言里也有，它最原始的意义就是禁用 CPU 缓存。

例如，我们声明一个 volatile 变量 volatile int x = 0，它表达的是：告诉编译器，对这个变量的读写，不能使用 CPU 缓存，必须从内存中读取或者写入。这个语义看上去相当明确，但是在实际使用的时候却会带来困惑。



例如下面的示例代码，假设线程 A 执行 writer() 方法，按照 volatile 语义，会把变量 “v=true” 写入内存；假设线程 B 执行 reader() 方法，同样按照 volatile 语义，线程 B 会从内存中读取变量 v，如果线程 B 看到 “v == true” 时，那么线程 B 看到的变量 x 是多少呢？

直觉上看，应该是 42，那实际应该是多少呢？这个要看 Java 的版本，如果在低于 1.5 版本上运行，x 可能是 42，也有可能是 0；如果在 1.5 以上的版本上运行，x 就是等于 42。







```java

// 以下代码来源于【参考1】
class VolatileExample {
  int x = 0;
  volatile boolean v = false;
  
  public void writer() {
    x = 42;
    v = true;
  }
  
  public void reader() {
    if (v == true) {
      // 这里x会是多少呢？
    }
  }
  
}
```

分析一下，为什么 1.5 以前的版本会出现 x = 0 的情况呢？我相信你一定想到了，变量 x 可能被 CPU 缓存而导致可见性问题。这个问题在 1.5 版本已经被圆满解决了。Java 内存模型在 1.5 版本对 volatile 语义进行了增强。怎么增强的呢？答案是一项 Happens-Before 规则。

### Happens-Before 规则

如何理解 Happens-Before 呢？如果望文生义（很多网文也都爱按字面意思翻译成“先行发生”），那就南辕北辙了，Happens-Before 并不是说前面一个操作发生在后续操作的前面，它真正要表达的是：**前面一个操作的结果对后续操作是可见的。**就像有心灵感应的两个人，虽然远隔千里，一个人心之所想，另一个人都看得到。Happens-Before 规则就是要保证线程之间的这种“心灵感应”。所以比较正式的说法是：Happens-Before 约束了编译器的优化行为，虽允许编译器优化，但是要求编译器优化后一定遵守 Happens-Before 规则。





Happens-Before 规则应该是 Java 内存模型里面最晦涩的内容了，和程序员相关的规则一共有如下六项，都是关于可见性的。

恰好前面示例代码涉及到这六项规则中的前三项，为便于你理解，我也会分析上面的示例代码，来看看规则 1、2 和 3 到底该如何理解。至于其他三项，我也会结合其他例子作以说明。

#### 1 程序的顺序性规则

这条规则是指在一个线程中，按照程序顺序，前面的操作 Happens-Before 于后续的任意操作。这还是比较容易理解的，比如刚才那段示例代码，按照程序的顺序，第 6 行代码 “x = 42;” Happens-Before 于第 7 行代码 “v = true;”，这就是规则 1 的内容，也比较符合单线程里面的思维：程序前面对某个变量的修改一定是对后续操作可见的。



#### 2 volatile 变量规则

这条规则是指对一个 volatile 变量的写操作， Happens-Before 于后续对这个 volatile 变量的读操作。

这个就有点费解了，对一个 volatile 变量的写操作相对于后续对这个 volatile 变量的读操作可见，这怎么看都是禁用缓存的意思啊，貌似和 1.5 版本以前的语义没有变化啊？如果单看这个规则，的确是这样，但是如果我们关联一下规则 3，就有点不一样的感觉了。



#### 3 传递性

这条规则是指如果 A Happens-Before B，且 B Happens-Before C，那么 A Happens-Before C。



我们将规则 3 的传递性应用到我们的例子中，会发生什么呢？可以看下面这幅图：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416154432150.png)

从图中，我们可以看到：

1. “x=42” Happens-Before 写变量 “v=true” ，这是规则 1 的内容；
2. 写变量“v=true” Happens-Before 读变量 “v=true”，这是规则 2 的内容 。

再根据这个传递性规则，我们得到结果：“x=42” Happens-Before 读变量“v=true”。这意味着什么呢？



如果线程 B 读到了“v=true”，那么线程 A 设置的“x=42”对线程 B 是可见的。也就是说，线程 B 能看到 “x == 42” ，有没有一种恍然大悟的感觉？这就是 1.5 版本对 volatile 语义的增强，这个增强意义重大，1.5 版本的并发工具包（java.util.concurrent）就是靠 volatile 语义来搞定可见性的，这个在后面的内容中会详细介绍。



#### 4 管程中锁的规则

这条规则是指对一个锁的解锁 Happens-Before 于后续对这个锁的加锁。

要理解这个规则，就首先要了解“管程指的是什么”。**管程**是一种通用的同步原语，在 Java 中指的就是 synchronized，synchronized 是 Java 里对管程的实现。

管程中的锁在 Java 里是隐式实现的，例如下面的代码，在进入同步块之前，会自动加锁，而在代码块执行完会自动释放锁，加锁以及释放锁都是编译器帮我们实现的。

```java

synchronized (this) { //此处自动加锁
  // x是共享变量,初始值=10
  if (this.x < 12) {
    this.x = 12; 
  }  
} //此处自动解锁
```



#### 5 线程 start() 规则

这条是关于线程启动的。它是指主线程 A 启动子线程 B 后，子线程 B 能够看到主线程在启动子线程 B 前的操作。

换句话说就是，如果线程 A 调用线程 B 的 start() 方法（即在线程 A 中启动线程 B），那么该 start() 操作 Happens-Before 于线程 B 中的任意操作。具体可参考下面示例代码。

```java

Thread B = new Thread(()->{
  // 主线程调用B.start()之前
  // 所有对共享变量的修改，此处皆可见
  // 此例中，var==77
});
// 此处对共享变量var修改
var = 77;
// 主线程启动子线程
B.start();
```



#### 6 线程 join() 规则

这条是关于线程等待的。它是指主线程 A 等待子线程 B 完成（主线程 A 通过调用子线程 B 的 join() 方法实现），当子线程 B 完成后（主线程 A 中 join() 方法返回），主线程能够看到子线程的操作。当然所谓的“看到”，指的是对**共享变量**的操作。

换句话说就是，如果在线程 A 中，调用线程 B 的 join() 并成功返回，那么线程 B 中的任意操作 Happens-Before 于该 join() 操作的返回。具体可参考下面示例代码。

```java

Thread B = new Thread(()->{
  // 此处对共享变量var修改
  var = 66;
});
// 例如此处对共享变量修改，
// 则这个修改结果对线程B可见
// 主线程启动子线程
B.start();
B.join()
// 子线程所有对共享变量的修改
// 在主线程调用B.join()之后皆可见
// 此例中，var==66
```



#### 被我们忽视的 final

前面我们讲 volatile 为的是禁用缓存以及编译优化，我们再从另外一个方面来看，有没有办法告诉编译器优化得更好一点呢？这个可以有，就是 **final 关键字**。

**final 修饰变量时，初衷是告诉编译器：这个变量生而不变，可以可劲儿优化。**Java 编译器在 1.5 以前的版本的确优化得很努力，以至于都优化错了。

问题类似于上一期提到的利用双重检查方法创建单例，构造函数的错误重排导致线程可能看到 final 变量的值会变化。详细的案例可以参考这个文档。

当然了，在 1.5 以后 Java 内存模型对 final 类型变量的重排进行了约束。现在只要我们提供正确构造函数没有“逸出”，就不会出问题了。

“逸出”有点抽象，我们还是举个例子吧，在下面例子中，在构造函数里面将 this 赋值给了全局变量 global.obj，这就是“逸出”，线程通过 global.obj 读取 x 是有可能读到 0 的。因此我们一定要避免“逸出”。

```java

// 以下代码来源于【参考1】
final int x;
// 错误的构造函数
public FinalFieldExample() { 
  x = 3;
  y = 4;
  // 此处就是讲this逸出，
  global.obj = this;
}
```



#### 总结

Java 的内存模型是并发编程领域的一次重要创新，之后 C++、C#、Golang 等高级语言都开始支持内存模型。Java 内存模型里面，最晦涩的部分就是 Happens-Before 规则了，Happens-Before 规则最初是在一篇叫做 Time, Clocks, and the Ordering of Events in a Distributed System 的论文中提出来的，在这篇论文中，Happens-Before 的语义是一种因果关系。在现实世界里，如果 A 事件是导致 B 事件的起因，那么 A 事件一定是先于（Happens-Before）B 事件发生的，这个就是 Happens-Before 语义的现实理解。

<br>

在 Java 语言里面，Happens-Before 的语义本质上是一种可见性，A Happens-Before B 意味着 A 事件对 B 事件来说是可见的，无论 A 事件和 B 事件是否发生在同一个线程里。例如 A 事件发生在线程 1 上，B 事件发生在线程 2 上，Happens-Before 规则保证线程 2 上也能看到 A 事件的发生。

<br>

Java 内存模型主要分为两部分，一部分面向你我这种编写并发程序的应用开发人员，另一部分是面向 JVM 的实现人员的，我们可以重点关注前者，也就是和编写并发程序相关的部分，这部分内容的核心就是 Happens-Before 规则。相信经过本章的介绍，你应该对这部分内容已经有了深入的认识。





## 03 | 互斥锁（上）：解决原子性问题

在第一篇文章中我们提到，一个或者多个操作在 CPU 执行的过程中不被中断的特性，称为“原子性”。理解这个特性有助于你分析并发编程 Bug 出现的原因，例如利用它可以分析出 long 型变量在 32 位机器上读写可能出现的诡异 Bug，明明已经把变量成功写入内存，重新读出来却不是自己写入的。



那原子性问题到底该如何解决呢？

你已经知道，原子性问题的源头是**线程切换**，如果能够禁用线程切换那不就能解决这个问题了吗？而操作系统做线程切换是依赖 CPU 中断的，所以禁止 CPU 发生中断就能够禁止线程切换。



在早期单核 CPU 时代，这个方案的确是可行的，而且也有很多应用案例，但是并不适合多核场景。这里我们以 32 位 CPU 上执行 long 型变量的写操作为例来说明这个问题，long 型变量是 64 位，在 32 位 CPU 上执行写操作会被拆分成两次写操作（写高 32 位和写低 32 位，如下图所示）。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416162430281.png)



在单核 CPU 场景下，同一时刻只有一个线程执行，禁止 CPU 中断，意味着操作系统不会重新调度线程，也就是禁止了线程切换，获得 CPU 使用权的线程就可以不间断地执行，所以两次写操作一定是：要么都被执行，要么都没有被执行，具有原子性。



但是在多核场景下，同一时刻，有可能有两个线程同时在执行，一个线程执行在 CPU-1 上，一个线程执行在 CPU-2 上，此时禁止 CPU 中断，只能保证 CPU 上的线程连续执行，并不能保证同一时刻只有一个线程执行，如果这两个线程同时写 long 型变量高 32 位的话，那就有可能出现我们开头提及的诡异 Bug 了。

<br>

“**同一时刻只有一个线程执行**”这个条件非常重要，我们称之为**互斥**。如果我们能够保证对共享变量的修改是互斥的，那么，无论是单核 CPU 还是多核 CPU，就都能保证原子性了。



### 简易锁模型

当谈到互斥，相信聪明的你一定想到了那个杀手级解决方案：锁。同时大脑中还会出现以下模型：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416162515862.png)



我们把一段需要互斥执行的代码称为**临界区**。线程在进入临界区之前，首先尝试加锁 lock()，如果成功，则进入临界区，此时我们称这个线程持有锁；否则呢就等待，直到持有锁的线程解锁；持有锁的线程执行完临界区的代码后，执行解锁 unlock()。

这个过程非常像办公室里高峰期抢占坑位，每个人都是进坑锁门（加锁），出坑开门（解锁），如厕这个事就是临界区。很长时间里，我也是这么理解的。这样理解本身没有问题，但却很容易让我们忽视两个非常非常重要的点：我们锁的是什么？我们保护的又是什么？

### 改进后的锁模型

我们知道在现实世界里，锁和锁要保护的资源是有对应关系的，比如你用你家的锁保护你家的东西，我用我家的锁保护我家的东西。在并发编程世界里，锁和资源也应该有这个关系，但这个关系在我们上面的模型中是没有体现的，所以我们需要完善一下我们的模型。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416162654653.png)



首先，我们要把临界区要保护的资源标注出来，如图中临界区里增加了一个元素：受保护的资源 R；其次，我们要保护资源 R 就得为它创建一把锁 LR；最后，针对这把锁 LR，我们还需在进出临界区时添上加锁操作和解锁操作。另外，在锁 LR 和受保护资源之间，我特地用一条线做了关联，这个关联关系非常重要。很多并发 Bug 的出现都是因为把它忽略了，然后就出现了类似锁自家门来保护他家资产的事情，这样的 Bug 非常不好诊断，因为潜意识里我们认为已经正确加锁了。



### Java 语言提供的锁技术：synchronized

锁是一种通用的技术方案，Java 语言提供的 synchronized 关键字，就是锁的一种实现。synchronized 关键字可以用来**修饰方法**，也可以用来**修饰代码块**，它的使用示例基本上都是下面这个样子：

```java

class X {
  // 1)修饰非静态方法
  synchronized void foo() {
    // 临界区
  }
  
  // 2)修饰静态方法
  synchronized static void bar() {
    // 临界区
  }
  
  // 3)修饰代码块
  Object obj = new Object()；
  void baz() {
    synchronized(obj) {
      // 临界区
    }
  }
  
}  
```



看完之后你可能会觉得有点奇怪，这个和我们上面提到的模型有点对不上号啊，加锁 lock() 和解锁 unlock() 在哪里呢？其实这两个操作都是有的，只是这两个操作是被 Java 默默加上的，Java 编译器会在 synchronized 修饰的方法或代码块前后自动加上加锁 lock() 和解锁 unlock()，这样做的好处就是加锁 lock() 和解锁 unlock() 一定是成对出现的，毕竟忘记解锁 unlock() 可是个致命的 Bug（意味着其他线程只能死等下去了）。

那 synchronized 里的加锁 lock() 和解锁 unlock() 锁定的对象在哪里呢？上面的代码我们看到只有修饰代码块的时候，锁定了一个 obj 对象，那修饰方法的时候锁定的是什么呢？这个也是 Java 的一条隐式规则：

> 当修饰静态方法的时候，锁定的是当前类的 Class 对象，在上面的例子中就是 Class X；
>
> 当修饰非静态方法的时候，锁定的是当前实例对象 this。



对于上面的例子，synchronized 修饰静态方法相当于:

```java

class X {
  // 修饰静态方法
  synchronized(X.class) static void bar() {
    // 临界区
  }
}
```

修饰非静态方法，相当于：

```java

class X {
  // 修饰非静态方法
  synchronized(this) void foo() {
    // 临界区
  }
}
```



### 用 synchronized 解决 count+=1 问题

相信你一定记得我们前面文章中提到过的 count+=1 存在的并发问题，现在我们可以尝试用 synchronized 来小试牛刀一把，代码如下所示。SafeCalc 这个类有两个方法：一个是 get() 方法，用来获得 value 的值；另一个是 addOne() 方法，用来给 value 加 1，并且 addOne() 方法我们用 synchronized 修饰。那么我们使用的这两个方法有没有并发问题呢？

```java

class SafeCalc {
  long value = 0L;
  long get() {
    return value;
  }
  synchronized void addOne() {
    value += 1;
  }
}
```

我们先来看看 addOne() 方法，首先可以肯定，被 synchronized 修饰后，无论是单核 CPU 还是多核 CPU，只有一个线程能够执行 addOne() 方法，所以一定能保证原子操作，那是否有可见性问题呢？要回答这问题，就要重温一下上一篇文章中提到的管程中锁的规则。

> 管程中锁的规则：对一个锁的解锁 Happens-Before 于后续对这个锁的加锁。



管程，就是我们这里的 synchronized（至于为什么叫管程，我们后面介绍），我们知道 synchronized 修饰的临界区是互斥的，也就是说同一时刻只有一个线程执行临界区的代码；而所谓“对一个锁解锁 Happens-Before 后续对这个锁的加锁”，指的是前一个线程的解锁操作对后一个线程的加锁操作可见，综合 Happens-Before 的传递性原则，我们就能得出前一个线程在临界区修改的共享变量（该操作在解锁之前），对后续进入临界区（该操作在加锁之后）的线程是可见的。

按照这个规则，如果多个线程同时执行 addOne() 方法，可见性是可以保证的，也就说如果有 1000 个线程执行 addOne() 方法，最终结果一定是 value 的值增加了 1000。看到这个结果，我们长出一口气，问题终于解决了。

但也许，你一不小心就忽视了 get() 方法。执行 addOne() 方法后，value 的值对 get() 方法是可见的吗？这个可见性是没法保证的。管程中锁的规则，是只保证后续对这个锁的加锁的可见性，而 get() 方法并没有加锁操作，所以可见性没法保证。那如何解决呢？很简单，就是 get() 方法也 synchronized 一下，完整的代码如下所示。

```java

class SafeCalc {
  long value = 0L;
  synchronized long get() {
    return value;
  }
  synchronized void addOne() {
    value += 1;
  }
}
```

上面的代码转换为我们提到的锁模型，就是下面图示这个样子。get() 方法和 addOne() 方法都需要访问 value 这个受保护的资源，这个资源用 this 这把锁来保护。线程要进入临界区 get() 和 addOne()，必须先获得 this 这把锁，这样 get() 和 addOne() 也是互斥的。



保护临界区 get() 和 addOne() 的示意图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416163536744.png)



这个模型更像现实世界里面球赛门票的管理，一个座位只允许一个人使用，这个座位就是“受保护资源”，球场的入口就是 Java 类里的方法，而门票就是用来保护资源的“锁”，Java 里的检票工作是由 synchronized 解决的。

### 锁和受保护资源的关系

我们前面提到，受保护资源和锁之间的关联关系非常重要，他们的关系是怎样的呢？一个合理的关系是：**受保护资源和锁之间的关联关系是 N:1 的关系。**

还拿前面球赛门票的管理来类比，就是一个座位，我们只能用一张票来保护，如果多发了重复的票，那就要打架了。现实世界里，我们可以用多把锁来保护同一个资源，但在并发领域是不行的，并发领域的锁和现实世界的锁不是完全匹配的。不过倒是可以用同一把锁来保护多个资源，这个对应到现实世界就是我们所谓的“包场”了。

上面那个例子我稍作改动，把 value 改成静态变量，把 addOne() 方法改成静态方法，此时 get() 方法和 addOne() 方法是否存在并发问题呢？

```java

class SafeCalc {
  static long value = 0L;
  synchronized long get() {
    return value;
  }
  synchronized static void addOne() {
    value += 1;
  }
}
```

如果你仔细观察，就会发现改动后的代码是用两个锁保护一个资源。这个受保护的资源就是静态变量 value，两个锁分别是 this 和 SafeCalc.class。我们可以用下面这幅图来形象描述这个关系。由于临界区 get() 和 addOne() 是用两个锁保护的，因此这两个临界区没有互斥关系，临界区 addOne() 对 value 的修改对临界区 get() 也没有可见性保证，这就导致并发问题了。

两把锁保护一个资源的示意图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416163943804.png)





### 总结

互斥锁，在并发领域的知名度极高，只要有了并发问题，大家首先容易想到的就是加锁，因为大家都知道，加锁能够保证执行临界区代码的互斥性。

这样理解虽然正确，但是却不能够指导你真正用好互斥锁。临界区的代码是操作受保护资源的路径，类似于球场的入口，入口一定要检票，也就是要加锁，但不是随便一把锁都能有效。所以必须深入分析锁定的对象和受保护资源的关系，综合考虑受保护资源的访问路径，多方面考量才能用好互斥锁。

synchronized 是 Java 在语言层面提供的互斥原语，其实 Java 里面还有很多其他类型的锁，但作为互斥锁，原理都是相通的：锁，一定有一个要锁定的对象，至于这个锁定的对象要保护的资源以及在哪里加锁 / 解锁，就属于设计层面的事情了。





## 04 | 互斥锁（下）：如何用一把锁保护多个资源？

在上一篇文章中，我们提到**受保护资源和锁之间合理的关联关系应该是 N:1 的关系**，也就是说可以用一把锁来保护多个资源，但是不能用多把锁来保护一个资源，并且结合文中示例，我们也重点强调了“不能用多把锁来保护一个资源”这个问题。而至于如何保护多个资源，我们今天就来聊聊。



## 05 | 一不小心就死锁了，怎么办？

在上一篇文章中，我们用 Account.class 作为互斥锁，来解决银行业务里面的转账问题，虽然这个方案不存在并发问题，但是所有账户的转账操作都是串行的，例如账户 A 转账户 B、账户 C 转账户 D 这两个转账操作现实世界里是可以并行的，但是在这个方案里却被串行化了，这样的话，性能太差。

试想互联网支付盛行的当下，8 亿网民每人每天一笔交易，每天就是 8 亿笔交易；每笔交易都对应着一次转账操作，8 亿笔交易就是 8 亿次转账操作，也就是说平均到每秒就是近 1 万次转账操作，若所有的转账操作都串行，性能完全不能接受。



那下面我们就尝试着把性能提升一下。



### 向现实世界要答案

上面这个过程在编程的世界里怎么实现呢？其实用两把锁就实现了，转出账本一把，转入账本另一把。在 transfer() 方法内部，我们首先尝试锁定转出账户 this（先把转出账本拿到手），然后尝试锁定转入账户 target（再把转入账本拿到手），只有当两者都成功时，才执行转账操作。这个逻辑可以图形化为下图这个样子。

两个转账操作并行示意图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416165129677.png)



而至于详细的代码实现，如下所示。经过这样的优化后，账户 A 转账户 B 和账户 C 转账户 D 这两个转账操作就可以并行了。

```java

class Account {
  private int balance;
  // 转账
  void transfer(Account target, int amt){
    // 锁定转出账户
    synchronized(this) {              
      // 锁定转入账户
      synchronized(target) {           
        if (this.balance > amt) {
          this.balance -= amt;
          target.balance += amt;
        }
      }
    }
  } 
  
}
```



### 没有免费的午餐

上面的实现看上去很完美，并且也算是将锁用得出神入化了。相对于用 Account.class 作为互斥锁，锁定的范围太大，而我们锁定两个账户范围就小多了，这样的锁，上一章我们介绍过，叫**细粒度锁。使用细粒度锁可以提高并行度，是性能优化的一个重要手段。**



这个时候可能你已经开始警觉了，使用细粒度锁这么简单，有这样的好事，是不是也要付出点什么代价啊？编写并发程序就需要这样时时刻刻保持谨慎。

**的确，使用细粒度锁是有代价的，这个代价就是可能会导致死锁。**



在详细介绍死锁之前，我们先看看现实世界里的一种特殊场景。如果有客户找柜员张三做个转账业务：账户 A 转账户 B 100 元，此时另一个客户找柜员李四也做个转账业务：账户 B 转账户 A 100 元，于是张三和李四同时都去文件架上拿账本，这时候有可能凑巧张三拿到了账本 A，李四拿到了账本 B。张三拿到账本 A 后就等着账本 B（账本 B 已经被李四拿走），而李四拿到账本 B 后就等着账本 A（账本 A 已经被张三拿走），他们要等多久呢？他们会永远等待下去…因为张三不会把账本 A 送回去，李四也不会把账本 B 送回去。我们姑且称为死等吧。

转账业务中的“死等”

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416165454266.png)

现实世界里的死等，就是编程领域的死锁了。死锁的一个比较专业的定义是：**一组互相竞争资源的线程因互相等待，导致“永久”阻塞的现象。**





上面转账的代码是怎么发生死锁的呢？我们假设线程 T1 执行账户 A 转账户 B 的操作，账户 A.transfer(账户 B)；同时线程 T2 执行账户 B 转账户 A 的操作，账户 B.transfer(账户 A)。当 T1 和 T2 同时执行完①处的代码时，T1 获得了账户 A 的锁（对于 T1，this 是账户 A），而 T2 获得了账户 B 的锁（对于 T2，this 是账户 B）。之后 T1 和 T2 在执行②处的代码时，T1 试图获取账户 B 的锁时，发现账户 B 已经被锁定（被 T2 锁定），所以 T1 开始等待；T2 则试图获取账户 A 的锁时，发现账户 A 已经被锁定（被 T1 锁定），所以 T2 也开始等待。于是 T1 和 T2 会无期限地等待下去，也就是我们所说的死锁了。



```java

class Account {
  private int balance;
  // 转账
  void transfer(Account target, int amt){
    // 锁定转出账户
    synchronized(this){     ①
      // 锁定转入账户
      synchronized(target){ ②
        if (this.balance > amt) {
          this.balance -= amt;
          target.balance += amt;
        }
      }
    }
  } 
}
```

关于这种现象，我们还可以借助**资源分配图**来可视化锁的占用情况（资源分配图是个有向图，它可以描述资源和线程的状态）。其中，资源用方形节点表示，线程用圆形节点表示；资源中的点指向线程的边表示线程已经获得该资源，线程指向资源的边则表示线程请求资源，但尚未得到。转账发生死锁时的资源分配图就如下图所示，一个“各据山头死等”的尴尬局面。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416165618844.png)



### 如何预防死锁

并发程序一旦死锁，一般没有特别好的方法，很多时候我们只能重启应用。因此，解决死锁问题最好的办法还是规避死锁。

那如何避免死锁呢？要避免死锁就需要分析死锁发生的条件，有个叫 Coffman 的牛人早就总结过了，只有以下这四个条件都发生时才会出现死锁：

1. 互斥，共享资源 X 和 Y 只能被一个线程占用；
2. 占有且等待，线程 T1 已经取得共享资源 X，在等待共享资源 Y 的时候，不释放共享资源 X；
3. 不可抢占，其他线程不能强行抢占线程 T1 占有的资源；
4. 循环等待，线程 T1 等待线程 T2 占有的资源，线程 T2 等待线程 T1 占有的资源，就是循环等待。



<br>

反过来分析，**也就是说只要我们破坏其中一个，就可以成功避免死锁的发生。**

其中，互斥这个条件我们没有办法破坏，因为我们用锁为的就是互斥。不过其他三个条件都是有办法破坏掉的，到底如何做呢？

1. 对于“占用且等待”这个条件，我们可以一次性申请所有的资源，这样就不存在等待了。
2. 对于“不可抢占”这个条件，占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源，这样不可抢占这个条件就破坏掉了。
3. 对于“循环等待”这个条件，可以靠按序申请资源来预防。所谓按序申请，是指资源是有线性顺序的，申请的时候可以先申请资源序号小的，再申请资源序号大的，这样线性化后自然就不存在循环了。



我们已经从理论上解决了如何预防死锁，那具体如何体现在代码上呢？下面我们就来尝试用代码实践一下这些理论。











## 14 | Lock和Condition（上）：隐藏在并发包中的管程



Java SDK 并发包内容很丰富，包罗万象，但是我觉得最核心的还是其对管程的实现。因为理论上利用管程，你几乎可以实现并发包里所有的工具类。

**Java SDK 并发包通过 Lock 和 Condition 两个接口来实现管程，其中 Lock 用于解决互斥问题，Condition 用于解决同步问题。**

### 什么是可重入锁

如果你细心观察，会发现我们创建的锁的具体类名是 ReentrantLock，这个翻译过来叫**可重入锁**，这个概念前面我们一直没有介绍过。**所谓可重入锁，顾名思义，指的是线程可以重复获取同一把锁。**



### 用锁的最佳实践

你已经知道，用锁虽然能解决很多并发问题，但是风险也是挺高的。可能会导致死锁，也可能影响性能。这方面有是否有相关的最佳实践呢？

有，还很多。但是我觉得最值得推荐的是并发大师 Doug Lea《Java 并发编程：设计原则与模式》一书中，推荐的三个用锁的最佳实践，它们分别是：

- 永远只在更新对象的成员变量时加锁
- 永远只在访问可变的成员变量时加锁
- 永远不在调用其他对象的方法时加锁



并发问题，本来就难以诊断，所以你一定要让你的代码尽量安全，尽量简单，哪怕有一点可能会出问题，都要努力避免。

### 总结

Java SDK 并发包里的 Lock 接口里面的每个方法，你可以感受到，都是经过深思熟虑的。除了支持类似 synchronized 隐式加锁的 lock() 方法外，还支持超时、非阻塞、可中断的方式获取锁，这三种方式为我们编写更加安全、健壮的并发程序提供了很大的便利。希望你以后在使用锁的时候，一定要仔细斟酌。







# 以下待整理



## 1.1 Java 线程的创建方式

### 继承 Thread 类创建线程

```java
// 1, 为什么不直接调用了run方法，而是调用start启动线程。
// 直接调用run方法会当成普通方法执行，此时相当于还是单线程执行。
// 只有调用start方法才是启动一个新的线程执行。

public class ThreadDemo1 {

    public static void main(String[] args) {
        // 3, 实例化新线程对象，调用start()方法启动线程
        Thread myThread = new MyThread();
        // start()方法是一个native方法，通过在操作系统上启动一个新线程，并最终执行run方法来启动一个线程。
        // 通知CPU以线程的方式启动run()方法
        myThread.start();

        for (int i = 0; i < 5; i++) {
            System.out.println("主线程执行输出+++" + i);
        }
    }

}

// Thread类 implements实现了 Runnable接口，并定义了操作线程的一些方法。
// 1,定义一个线程类继承Thread（创建了一个线程）
class MyThread extends Thread{
    
    // 2,重写run方法，里面是定义线程以后要干啥
    @Override
    public void run() {
        //super.run();
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程执行输出..." + i);
        }
    }
}
```

### 实现Runnable接口

- 如果子类已经继承了一个类，就无法再直接继承Thread类，此时可以通过实现Runnable接口创建线程。
- 用 Runnable 更容易与线程池等高级 API 配合

```java
// 1,定义一个线程任务类MyRunnable实现 Runnable 接口，重写run()方法
class ChildrenClassThread implements Runnable {

    // 2,重写run()方法，定义线程的执行任务
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("子线程执行输出..." + i);
        }
    }
}

public static void main(String[] args) {
  // 3, 创建一个任务对象
  ChildrenClassThread childrenClassThread = new ChildrenClassThread();
  // 4, 交给线程对象,并启动线程
  new Thread(childrenClassThread).start();
  for (int i = 0; i < 10; i++) {
    System.out.println("主线程执行输出..." + i);
  }
}
```



### 实现Callable接口

- 通过 `Callable` 接口配合 `FutureTask` 类来创建线程，使用该方法创建线程能够支持带返回值的任务
  - 前面的那两种方法是不支持带返回值的。
  - 通过实现`Callable`接口的`call`方法来描述带有返回值的任务
  - `FutureTask`就是对于具体的`Runnable`或者`Callable`任务的执行结果进行取消、查询是否完成、获取返回值。
    - 必要时可以通过`get`方法获取执行结果（返回值），如果任务还没有执行完毕，该方法会阻塞直到任务返回结果。
- 在创建线程的时候，传入的引用不能是`Callable`类型，而应该是`FutrueTask`类型
  - `FutrueTask`类实现了`Runnable`类，所以在此之前我们需要把实现`Callable`接口的对象引用传给`FutrueTask`类的实例对象。

<br>

- 综上，`Callable`用来描述任务，`FutureTask`类用来管理`Callable`任务的执行结果。

<br>

🌸参考代码：

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                return 100 * (1 + 100) / 2;
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        // 获取执行结果
        System.out.println(futureTask.get());
    }
}
```

🌸运行结果：

```java
5050

Process finished with exit code 0
```



#### 通过ExecutorService和Callable`<Class>`实现有返回值的线程（《offer来了》中内容）

```java
public class ThreadDemo33 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建固定大小的线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        // 创建有多个返回值的任务列表list
        List<Future> list = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            // 创建Callable任务对象
            MyCallable33 myCallable = new MyCallable33(i);
            // 提交线程，获取Future对象并将其保存到Future List中
            Future future = pool.submit(myCallable);
            list.add(future);
        }
        // 关闭线程池,等待线程执行结束
        pool.shutdownNow();
        // 遍历所有线程的运行结果
        for (Future future : list) {
            System.out.println(future.get().toString());
        }

    }
}

// 1, 定义一个任务类，实现Callable接口，应该申明线程任务执行完毕后返回结果的数据类型
class MyCallable33 implements Callable<String>{
    private final int n;

    public MyCallable33(int n) {
        this.n = n;
    }

    // 2, 重写call()方法（任务方法）
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        // 查看下当前线程的名字和id
        System.out.println(Thread.currentThread().getName() + "&&id: " + Thread.currentThread().getId());
        
        return "子线程执行结果是：" + sum + "";
    }
}
```



#### FutureTask 配合 Thread

FutureTask 能够接收 Callable 类型的参数，用来处理有返回结果的情况

```java
// 创建任务对象
FutureTask<Integer> task3 = new FutureTask<>(() -> {
    log.debug("hello");
    return 100;
});
// 参数1 是任务对象; 参数2 是线程名字，推荐
new Thread(task3, "t3").start();
// 主线程阻塞，同步等待 task 执行完毕的结果
Integer result = task3.get();
log.debug("结果是:{}", result);
```

输出：

```bash
19:22:27 [t3] c.ThreadStarter - hello
19:22:27 [main] c.ThreadStarter - 结果是:100
```



### 基于线程池

创建一个线程池并用该线程池提交线程任务。

```java

```

## 1.2线程池

### 线程池的工作原理

程序中，我们会用各种池化技术来缓存付出昂贵代价创建的对象，比如线程池、连接池、内存池。

一般是预先创建一些对象放入池中，使用的时候直接取出使用，用完归还以便复用，还会通过一定的策略调整池中缓存对象的数量，实现池的动态伸缩。

由于线程的创建比较昂贵，随意、没有控制地创建大量线程会造成性能问题，因此短平快的任务一般考虑使用线程池来处理，而不是直接创建线程。



### 核心组件（4个）和核心类

- 线程池管理器（用于创建并管理线程池）
- 工作线程（线程池中执行具体任务的线程）
- 任务接口（用于定义工作线程的调度和执行策略）
- 任务队列（存放待处理的任务）

​                                                                                                                

线程池的体系结构

 java.util.concurrent.Executor : 负责线程的使用与调度的根接口

```lua
 |--ExecutorService 子接口：线程池的主要接口
   |--ThreadPoolExecutor 线程池的实现类
   |--ScheduledExecutorService 子接口：负责线程的调度
     |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
```



ThreadPoolExecutor 是构建线程池的核心方法，定义如下

```java
// ThreadPoolExecutor 的构造方法
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
   ........................
}
```

参数说明

| 序号 | 参数            | 说明                                             |
| ---- | --------------- | ------------------------------------------------ |
| 1    | corePoolSize    | 核心线程数量                                     |
| 2    | maximumPoolSize | 最大线程数量                                     |
| 3    | keepAliveTime   | 当前线程数大于corePoolSize时，空闲线程的存活时间 |
| 4    | unit            | keepAliveTime的时间单位                          |
| 5    | workQueue       | 任务队列，被提交但尚未被执行的任务存放的地方     |
| 6    | threadFactory   | 线程工厂，用于创建线程                           |
| 7    | handler         | 任务拒绝策略                                     |

### Java 线程池的工作流程





### 线程池的拒绝策略

- CallerRunsPolicy：提交任务的线程自己去执行该任务。

- AbortPolicy：默认的拒绝策略，会 throws RejectedExecutionException。
- DiscardPolicy：直接丢弃任务，没有任何异常抛出。
- DiscardOldestPolicy：丢弃最老的任务，其实就是把最早进入工作队列的任务丢弃，然后把新任务加入到工作队列。

- 自定义拒绝策略：自己扩展 RejectedExecutionException 接口来实现拒绝策略



### 5种常用的线程池

Java定义了 `Executor`接口并在该接口中定义了`execute()` 方法（唯一一个方法）用于执行一个线程任务，然后通过 ExecutorService 继承Executor接口并执行具体的线程操作。

`ExecutorService` 接口有多个实现类可用于创建不同的线程池，如下所示是5种常用的线程池。

| 名称                    | 说明                          |
| ----------------------- | ----------------------------- |
| newCachedThreadPool     | 可缓存的线程池                |
| newFixedThreadPool      | 固定大小的线程池              |
| newScheduledThreadPool  | 可做任务调度的线程池          |
| newSingleThreadExecutor | 单个线程的线程池              |
| newWorkStealingPool     | 足够大小的线程池，JDK 1.8新增 |



#### newScheduledThreadPool（可定时调度）

newScheduledThreadPool创建了一个可定时调度的线程池，可设置在给定的延迟时间后执行或者定期执行某个线程任务：

```java
public class ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

        // 1 创建一个延迟3秒执行的线程
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 3 seconds execute.");
            }
        },3, TimeUnit.SECONDS);
        
        // 2 创建一个延迟1秒执行且每3秒执行一次的线程
        //scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
        //    @Override
        //    public void run() {
        //        System.out.println("delay 1 seconds. repeat execute every 3 seconds");
        //    }
        //}, 1, 3, TimeUnit.SECONDS);

        scheduledThreadPool.shutdown();
    }

}
```





#### newSingleThreadExecutor（永远有且只有一个可用的）

保证永远有且只有一个可用的线程，在该线程停止或发生异常时，newSingleThreadExecutor线程池会启动一个新的线程来代替该线程继续执行任务

```java
ExecutorService singleThread = Executors.newSingleThreadExecutor();
```







### 线程池使用注意

#### 22 | Executor与线程池：如何创建正确的线程池？（极客时间内容）

虽然在 Java 语言中创建线程看上去就像创建一个对象一样简单，只需要 new Thread() 就可以了，但实际上创建线程远不是创建一个对象那么简单。

- 创建对象：仅仅是在 JVM 的堆里分配一块内存而已

- 而创建一个线程，却需要调用操作系统内核的 API，然后操作系统要为线程分配一系列的资源，这个成本就很高了。
  - 所以**线程是一个重量级的对象，应该避免频繁创建和销毁**。
  - 如何避免呢？线程池

##### （1）线程池是一种生产者 - 消费者模式

- 线程池的设计，没有办法直接采用一般意义上池化资源的设计方法

```java
//采用一般意义上池化资源的设计方法
class ThreadPool{
  // 获取空闲线程
  Thread acquire() {
  }
  // 释放线程
  void release(Thread t){
  }
} 
//期望的使用
ThreadPool pool；
Thread T1=pool.acquire();
//传入Runnable对象
T1.execute(()->{
  //具体业务逻辑
  ......
});
```

- 目前业界线程池的设计，普遍采用的都是生产者 - 消费者模式。线程池的使用方是生产者，线程池本身是消费者。
  - 下面的示例代码中，我们创建了一个非常简单的线程池 MyThreadPool，你可以通过它来理解线程池的工作原理。

```java
//简化的线程池，仅用来说明工作原理
class MyThreadPool{
  //利用阻塞队列实现生产者-消费者模式
  BlockingQueue<Runnable> workQueue;
  
  //保存内部工作线程
  List<WorkerThread> threads = new ArrayList<>();
  // 构造方法
  MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue){
    this.workQueue = workQueue;
    // 创建工作线程
    for(int idx=0; idx<poolSize; idx++){
      WorkerThread work = new WorkerThread();
      work.start();
      threads.add(work);
    }
  }
  
  // 提交任务
  void execute(Runnable command){
    workQueue.put(command);
  }
  
  // 工作线程负责消费任务，并执行任务
  class WorkerThread extends Thread{
    public void run() {
      //循环取任务并执行
      while(true){ ①
        Runnable task = workQueue.take();
        task.run();
      } 
    }
  }
  
}

/** 下面是使用示例 **/
// 创建有界阻塞队列
BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
// 创建线程池  
MyThreadPool pool = new MyThreadPool(10, workQueue);

// 提交任务  
pool.execute(()->{
    System.out.println("hello");
});
```

- 线程池的工作原理
  - 在 MyThreadPool 的内部，我们维护了一个阻塞队列 workQueue 和一组工作线程，工作线程的个数由构造函数中的 poolSize 来指定。
  - 用户通过调用 execute() 方法来提交 Runnable 任务，execute() 方法的内部实现仅仅是将任务加入到 workQueue 中。
  - MyThreadPool 内部维护的工作线程会消费 workQueue 中的任务并执行任务，相关的代码就是代码①处的 while 循环。



##### （2）如何使用 Java 中的线程池

Java 并发包里提供的线程池，远比我们上面的示例代码强大得多，当然也复杂得多。

Java 提供的线程池相关的工具类中，最核心的是 **ThreadPoolExecutor**，通过名字你也能看出来，它强调的是 Executor，而不是一般意义上的池化资源。

ThreadPoolExecutor 的构造函数非常复杂，如下面代码所示，这个最完备的构造函数有 7 个参数。

```java
ThreadPoolExecutor(
  int corePoolSize,
  int maximumPoolSize,
  long keepAliveTime,
  TimeUnit unit,
  BlockingQueue<Runnable> workQueue,
  ThreadFactory threadFactory,
  RejectedExecutionHandler handler) 
```

你可以**把线程池类比为一个项目组，而线程就是项目组的成员**。

- **corePoolSize**：表示线程池保有的最小线程数。有些项目很闲，但是也不能把人都撤了，至少要留 corePoolSize 个人坚守阵地。

- **maximumPoolSize**：表示线程池创建的最大线程数。当项目很忙时，就需要加人，但是也不能无限制地加，最多就加到 maximumPoolSize 个人。当项目闲下来时，就要撤人了，最多能撤到 corePoolSize 个人。

- **keepAliveTime & unit**：上面提到项目根据忙闲来增减人员，那在编程世界里，如何定义忙和闲呢？很简单，一个线程如果在一段时间内，都没有执行任务，说明很闲，keepAliveTime 和 unit 就是用来定义这个“一段时间”的参数。也就是说，如果一个线程空闲了keepAliveTime & unit这么久，而且线程池的线程数大于 corePoolSize ，那么这个空闲的线程就要被回收了。

- **workQueue**：工作队列，和上面示例代码的工作队列同义。

- **threadFactory**：通过这个参数你可以自定义如何创建线程，例如你可以给线程指定一个有意义的名字。

- **handler**：通过这个参数你可以自定义任务的拒绝策略。如果线程池中所有的线程都在忙碌，并且工作队列也满了（前提是工作队列是有界队列），那么此时提交任务，线程池就会拒绝接收。至于拒绝的策略，你可以通过 handler 这个参数来指定。ThreadPoolExecutor 已经提供了以下 4 种策略。
  - CallerRunsPolicy：提交任务的线程自己去执行该任务。
  - AbortPolicy：默认的拒绝策略，会 throws RejectedExecutionException。
  - DiscardPolicy：直接丢弃任务，没有任何异常抛出。
  - DiscardOldestPolicy：丢弃最老的任务，其实就是把最早进入工作队列的任务丢弃，然后把新任务加入到工作队列。



[execute和submit的区别](https://blog.csdn.net/qq_50652600/article/details/123417584)

execute只能提交**Runnable类型**的任务，无返回值。

而submit既能提交**Runnable类型**的任务，返回值为null，也能提交**Callable**类型的任务，返回值为Future。







##### （3）使用线程池要注意些什么

- 不建议使用 Executors
  - Executors 提供的很多方法默认使用的都是无界的 LinkedBlockingQueue，高负载情境下，无界队列很容易导致 OOM，而 **OOM 会导致所有请求都无法处理，这是致命问题**。所以强烈建议使用有界队列。

- 默认拒绝策略要慎重使用
  - 对于运行时异常编译器并不强制 catch 它，所以开发人员很容易忽略。
  - 如果线程池处理的任务非常重要，建议自定义自己的拒绝策略；并且在实际工作中，自定义的拒绝策略往往和降级策略配合使用。

```bash
# 老师你好，使用有界队列虽然避免了OOM  但是如果请求量太大，我又不想丢弃和异常的情况下一般怎么实践呢。我对降级这一块没经验，我能直观想到的就是存放在缓存，如果缓存内存也不够了就只能持久化了

作者回复: 可以放数据库，放mq,redis，本地文件都可以，具体要看实际需求
```

#### 23 | Future：如何用多线程实现最优的“烧水泡茶”程序？（极客时间内容）

使用 ThreadPoolExecutor 的时候，如何获取任务执行结果。

##### （1）如何获取任务执行结果

Java 通过 ThreadPoolExecutor 提供的 3 个 submit() 方法和 1 个 FutureTask 工具类来支持获得任务执行结果的需求。

```java
// 提交Runnable任务
Future<?> submit(Runnable task);

// 提交Callable任务
<T> Future<T> submit(Callable<T> task);

// 提交Runnable任务及结果引用  
<T> Future<T> submit(Runnable task, T result);
```

你会发现它们的返回值都是 Future 接口，Future 接口有 5 个方法

- 取消任务的方法 cancel()
- 判断任务是否已取消的方法 isCancelled()
- 判断任务是否已结束的方法 isDone()以及
- 2 个获得任务执行结果的 get() 和 get(timeout, unit)
  - get(timeout, unit) 支持超时机制
  - 不过需要注意的是：这两个 get() 方法都是阻塞式的，如果被调用的时候，任务还没有执行完，那么调用 get() 方法的线程会阻塞，直到任务执行完才会被唤醒。

我们提交的任务不但能够获取任务执行结果，还可以取消任务。

```java
// 取消任务
boolean cancel(boolean mayInterruptIfRunning);
// 判断任务是否已取消  
boolean isCancelled();
// 判断任务是否已结束
boolean isDone();

// 获得任务执行结果
get();
// 获得任务执行结果，支持超时
get(long timeout, TimeUnit unit);
```



前面我们提到的 Future 是一个接口，而 FutureTask 是一个实实在在的工具类，这个工具类有两个构造函数。

```java
FutureTask(Callable<V> callable);
FutureTask(Runnable runnable, V result);
```

那如何使用 FutureTask 呢？

- FutureTask 实现了 Runnable 和 Future 接口
- 由于实现了 Runnable 接口，所以可以将 FutureTask 对象作为任务提交给 ThreadPoolExecutor 去执行，也可以直接被 Thread 执行

```java
// 创建FutureTask
FutureTask<Integer> futureTask = new FutureTask<>(()-> 1+2);
// 创建线程池
ExecutorService es = Executors.newCachedThreadPool();
// 提交FutureTask 
es.submit(futureTask);
// 获取计算结果
Integer result = futureTask.get();
```

- 又因为实现了 Future 接口，所以也能用来获得任务的执行结果。

```java
// 创建FutureTask
FutureTask<Integer> futureTask = new FutureTask<>(()-> 1+2);
// 创建并启动线程
Thread T1 = new Thread(futureTask);
T1.start();
// 获取计算结果
Integer result = futureTask.get();
```

##### （2）实现最优的“烧水泡茶”程序

烧水泡茶最优的工序应该是下面这样：

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/%E7%83%A7%E6%B0%B4%E6%B3%A1%E8%8C%B6.png)



并发编程可以总结为三个核心问题：分工、同步和互斥。

下面我们用程序来模拟一下这个最优工序。

编写并发程序，首先要做的就是分工，所谓分工指的是如何高效地拆解任务并分配给线程。

- 用两个线程 T1 和 T2 来完成烧水泡茶程序，T1 负责洗水壶、烧开水、泡茶这三道工序，T2 负责洗茶壶、洗茶杯、拿茶叶三道工序

- 其中 T1 在执行泡茶这道工序时需要等待 T2 完成拿茶叶的工序。对于 T1 的这个等待动作，你应该可以想出很多种办法，
  - 例如 Thread.join()、CountDownLatch，甚至阻塞队列都可以解决，
  - 不过今天我们用 Future 特性来实现。



```java
public class TeaTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // 创建任务T2的FutureTask
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        // 创建任务T1的FutureTask
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        // 线程T1执行任务ft1
        Thread T1 = new Thread(ft1);
        T1.start();
        // 线程T2执行任务ft2
        Thread T2 = new Thread(ft2);
        T2.start();

        // 等待线程T1执行结果
        System.out.println(ft1.get());

    }


    // T1Task需要执行的任务：
    // 洗水壶、烧开水、泡茶
    static class T1Task implements Callable<String> {
        FutureTask<String> ft2;

        // T1任务需要T2任务的FutureTask
        T1Task(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1:洗水壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1:烧开水...");
            TimeUnit.SECONDS.sleep(15);

            // 获取T2线程的茶叶 ⭐️⭐️⭐️⭐️⭐️
            String tf = ft2.get();
            System.out.println("T1:拿到茶叶:" + tf);

            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        }
    }

    // T2Task需要执行的任务:
    // 洗茶壶、洗茶杯、拿茶叶
    static class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("T2:洗茶壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2:洗茶杯...");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2:拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "龙井";
        }
    }
}
// 一次执行结果：
//        T1:洗水壶...
//        T2:洗茶壶...
//        T1:烧开水...
//        T2:洗茶杯...
//        T2:拿茶叶...
//        T1:拿到茶叶:龙井
//        T1:泡茶...
//        上茶:龙井
```

- 我们创建了两个 FutureTask——ft1 和 ft2，ft1 完成洗水壶、烧开水、泡茶的任务，ft2 完成洗茶壶、洗茶杯、拿茶叶的任务

- 这里需要注意的是 ft1 这个任务在执行泡茶任务前，需要等待 ft2 把茶叶拿来，**所以 ft1 内部需要引用 ft2，并在执行泡茶之前，调用 ft2 的 get() 方法实现等待。** 



##### （3）总结

**利用 Java 并发包提供的 Future 可以很容易获得异步任务的执行结果**，无论异步任务是通过线程池 ThreadPoolExecutor 执行的，还是通过手工创建子线程来执行的。

**利用多线程可以快速将一些串行的任务并行化，从而提高性能**；如果任务之间有依赖关系，比如当前任务依赖前一个任务的执行结果，这种问题基本上都可以用 Future 来解决。



[深入理解并发编程之线程池FutureTask](https://blog.csdn.net/qq_19586549/article/details/122980971)



<hr>


通过三个生产事故，来看看使用线程池应该注意些什么。

#### 线程池的声明需要手动进行

**Java** 中的 **Executors** 类定义了一些快捷的工具方法，来帮助我们快速创建线程池。《阿里巴巴Java开发手册》中提到，禁止使用这些方法来创建线程池，而应该手动 **new ThreadPoolExecutor** 来创建线程池。

这一条规则的背后，是大量血淋淋的生产事故，最典型的就是 **newFixedThreadPool** 和**newCachedThreadPool** ，可能因为资源耗尽导致 **OOM** 问题。

首先，我们来看一下 **newFixedThreadPool** 为什么可能会出现 **OOM** 的问题。

我们写一段测试代码，来初始化一个单线程的 **FixedThreadPool** ，循环1亿次向线程池提交任务，每个任务都会创建一个比较大的字符串然后休眠一小时：

```java
@GetMapping("oom1")
public void oom1() throws InterruptedException {
ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//打印线程池的信息，稍后我会解释这段代码
printStats(threadPool); 
for (int i = 0; i < 100000000; i++) {
    threadPool.execute(() -> {
        String payload = IntStream.rangeClosed(1, 1000000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString();
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
        }
        log.info(payload);
    });
}
 
threadPool.shutdown();
threadPool.awaitTermination(1, TimeUnit.HOURS);
 
}
```

执行程序后不久，日志中就出现了如下 **OOM** ：

```bash
Exception in thread "http-nio-45678-ClientPoller" java.lang.OutOfMemoryError: GC overhead limit exceeded
```

翻看 **newFixedThreadPool** 方法的源码不难发现，线程池的工作队列直接 **new** 了一个 **LinkedBlockingQueue**，而默认构造方法的 **LinkedBlockingQueue** 是一个 **Integer.MAX_VALUE** 长度的队列，可以认为是无界的：

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
return new ThreadPoolExecutor(nThreads, nThreads,
0L, TimeUnit.MILLISECONDS,
new LinkedBlockingQueue<Runnable>());
}
public class LinkedBlockingQueue<E> extends AbstractQueue<E>
implements BlockingQueue<E>, java.io.Serializable {
...
/**
 * Creates a {@code LinkedBlockingQueue} with a capacity of
 * {@link Integer#MAX_VALUE}.
 */
public LinkedBlockingQueue() {
    this(Integer.MAX_VALUE);
}
 
...
}
```

虽然使用 **newFixedThreadPool** 可以把工作线程控制在固定的数量上，但任务队列是无界的。如果任务较多并且执行较慢的话，队列可能会快速积压，撑爆内存导致OOM。

<br>

我们再把刚才的例子稍微改一下，改为使用 **newCachedThreadPool** 方法来获得线程池。程序运行不久后，同样看到了如下 **OOM** 异常：

```bash
[11:30:30.487] [http-nio-45678-exec-1] [ERROR] [.a.c.c.C.[.[.[/].[dispatcherServlet]:175 ] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Handler dispatch failed; nested exception is java.lang.OutOfMemoryError: unable to create new native thread] with root cause
java.lang.OutOfMemoryError: unable to create new native thread
```

从日志中可以看到，这次 **OOM** 的原因是无法创建线程，翻看 **newCachedThreadPool** 的源码可以看到，这种线程池的最大线程数是**Integer.MAX_VALUE**，可以认为是没有上限的，而其工作队列 **SynchronousQueue** 是一个没有存储空间的阻塞队列。这意味着，只要有请求到来，就必须找到一条工作线程来处理，如果当前没有空闲的线程就再创建一条新的。

由于我们的任务需要 **1** 小时才能执行完成，大量的任务进来后会创建大量的线程。我们知道线程是需要分配一定的内存空间作为线程栈的，比如 **1MB**，因此无限制创建线程必然会导致 **OOM**：

```java
public static ExecutorService newCachedThreadPool() {
	return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
}
```

其实，大部分 **Java** 开发同学知道这两种线程池的特性，只是抱有侥幸心理，觉得只是使用线程池做一些轻量级的任务，不可能造成队列积压或开启大量线程。

但，现实往往是残酷的。

我之前就遇到过这么一个事故：用户注册后，我们调用一个外部服务去发送短信，发送短信接口正常时可以在 **100** 毫秒内响应，TPS **100**的注册量，**CachedThreadPool** 能稳定在占用 **10** 个左右线程的情况下满足需求。在某个时间点，外部短信服务不可用了，我们调用这个服务的超时又特别长，比如1分钟，1分钟可能就进来了 **6000** 用户，产生 **6000** 个发送短信的任务，需要 **6000** 个线程，没多久就因为无法创建线程导致了 **OOM**，整个应用程序崩溃。

<br>

因此，我同样不建议使用 **Executors** 提供的两种快捷的线程池，原因如下：

我们需要根据自己的场景、并发情况来评估线程池的几个核心参数，包括核心线程数、最大线程数、线程回收策略、工作队列的类型，以及拒绝策略，确保线程池的工作行为符合需求，一般都需要设置有界的工作队列和可控的线程数。

任何时候，都应该为自定义线程池指定有意义的名称，以方便排查问题。当出现线程数量暴增、线程死锁、线程占用大量CPU、线程执行出现异常等问题时，我们往往会抓取线程栈。此时，有意义的线程名称，就可以方便我们定位问题。

除了建议手动声明线程池以外，我还**建议用一些监控手段来观察线程池的状态**。线程池这个组件往往会表现得任劳任怨、默默无闻，除非是出现了拒绝策略，否则压力再大都不会抛出一个异常。如果我们能提前观察到线程池队列的积压，或者线程数量的快速膨胀，往往可以提早发现并解决问题。



#### 线程池线程管理策略详解

在之前的 **Demo** 中，我们用一个 **printStats** 方法实现了最简陋的监控，每秒输出一次线程池的基本内部信息，包括线程数、活跃线程数、完成了多少任务，以及队列中还有多少积压任务等信息：

```java
private void printStats(ThreadPoolExecutor threadPool) {
   Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
        log.info("=========================");
        log.info("Pool Size: {}", threadPool.getPoolSize());
        log.info("Active Threads: {}", threadPool.getActiveCount());
        log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
        log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
		log.info("=========================");
	}, 0, 1, TimeUnit.SECONDS);
}
```

接下来，我们就利用这个方法来观察一下线程池的基本特性吧。

首先，自定义一个线程池。这个线程池具有 **2** 个核心线程、**5** 个最大线程、使用容量为 **10** 的 **ArrayBlockingQueue** 阻塞队列作为工作队列，使用默认的**AbortPolicy** 拒绝策略，也就是任务添加到线程池失败会抛出 **RejectedExecutionException** 。此外，我们借助了 **Jodd** 类库的 **ThreadFactoryBuilder** 方法来构造一个线程工厂，实现线程池线程的自定义命名。

然后，我们写一段测试代码来观察线程池管理线程的策略。测试代码的逻辑为，每次间隔 **1** 秒向线程池提交任务，循环 **20** 次，每个任务需要 **10** 秒才能执行完成，代码如下：

```java
@GetMapping("right")
public int right() throws InterruptedException {
//使用一个计数器跟踪完成的任务数
AtomicInteger atomicInteger = new AtomicInteger();
//创建一个具有2个核心线程、5个最大线程，使用容量为10的ArrayBlockingQueue阻塞队列作为工作队列的线程池，使用默认的AbortPolicy拒绝策略
ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
2, 5,
5, TimeUnit.SECONDS,
new ArrayBlockingQueue<>(10),
new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get(),
new ThreadPoolExecutor.AbortPolicy());
printStats(threadPool);
//每隔1秒提交一次，一共提交20次任务
IntStream.rangeClosed(1, 20).forEach(i -> {
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    int id = atomicInteger.incrementAndGet();
    try {
        threadPool.submit(() -> {
            log.info("{} started", id);
            //每个任务耗时10秒
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
            }
            log.info("{} finished", id);
        });
    } catch (Exception ex) {
        //提交出现异常的话，打印出错信息并为计数器减一
        log.error("error submitting task {}", id, ex);
        atomicInteger.decrementAndGet();
    }
});
 
TimeUnit.SECONDS.sleep(60);
return atomicInteger.intValue();
 
}
```

**60** 秒后页面输出了 **17**，有3次提交失败了：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220601145842933.png)



并且日志中也出现了 **3** 次类似的错误信息：

```bash
[14:24:52.879] [http-nio-45678-exec-1] [ERROR] [.t.c.t.demo1.ThreadPoolOOMController:103 ] - error submitting task 18
java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@163a2dec rejected from java.util.concurrent.ThreadPoolExecutor@18061ad2[Running, pool size = 5, active threads = 5, queued tasks = 10, completed tasks = 2]
```



我们把 **printStats** 方法打印出的日志绘制成图表，得出如下曲线：(https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220517082642564.png)

至此，我们可以总结出线程池默认的工作行为：

不会初始化 **corePoolSize** 个线程，有任务来了才创建工作线程；

- 当核心线程满了之后不会立即扩容线程池，而是把任务堆积到工作队列中；
- 当工作队列满了后扩容线程池，一直到线程个数达到 **maximumPoolSize** 为止；
- 如果队列已满且达到了最大线程后还有任务进来，按照拒绝策略处理；
- 当线程数大于核心线程数时，线程等待 **keepAliveTime** 后还是没有任务需要处理的话，收缩线程到核心线程数。

了解这个策略，有助于我们根据实际的容量规划需求，为线程池设置合适的初始化参数。当然，我们也可以通过一些手段来改变这些默认工作行为，比如：

- 声明线程池后立即调用 **prestartAllCoreThreads** 方法，来启动所有核心线程；
- 传入 **true** 给 **allowCoreThreadTimeOut** 方法，来让线程池在空闲的时候同样回收核心线程。







## 1.4 线程的基本方法

线程相关的基本方法有wait（等待）、notify（唤醒）、notifyAll、sleep（睡眠）、join（加入）、yield（让步）等，这些方法**控制线程的运行，并影响线程的状态变化**。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/image-20220601153635190.png)





### 线程等待 wait方法

调用wait方法的线程会进入 `WAITING` 状态，只有等到其他线程的通知或被中断后才会返回。

需要注意的是，在调用wait方法后会释放对象的锁，因此wait方法一般被用于同步方法或同步代码块中。

### 线程睡眠 sleep方法

调用sleep方法会导致当前线程休眠。

与wait方法不同的是，sleep方法不会释放当前占有的锁，会导致线程进入`TIMED-WATING` 状态，而wait方法会导致当前线程进入`WATING` 状态。

### 线程让步 yield方法

调用yield方法会使当前线程让出（释放）CPU执行时间片，与其他线程一起重新竞争CPU时间片。

在一般情况下，优先级高的线程更有可能竞争到CPU时间片，但这不是绝对的，有的操作系统对线程的优先级并不敏感。

### 线程中断 interrupt方法

interrupt方法用于向线程发行一个终止通知信号，会影响该线程内部的一个中断标识位，这个 线程本身并不会因为调用了interrupt方法而改变状态（阻塞、终止等）。状态的具体变化需要等待接收到中断标识的程序的最终处理结果来判定。

对interrupt方法的理解需要注意以下4个核心点。

- 调用interrupt方法并不会中断一个正在运行的线程，也就是说处于Running状态的线程并不会因为被中断而终止，仅仅改变了内部维护的中断标识位而已。具体的JDK源码如下：

```java

```



### 线程加入 join方法



### 线程唤醒 notify方法





### 后台守护线程 setDaemon方法



### 其它基本方法

```java
1. start():启动当前线程；调用当前线程的run()
2. run(): 通常需要重写Thread类中的此方法，将创建的线程要执行的操作声明在此方法中

3. currentThread():静态方法，返回执行当前代码的线程
4. getName():获取当前线程的名字
5. setName():设置当前线程的名字

6. yield():释放当前cpu的执行权
7. join():在线程a中调用线程b的join(),此时线程a就进入阻塞状态，直到线程b完全执行完以后，线程a才结束阻塞状态。
8. stop():已过时。当执行此方法时，强制结束当前线程。
9. sleep(long millitime):让当前线程“睡眠”指定的millitime毫秒。在指定的millitime毫秒时间内，当前线程是阻塞状态。
10. isAlive():判断当前线程是否存活
```





### 终止线程的4种方式

#### （1）正常运行结束

指线程体执行完成，线程自动结束。

#### （2）使用退出标志退出线程

在一般情况下，在run方法执行完毕时，线程会正常结束。

然而，有些线程是后台线程，需要 长时间运行，只有在系统满足某些特殊条件后，才能触发关闭这些线程。

#### （3）使用**Interrupt**方法终止线程

使用interrupt方法终止线程有以下两种情况。

#### （4）使用**stop**方法终止线程：不安全

在程序中可以直接调用Thread.stop方法强行终止线程，但这是很危险的，就像突然关闭计算机的电源，而不是正常关机一样，可能会产生不可预料的后果。

在程序使用Thread.stop方法终止线程时，该线程的子线程会抛出ThreadDeatherror错误，并且释 放子线程持有的所有锁。加锁的代码块一般被用于保护数据的一致性，如果在调用Thread.stop方法 后导致该线程所持有的所有锁突然释放而使锁资源不可控制，被保护的数据就可能出现不一致的情况，其他线程在使用这些被破坏的数据时，有可能使程序运行错误。因此，并不推荐采用这种方法终止线程。



## 1.5 Java中的 锁🔐

Java中的锁主要用于保障多并发线程情况下数据的一致性。



- 锁从乐观和悲观的角度：可分为**乐观锁和悲观锁**
- 从获取资源的公平性角度：可分为**公平锁和非公平锁**
- 从是否共享资源的角度：可分为**共享锁和独占锁**
- 从锁的状态的角度：可分为**偏向锁、轻量级锁 和重量级锁**
- 同时，在JVM中还巧妙设计了**自旋锁**以更快地使用CPU资源



下面将详细介绍这些锁。

### 1.5.1 乐观锁

乐观锁采用乐观的思想处理数据，在每次读取数据时都认为别人不会修改该数据，所以不会上 锁，但在更新时会判断在此期间别人有没有更新该数据，通常采用在写时先读出当前版本号然后加锁的方法。

具体过程为：比较当前版本号与上一次的版本号，如果版本号一致，则更新，如果版本号不一致，则重复进行读、比较、写操作。

Java中的乐观锁大部分是通过`CAS`（**Compare And Swap，比较和交换**）操作实现的，CAS是一 种原子更新操作，在对数据操作之前首先会比较当前值跟传入的值是否一样，如果一样则更新，否则不执行更新操作，直接返回失败状态。





### 1.5.2 悲观锁

悲观锁采用悲观思想处理数据，在每次读取数据时都认为别人会修改数据，所以每次在读写数据时都会上锁，这样别人想读写这个数据时就会阻塞、等待直到拿到锁。

Java中的悲观锁大部分基于AQS（Abstract Queued Synchronized，抽象的队列同步器）架构实 现。AQS定义了一套多线程访问共享资源的同步框架，许多同步类的实现都依赖于它，例如常用的Synchronized、ReentrantLock、Semaphore、CountDownLatch等。该框架下的锁会先尝试以CAS乐观锁去获取锁，如果获取不到，则会转为悲观锁（如RetreenLock）。

### 1.5.3 自旋锁



### 1.5.4 synchronized

synchronized关键字用于为**Java对象、方法、代码块**提供线程安全的操作。

synchronized属于独占式的悲观锁，同时属于可重入锁。

<br>

在使用synchronized修饰对象时，同一时刻只能有一个线程对 该对象进行访问；

在synchronized修饰方法、代码块时，同一时刻只能有一个线程执行该方法体或代码块，其他线程只有等待当前线程执行完毕并释放锁资源后才能访问该对象或执行同步代码块。



#### 作用范围

- 作用于成员变量和非静态方法时：锁住的是对象的实例，即this对象。

- 作用于静态方法时：锁住的是Class实例，因为静态方法属于Class而不属于对象。

- 作用于一个代码块时：锁住的是所有代码块中配置的对象。

#### 用法简介

##### （1）对于成员变量和非静态方法

```java
// 定义了两个使用synchronized修饰的普通方法，然后在main函数中定义对象的实例 并发执行各个方法。
// 我们看到，线程 1会等待线程 2执行完成才能执行，这是因为synchronized锁住了当前的对象实例synchronizedDemo1导致的。
public class SynchronizedDemo1 {


    public static void main(String[] args) {
        final SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();

        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo1.generalMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo1.generalMethod2();
            }
        }).start();

    }

    // 修饰普通的同步方法，锁住的是当前实例对象
    public synchronized void generalMethod1(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod1 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 修饰普通的同步方法，锁住的是当前实例对象
    public synchronized void generalMethod2(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod2 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
```

具体的执行结果如下

```java
generalMethod1 execute 0time
generalMethod1 execute 1time
generalMethod1 execute 2time
generalMethod2 execute 0time
generalMethod2 execute 1time
generalMethod2 execute 2time
```

稍微把程序修改一下，定义两个实例分别调用两个方法，程序就能并发执行起来了：

```java
final SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
final SynchronizedDemo1 synchronizedDemo2 = new SynchronizedDemo1();

// 匿名内部类
new Thread(new Runnable() {
    @Override
    public void run() {
        synchronizedDemo1.generalMethod1();
    }
}).start();

new Thread(new Runnable() {
    @Override
    public void run() {
        //synchronizedDemo1.generalMethod2();
        synchronizedDemo2.generalMethod2();
    }
}).start();
```

##### （2）对于静态方法

锁住的是当前类的Class对象，具体的使用代码如下，我们只需在以上方法上加上**static**关键字即可：

```java
public class SynchronizedDemo2 {

    public static void main(String[] args) {
        final SynchronizedDemo2 synchronizedDemo1 = new SynchronizedDemo2();
        final SynchronizedDemo2 synchronizedDemo2 = new SynchronizedDemo2();

        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo1.generalMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo2.generalMethod2();
            }
        }).start();
    }


    // 修饰静态同步方法，锁住的是当前类的 Class对象
    public static synchronized void generalMethod1(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod1 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 修饰静态同步方法，锁住的是当前类的 Class对象
    public static synchronized void generalMethod2(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("generalMethod2 execute " + i + "time");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
```



以上代码首先定义了两个static的synchronized方法，然后定义了两个实例分别执行这两个方法，具体的执行结果如下

```bah
generalMethod1 execute 0time
generalMethod1 execute 1time
generalMethod1 execute 2time
generalMethod2 execute 0time
generalMethod2 execute 1time
generalMethod2 execute 2time
```

我们通过日志能清晰地看到，因为static方法是属于Class的，并且Class的相关数据在JVM中是全局共享的，因此静态方法锁相当于类的一个全局锁，会锁住所有调用该方法的线程。

##### （3）对于代码块

锁住的是在代码块中配置的对象。

```java
public class SynchronizedDemo {

    String lockA = "lockA";

    public static void main(String[] args) {

        final SynchronizedDemo synchronizedDemo = new SynchronizedDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo.blockMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedDemo.blockMethod2();
            }
        }).start();

    }


    // 用于方法块,锁住的是在括号里面配置的对象
    public void blockMethod1(){
        try {
            synchronized (lockA) {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Method 1 execute");
                    Thread.sleep(3000);
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void blockMethod2(){
        try {
            synchronized (lockA) {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Method 2 execute");
                    Thread.sleep(3000);
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
```



以上代码的执行结果很简单，由于两个方法都需要获取名为lockA的锁，所以线程 1会等待线程2执行完成后才能获取该锁并执行

```bash
Method 1 execute
Method 1 execute
Method 1 execute
Method 2 execute
Method 2 execute
Method 2 execute
```



我们在写多线程程序时可能会出现A线程依赖B线程中的资源，而B线程又依赖于A线程中的资 源的情况，这时就可能出现死锁。我们在开发时要杜绝资源相互调用的情况。

如下所示就是一段典型的死锁代码：

```java

```



#### 实现原理

在synchronized内部包括ContentionList、EntryList、WaitSet、OnDeck、Owner、!Owner这6个区域，每个区域的数据都代表锁的不同状态。

- ContentionList：锁竞争队列，所有请求锁的线程都被放在竞争队列中。

- EntryList：竞争候选列表，在Contention List中有资格成为候选者来竞争锁资源的线程被移动到了Entry List中。
- WaitSet：等待集合，调用wait方法后被阻塞的线程将被放在WaitSet中。
- OnDeck：竞争候选者，在同一时刻最多只有一个线程在竞争锁资源，该线程的状态被称为OnDeck。
- Owner：竞争到锁资源的线程被称为Owner状态线程。
- !Owner：在Owner线程释放锁后，会从Owner的状态变成!Owner。

synchronized在收到新的锁请求时首先自旋，如果通过自旋也没有获取锁资源，则将被放入锁竞争队列ContentionList中。



synchronized是一个重量级操作，需要调用操作系统的相关接口，性能较低，给线程加锁的时间有可能超过获取锁后具体逻辑代码的操作时间。

JDK 1.6对synchronized做了很多优化，引入了适应自旋、锁消除、锁粗化、轻量级锁及偏向锁 等以提高锁的效率。锁可以从偏向锁升级到轻量级锁，再升级到重量级锁。这种升级过程叫作锁膨胀。在JDK 1.6中默认开启了偏向锁和轻量级锁，可通过-XX:UseBiasedLocking禁用偏向锁。

#### synchronized的优化手段

##### 锁膨胀/升级

synchronized关键字加的锁既是轻量级锁也是重量级锁，它是根据实际情况自适应加锁的，这种自适应是基于锁膨胀或者说是锁升级这样的优化手段来实现的。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220607220239342.png)

「🌸锁升级过程：」

- 当没有线程加锁的时候，此时为无锁状态。
- 当首个线程进行加锁的时候，此时进入偏向锁的状态，偏向锁不是真的加锁，而是在对象头做个标记而已，

- 当有其他线程进行加锁，导致产生了锁竞争时，此时进入轻量级锁状态。
- 如果竞争进一步加剧，进入重量级锁状态。

##### 锁粗化

- 所谓锁粗化就是将`synchronized`的加锁代码块范围增大，加锁的代码块中的内容越多，锁就越粗，否则锁就越细。

- 一般我们认为，锁越细，多线程间的并发性越高，锁越粗，加锁解锁的开销就会更小。
  - 编译器会对你加的锁做一个优化，如果编译器判定加的锁过细，就会自动粗化，从而提高程序运行效率。

##### 锁消除

有些代码，编译器认为没有加锁的必要，就会自动把你加的锁自动去除，像类似这样的优化，就是锁消除。



### 03 | 互斥锁（上）：解决原子性问题（极客时间专栏内容）

“ **同一时刻，只能有一个线程操作共享对象(数据、代码)** ”这个条件非常重要，我们称之为**互斥**。

如果我们能够保证对共享变量的修改是互斥的，那么，无论是单核 CPU 还是多核 CPU，就都能保证原子性了。



#### （1）简易锁模型

当谈到互斥，相信聪明的你一定想到了那个杀手级解决方案：锁。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220606132706735.png)

我们把一段需要互斥执行的代码称为临界区。

```bash
线程在进入临界区之前，首先尝试加锁 `lock()`，如果成功，则进入临界区，此时我们称这个线程持有锁；
否则呢就等待，直到持有锁的线程解锁；持有锁的线程执行完临界区的代码后，执行解锁 `unlock()`。
```

- 两个非常非常重要的点：我们锁的是什么？我们保护的又是什么？
  - 锁的是共享变量的访问，保护的是共享变量。

#### （2）改进后的锁模型

我们知道在现实世界里，**锁和锁要保护的资源是有对应关系的**，比如你用你家的锁保护你家的东西，我用我家的锁保护我家的东西。

在并发编程世界里，锁和资源也应该有这个关系，如上图中改进后的锁模型。

```bash
首先，我们要把临界区要保护的资源标注出来，如图中临界区里增加了一个元素：受保护的资源 R。
其次，我们要保护资源 R 就得为它创建一把锁 LR；最后，针对这把锁 LR，我们还需在进出临界区时添上加锁操作和解锁操作。

另外，在锁 LR 和受保护资源之间，我特地用一条线做了关联，这个关联关系非常重要。
很多并发 Bug 的出现都是因为把它忽略了，然后就出现了类似锁自家门来保护他家资产的事情。
这样的 Bug 非常不好诊断，因为潜意识里我们认为已经正确加锁了。
```

<br>

明确锁的范围以及能够锁住的资源。 容易出问题的地方： 1、锁住了错误的资源 2、锁的粒度太大，锁住的资源太多，导致性能太低

#### （3） Java 语言提供的锁技术：synchronized

锁是一种通用的技术方案，Java 语言提供的 synchronized 关键字，就是锁的一种实现。

synchronized 关键字可以用来修饰方法，也可以用来修饰代码块，它的使用示例基本上都是下面这个样子：

```java
class X {
  // 修饰非静态方法
  synchronized void foo() {
    // 临界区
  }
  // 修饰静态方法
  synchronized static void bar() {
    // 临界区
  }
  // 修饰代码块
  Object obj = new Object()；
  void baz() {
    synchronized(obj) {
      // 临界区
    }
  }
}  
```

- 这个和我们上面提到的模型有点对不上号啊，加锁 lock() 和解锁 unlock() 在哪里呢？
  - 其实这两个操作都是有的，只是这两个操作是被 Java 默默加上的
  - Java 编译器会在 synchronized 修饰的方法或代码块**前后自动加上加锁 lock() 和解锁 unlock()**
  - 这样做的好处就是加锁 lock() 和解锁 unlock() **一定是成对出现的**，毕竟忘记解锁 unlock() 可是个致命的 Bug（意味着其他线程只能死等下去了）。

- 那 synchronized 里的加锁 lock() 和解锁 unlock() 锁定的对象在哪里呢？
  - 我们看到只有修饰代码块的时候，锁定了一个 obj 对象
  - 那修饰方法的时候锁定的是什么呢？这个也是 Java 的一条隐式规则
    - 当修饰静态方法的时候，锁定的是**当前类的 Class 对象**，在上面的例子中就是 Class X；
    - 当修饰非静态方法的时候，锁定的是**当前实例对象 this**。

对于上面的例子，synchronized 修饰静态方法相当于:

```java
class X {
  // 修饰静态方法
  synchronized(X.class) static void bar() {
    // 临界区
  }
}
```

修饰非静态方法，相当于：

```java
class X {
  // 修饰非静态方法
  synchronized(this) void foo() {
    // 临界区
  }
}
```

#### （4）用 synchronized 解决 count+=1 问题

针对前面文章中提到过的 count+=1 存在的并发问题，现在我们可以尝试用 synchronized 来小试牛刀一把。



```java
class SafeCalc {
  long value = 0L;
  long get() {
    return value;
  }
  
  synchronized void addOne() {
    value += 1;
  }
}
```

- 我们使用的这两个方法有没有并发问题呢？
  - 我们先来看看 addOne() 方法，首先可以肯定，被 synchronized 修饰后，无论是单核 CPU 还是多核 CPU，只有一个线程能够执行 addOne() 方法，所以一定能保证原子操作。

<br>

- 那是否有可见性问题呢？
  - 综合 Happens-Before 的传递性原则，我们就能得出前一个线程在临界区修改的共享变量（该操作在解锁之前），对后续进入临界区（该操作在加锁之后）的线程是可见的。
  - 按照这个规则，如果多个线程同时执行 addOne() 方法，可见性是可以保证的，也就说如果有 1000 个线程执行 addOne() 方法，最终结果一定是 value 的值增加了 1000。看到这个结果，我们长出一口气，问题终于解决了。

<br>

- 但也许，你一不小心就忽视了 get() 方法。执行 addOne() 方法后，value 的值对 get() 方法是可见的吗？
  - 这个可见性是没法保证的。
  - 那如何解决呢？很简单，就是 get() 方法也 synchronized 一下

```java
class SafeCalc {
  long value = 0L;
  synchronized long get() {
    return value;
  }
  synchronized void addOne() {
    value += 1;
  }
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220607090000576.png)



#### （5）锁和受保护资源的关系

受保护资源和锁之间的关联关系是 N:1 的关系。

#### 总结

互斥锁，在并发领域的知名度极高，只要有了并发问题，大家首先容易想到的就是加锁，因为大家都知道，加锁能够保证执行临界区代码的互斥性。

必须深入分析锁定的对象和受保护资源的关系，综合考虑受保护资源的访问路径，多方面考量才能用好互斥锁。

<br>

synchronized 是 Java 在语言层面提供的互斥原语，其实 Java 里面还有很多其他类型的锁，但作为互斥锁，原理都是相通的：锁，一定有一个要锁定的对象，至于这个锁定的对象要保护的资源以及在哪里加锁 / 解锁，就属于设计层面的事情了。







### 7.5 ReentrantLock

ReentrantLock继承了Lock接口并实现了在接口中定义的方法，是一个可重入的独占锁。

ReentrantLock通过自定义队列同步器（Abstract Queued Sychronized，AQS）来实现锁的获取与释放。



ReentrantLock不但提供了synchronized对锁的操作功能，还提供了诸如可响应中断锁、可轮询锁请求、定时锁等避免多线程死锁的方法。

#### ReentrantLock用法

- ReentrantLock 是显式的操作过程，何时加锁、何时释放锁都在程序员的控制之下。
- 具体的使用是定义一个ReentrantLock，通过`lock`方法加锁，`unlock`方法解锁。
  - 由于加锁解锁两个操作是分开的，容易死锁，所以一般要搭配`finally`使用。

```java
ReentrantLock lock = new ReentrantLock(); 
//dosomething
lock.lock();   
try {    
 	// working    
} finally {    
 	lock.unlock()    
}
```



🌸 示例

```java
public class ReentrantLockDemo implements Runnable {

    // 1 定义一个 ReentrantLock
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10; j++) {
            // 2 加锁
            lock.lock();
            // 可重入锁
            //lock.lock();
            try {
                i++;
            } finally {
                // 3 释放锁
                lock.unlock();
                // 可重入锁
                //lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        Thread t1 = new Thread(reentrantLockDemo);

        t1.start();
        t1.join();
        System.out.println(i);
    }
}
```

- ReentrantLock之所以**被称为可重入锁**，是因为ReentrantLock锁可以反复进入。
  - 即允许连续两次 获得同一把锁，两次释放同一把锁。
  - 将上述代码中的注释部分去掉后，程序仍然可以正常执行。注意，获取锁和释放锁的次数要相同。
    - 如果释放锁的次数多于获取锁的次数，Java就会抛出java.lang.IllegalMonitorStateException异常；
    - 如果释放锁的次数少于获取锁的次数，该线程就会一直持有该锁，其他线程将无法获取锁资源。



### ReentrantLock如何避免死锁：响应中断、可轮询锁、定时锁





### Lock接口的主要方法

| 方法                                | 作用                             | 注                                                           |
| ----------------------------------- | -------------------------------- | ------------------------------------------------------------ |
| void lock()                         | 给对象加锁                       | 如果锁未被其他线程使用，则当前线程将获取该锁；如果锁正在被其他线程持有，则将阻塞等待，直到当前线程获取锁。 |
| boolean tryLock()                   | 试图给对象加锁                   |                                                              |
| tryLock(long timeout TimeUnit unit) | 创建定时锁                       | 如果在给定的等待时间内有可用锁，则获取该锁。                 |
| void unlock()                       | 释放当前线程所持有的锁           | 锁只能由持有者释放，如果当前线程并不持有该锁却执行该方法，则抛出异常。 |
| Condition newCondition()            | 创建条件对象，获取等待通知组件。 |                                                              |
| getHoldCount()                      | 查询当前线程保持此锁的次数       | 也就是此线程执行lock方法的次数                               |
| getQueueLength()                    | 返回等待获取此锁的线程估计数     | 比如启动 5 个线程，1 个线程获得锁，此时返回4。               |
|                                     |                                  |                                                              |
|                                     |                                  |                                                              |



### 公平锁与非公平锁（默认）

ReentrantLock支持公平锁和非公平锁两种方式。

公平锁指锁的分配和竞争机制是公平的，即遵循**先到先得原则**。非公平锁指JVM遵循**随机、就近原则**分配锁的机制。

ReentrantLock通过在构造函数ReentrantLock(boolean fair)中传递不同的参数来定义不同类型的 锁，默认的实现是非公平锁。

这是因为，非公平锁虽然放弃了锁的公平性，但是执行效率明显高于公平锁。如果系统没有特殊的要求，一般情况下建议使用非公平锁。



### 7.6 synchronized和ReentrantLock的比较

### 共同点

- 都用于控制多线程对共享对象的访问。
- 都是可重入锁。
- 都保证了可见性和互斥性。

### 不同点

| VS         | ReentrantLock                                             | synchronized               |
| ---------- | --------------------------------------------------------- | -------------------------- |
| 显式或隐式 | 通过`lock`方法加锁，`unlock`方法解锁。                    | 隐式获取和释放锁           |
| 属于       | API级别的                                                 | JVM级别的                  |
| 是否公平   | 可以指定fair参数来决定                                    | 非公平锁                   |
| 出现锁竞争 | 竞争失败时可以阻塞等待，也可以通过trylock方法直接返回退出 | 竞争失败时只能阻塞等待     |
| 等待机制   | `Condition`类                                             | `wait/notify`等待机制      |
| 底层实现   | 同步非阻塞，采用乐观并发策略                              | 同步阻塞，采用悲观并发策略 |

<br>

- ReentrantLock 显式获取和释放锁，synchronized隐式获取和释放锁。
  - 为了避免程序出现异常而无法正常释放锁，使用ReentrantLock 时必须在 finally 语句块中执行释放锁操作。

- ReentrantLock 可响应中断、可轮回，为处理锁提供了更多的灵活性。
- ReentrantLock 是API级别的，是一个java标准类，是使用java代码实现的。`synchronized`是一个关键字，是基于JVM内部实现的，是C/C++代码。
- ReentrantLock 可以定义公平锁。
  - 构造实例对象时，可以指定fair参数来决定该锁对象是公平锁还是非公平锁。
- ReentrantLock 通过Condition可以绑定多个条件。
- 底层实现不同
  - synchronized 同步阻塞，采用悲观并发策略。
  - ReentrantLock 同步非阻塞，采用乐观并发策略。



### 7.8 AtomicInteger

我们知道，在多线程程序中，诸如++i或i++等运算不具有原子性，因此不是安全的线程操作。

我们可以通过synchronized或ReentrantLock将该操作变成一个原子操作，但是synchronized和ReentrantLock均属于重量级锁。

因此JVM为此类原子操作提供了一些原子操作同步类，使得同步操作（线程安全操作）更加方便、高效，它便是AtomicInteger。

<br>

AtomicInteger 为提供原子操作的 Integer 的 类 ， 常见的原子操作类还有 AtomicBoolean 、AtomicInteger、AtomicLong、AtomicReference等，它们的实现原理相同，区别在于运算对象的类型 不同。

还可以通过AtomicReference<V>将一个对象的所有操作都转化成原子操作。AtomicInteger的性能通常是synchronized和ReentrantLock的好几倍。具体用法如下：

```java
public class CASTest3 {
    AtomicInteger i = new AtomicInteger();
    //int i = 0;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        CASTest3 casTest = new CASTest3();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //casTest.i++;
                casTest.i.getAndIncrement();
            }
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //casTest.i++;
                casTest.i.getAndIncrement();
            }
            countDownLatch.countDown();
        }).start();

        //Thread.sleep(900);
        countDownLatch.await();
        System.out.println(casTest.i.get());
    }
}
```



https://stackoverflow.com/questions/72528431/is-atomicinteger-and-countdownlatch-used-correctly





## 1.6 线程上下文切换





## 9 Java阻塞队列





















## 10 Java并发关键字 ⭐️

### CountDownLatch 同步工具类

CountDownLatch类位于java.util.concurrent包下，是一个同步工具类，允许一个或多个线程一直**等待其他线程的操作执行完后再执行相关操作**。

打个比方，假设有一场跑步比赛，一共有5个远动员参赛，只有当最后一个远动员冲过终点线时，裁判才能宣布比赛结束。

这里的运动员就相当于线程，裁判就相当于`CountDownLatch`类。

🌸「常用方法：」

| 序号 | 方法                                            | 方法类型 | 作用                                                  |
| ---- | ----------------------------------------------- | -------- | ----------------------------------------------------- |
| 1    | public CountDownLatch(int count)                | 构造方法 | 构造实例对象，count表示CountDownLatch对象中计数器的值 |
| 2    | public void await() throws InterruptedException | 普通方法 | 使所处的线程进入阻塞等待，直到计数器的值清零          |
| 3    | public void countDown()                         | 普通方法 | 将计数器的值减1                                       |
| 4    | public long getCount()                          | 普通方法 | 获取计数器最初的值                                    |



🌸「使用方式：」

- 创建`CountDownLatch`对象，并初始化计数器的值。
- 在每个线程执行的最后使用`countDown`方法，表示当前线程执行完毕，计数器的值减1。
- 在主线程中使用`await`方法，等待`CountDownLatch`对象的计数器清零，表示所管理的线程全部执行完毕，起到线程同步的作用。

🌸「参考代码：」

```java
public class CountDownLatchTest {
    public static void main(String[] args) {

        // 1、定义大小为2 的 CountDownLatch
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("子线程1正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程1执行完毕");
                    // 2、子线程1执行完毕后调用countDown 方法
                    countDownLatch.countDown();
                } catch (Exception e){
                }
            }
        }.start();

        new Thread(() -> {
            try {
                System.out.println("子线程2正在执行");
                Thread.sleep(3000);
                System.out.println("子线程2执行完毕");
                // 2、子线程2执行完毕后调用countDown 方法
                countDownLatch.countDown();
            } catch (Exception e){
            }
        }).start();


        try {
            System.out.println("等待2个子线程执行完毕...");
            countDownLatch.await();// 3、countDownLatch 上等待子线程执行完毕

            // 4、子线程执行完毕，开始执行主线程
            System.out.println("2个子线程执行完毕，继续执行主线程");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
// 子线程1正在执行
// 等待2个子线程执行完毕...
// 子线程2正在执行
// 子线程1执行完毕
// 子线程2执行完毕
// 2个子线程执行完毕，继续执行主线程
```

子线程执行完业务代码后再执行 **latch.countDown()** 时减少一个信号量，表示自己已经执行完成。

主线程调用 **latch.await()** 阻塞等待，在所有线程都执行完成并调用了countDown函数时，表示所有线程均执行完成，这时程序会主动唤醒主线程并开始执行主线程的业务逻辑。

🌸「参考代码：」

```java
import java.util.concurrent.*;
public class Main {
    public static final int COUNT = 5;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "任务执行完毕！");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        //等待计数器清零，清零前，线程处于阻塞等待状态，清零后，即全部任务执行完毕
        countDownLatch.await();
        System.out.println("任务全部完成！");
    }
}
```



这样的场景在实际开发当中，也是很常见的，**比如要下载一个较大的文件的时候，常常将文件拆分，使用多线程并发下载。**

而在这样一个场景中，需要等待最后一个线程也下载完毕，才能说整个文件下载完毕，也就是使用CountDownLatch对象进行计数，等计数器清零了`await`方法就会返回，表示文件下载完成。





### Semaphore类（信号量）

这个概念比较抽象，我们来打个比方，有个停车场，停车场门口有一个灯牌，会显示停车位还剩余多少个，每进去一辆车，显示的停车位数量就减一，每出去一辆出，显示的停车位数量就加一。

<br>



上面显示停车位数量的灯牌其实就是信号量，信号量是一更加广义的锁，描述了可用资源的个数。

每次申请一个可用资源，信号量中的计数器就减一（P操作）。

每次释放一个可用资源，信号量中的计数器就加一（V操作）。

当可用资源数量为0时，再次进行P操作，会陷入阻塞等待状态。

<br>

锁我们可以理解为“二元信号量”，因为计数器的取值不是0就是1，它的可用资源就一个。

🌸Semaphore类的常用方法：

| 序号 | 方法                                              | 方法类型 | 作用                                                  |
| ---- | ------------------------------------------------- | -------- | ----------------------------------------------------- |
| 1    | public Semaphore(int permits)                     | 构造方法 | 构造可用资源为permits个的信号量对象                   |
| 2    | public Semaphore(int permits, boolean fair)       | 构造方法 | 相比于方法1，该构造方法还能指定信号量是否是公平性质的 |
| 3    | public void acquire() throws InterruptedException | 普通方法 | 申请可用资源                                          |
| 4    | public void release()                             | 普通方法 | 释放可用资源                                          |

🌸代码演示：

```java
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {
        //构造方法中的permits参数表示可用资源的个数
        Semaphore semaphore = new Semaphore(4);
        //每次使用一个可用资源，信号量就会减少1
        semaphore.acquire();
        System.out.println("申请成功");
        semaphore.acquire();
        System.out.println("申请成功");
        semaphore.acquire();
        System.out.println("申请成功");
        semaphore.acquire();
        System.out.println("申请成功");

        System.out.println("线程是否阻塞？" + "没有！");
        ////此时可用资源为0，线程进入阻塞，需要使用release方法释放资源，线程才能继续执行
        // 上面应该写的是：线程才能继续使用可用资源
        semaphore.release();
        System.out.println("释放成功");
        semaphore.acquire();
        System.out.println("线程是否阻塞？" + "没有！");
        System.out.println("申请成功");
    }
}

```

🌸执行结果：

```java
申请成功
申请成功
申请成功
申请成功
释放成功
申请成功

Process finished with exit code 0
```





### volatile关键字的作用

Java除了使用了synchronized保证变量的同步，还使用了稍弱的同步机制，即volatile变量。volatile也用于确保将变量的更新操作通知到其他线程。

volatile变量具备两种特性：

- 一种是**保证该变量对所有线程可见**，在一个线程修改了变量的值 后，新的值对于其他线程是可以立即获取的；
- 一种是volatile**禁止指令重排**，即volatile变量不会被缓 存在寄存器中或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。

volatile主要适用于一个变量被多个线程共享，多个线程均可针对这个变量执行赋值或者读取的操作。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/volatile.png)



volatile在某些场景下可以代替synchronized，但是volatile不能完全取代synchronized的位置，只有在一些特殊场景下才适合使用volatile。比如，必须同时满足下面两个条件才能保证并发环境的线程安全。

-  对变量的写操作不依赖于当前值（比如i++），或者说是单纯的变量赋值（booleanflag=true）。
-  该变量没有被包含在具有其他变量的不变式中，也就是说在不同的volatile变量之间不能互相依赖，只有在状态真正独立于程序内的其他内容时才能使用volatile。



# 11 多线程如何共享数据

# 12 🍂有关数据结构的线程安全类

## 🍁多线程使用顺序表

ArrayList在多线程中是线程不安全的，多线程环境中使用基于写实拷贝实现的**CopyOnWriteArrayList**。

所谓写实拷贝，就是写的时候会创建一个副本，再副本上进行修改，同时如果存在读操作会在原文件数进行查询，等修改完毕后就会将副本“转正”。

## 🍁多线程使用队列

🌸多线程情况下常常使用阻塞队列：

- ArrayBlockingQueue 基于数组实现的阻塞队列

- LinkedBlockingQueue 基于链表实现的阻塞队列
- PriorityBlockingQueue 基于堆实现的带优先级的阻塞队列
- TransferQueue 最多只包含一个元素的阻塞队列

### 🍁多线程使用哈希表

HashMap本身是线程不安全的，将HashMap中的重要方法使用`synchornized`加锁后，就得到了HashTable类。

虽然HashTable类是线程安全的，但是由于是对方法进行无脑加锁，本质加锁的对象是HashTable类的实例对象，这样就会导致锁竞争概率加大，就相当于公司里所有的员工需要请假时都需要找老板签字批准，这样会导致老板非常地忙，这个老板就相当于加锁的哈希表对象，最终会造成哈希表的效率下降。


![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/hashtable.png)



为了解决这个问题，java提供了ConcurrentHashMap类，该类是基于哈希表中的每一个链表对象进行加锁。

线程需要对哪个链表对象进行操作，就在哪里加锁，由于哈希表中链表数量很多，链表对象的元素个数较少，可以有效地降低锁竞争的概率，相当于公司中的老板将权力下放给各个部门，员工请假时只需向所在的部门领导请假即可。

![](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/concurrenthashmap.png)




# 13 Java中的线程调度



# 14 进程调度算法

## 优先调度算法

## 高优先级优先调度

## 时间片的轮转调度算法





# 15 什么是CAS（Compare And Swap）：比较并交换

- CAS算法CAS(*V* ,*E* ,*N* )包含 3个参数
  - *V* 表示要更新的变量
  - *E* 表示预期的值
  - *N* 表示新值

- 在且仅在 *V* 值等于 *E* 值时，才会将*V* 值设为 *N* 。
  - 如果 *V*值和 *E* 值不同，则说明已经有其他线程做了更新，当前线程什么都不做。最后，CAS返回当前*V* 的真实值。

- CAS 的特性：乐观锁
- CAS自旋等待
  - JDK的原子包java.util.concurrent.atomic里面提供了一组原子类。这些原子类的基本特性就是在多线程环境下，在有多个线程同时执行这些类的实例包含的方法时，会有排他性。
  - 其内部便是基于CAS算法实现的
  - 相对于synchronized阻塞算法，CAS是非阻塞算法的一种常见实现。由于CPU的切换比CPU指令集的操作更加耗时，所以CAS的自旋操作在性能上有了很大的提升。

```java

```





# 16 ABA问题（引入版本号）

根据上面的介绍我们知道CAS指令操作的本质是先比较，满足条件后再进行交换，在大部分情况下都能保证线程安全，但是有一种非常极端的情况，那就是一个值被修改后又被改回到原来的值，此时CAS操作也能成功执行，这种情况在大多数的情况是没有影响的，但是也存在问题。

像上述一个值被修改后又被改回来这种情况就是CAS中的ABA问题。

虽说对于大部分场景都不会有问题，但是也存在bug，比如有以下一个场景就说明了ABA问题所产生的bug：

有一天。滑稽老铁到ATM机去取款，使用ATM查询之后，滑稽老铁发现它银行卡的余额还有`200`，于是滑稽老铁想去`100`块给女朋友买小礼物，但是滑稽老铁取款时，在点击取款按钮后机器卡了一下，滑稽老铁下意识又点了一下，假设这两部取款操作执行图如下：

![滑稽老铁取钱](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/ABA1.png)

<br>

如果没有出现意外，即使按下两次取款按钮也是正常的，但是在这两次CAS操作之间，如图滑稽老铁的朋友给它转账了100块，导致第一次CAS扣款100后的余额从100变回到了200，这时第二次CAS操作也会执行成功，导致又被扣款100块，最终余额是100块，这种情况是不合理的，滑稽老铁会组织滑稽大军讨伐银行的，合理的情况应该是第二次CAS仍然失败，最终余额为200元。 

![滑稽老铁被多扣钱](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/ABA2.png)

<br>



为了解决ABA问题造成的bug，可以引入版本号，版本号只能增加不能减少，加载数据的时候，版本号也要一并加载，每一次修改余额都要将版本号加`1`， 在进行CAS操作之前，都要对版本号进行验证，如果版本号与之前加载的版本号不同，则放弃此次CAS指令操作。

![滑稽老铁的救赎](https://java-notes-1308812086.cos.ap-beijing.myqcloud.com/ABA3.png)

上面的这张图是引入版本号之后，滑稽老铁账户余额变化图，我们不难发现余额的变化是合理的。





# 17 什么是AQS

AQS（Abstract Queued Synchronizer）是一个抽象的队列同步器，通过维护一个共享资源状态 （Volatile Int State）和一个先进先出（FIFO）的线程等待队列来实现一个多线程访问共享资源的同步框架。

## 原理

## 状态

## **AQS**共享资源的方式：独占式（Exclusive）和共享式（Share）

独占式：只有一个线程能执行，具体的Java实现有ReentrantLock。

共享式：多个线程可同时执行，具体 的Java实现有Semaphore和CountDownLatch。



# 分割线

## 3. Java线程

- 创建和运行线程
- 查看线程
- 线程 API
- 线程状态





### 3.2 观察多个线程同时运行

主要是理解

- 交替执行
- 谁先谁后，不由我们控制



### 3.3 查看进程线程的方法

#### windows

- 任务管理器可以查看进程和线程数，也可以用来杀死进程
- tasklist 查看进程
- taskkill 杀死进程

#### linux

- ps -fe 查看所有进程
- ps -fT -p <PID> 查看某个进程（PID）的所有线程
- kill 杀死进程
- top 按大写 H 切换是否显示线程
- top -H -p <PID> 查看某个进程（PID）的所有线程

#### Java

- jps 命令查看所有 Java 进程
- jstack <PID> 查看某个 Java 进程（PID）的所有线程状态
- jconsole 来查看某个 Java 进程中线程的运行情况（图形界面）



jconsole 远程监控配置

- 需要以如下方式运行你的 java 类

```java
java -Djava.rmi.server.hostname=`ip地址` -Dcom.sun.management.jmxremote -
Dcom.sun.management.jmxremote.port=`连接端口` -Dcom.sun.management.jmxremote.ssl=是否安全连接 -
Dcom.sun.management.jmxremote.authenticate=是否认证 java类
```

- 修改 /etc/hosts 文件将 127.0.0.1 映射至主机名

如果要认证访问，还需要做如下步骤

- 复制 jmxremote.password 文件
- 修改 jmxremote.password 和 jmxremote.access 文件的权限为 600 即文件所有者可读写
- 连接时填入 controlRole（用户名），R&D（密码）



### 3.4 原理之线程运行

#### 栈与栈帧

Java Virtual Machine Stacks （Java 虚拟机栈）

我们都知道 JVM 中由堆、栈、方法区所组成，其中栈内存是给谁用的呢？其实就是线程，每个线程启动后，虚拟机就会为其分配一块栈内存。

- 每个栈由多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存
- 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法



#### 线程上下文切换（Thread Context Switch）

因为以下一些原因导致 cpu 不再执行当前的线程，转而执行另一个线程的代码

- 线程的 cpu 时间片用完
- 垃圾回收
- 有更高优先级的线程需要运行
- 线程自己调用了 sleep、yield、wait、join、park、synchronized、lock 等方法

当 Context Switch 发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java 中对应的概念就是程序计数器（Program Counter Register），它的作用是记住下一条 jvm 指令的执行地址，是线程私有的

- 状态包括程序计数器、虚拟机栈中每个栈帧的信息，如局部变量、操作数栈、返回地址等
- Context Switch 频繁发生会影响性能





### 3.5 常用方法

| 方法名           | static | 功能说明                                                     | 注意                                                         |
| ---------------- | ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| start()          |        | 启动一个新线程，在新的线程运行 run 方法中的代码              | start 方法只是让线程进入就绪，里面代码不一定立刻运行（CPU 的时间片还没分给它）。每个线程对象的start方法只能调用一次，如果调用了多次会出现IllegalThreadStateException |
| run()            |        | 新线程启动后会调用的方法                                     | 如果在构造 Thread 对象时传递了 Runnable 参数，则线程启动后会调用 Runnable 中的 run 方法，否则默认不执行任何操作。但可以创建 Thread 的子类对象，来覆盖默认行为 |
| join()           |        |                                                              | 等待线程运行结束                                             |
| join(long n)     |        |                                                              | 等待线程运行结束,最多等待 n 毫秒                             |
| getId()          |        | 获取线程长整型的 id                                          | id 唯一                                                      |
| getName()        |        | 获取线程名                                                   |                                                              |
| setName(String)  |        | 修改线程名                                                   |                                                              |
| getPriority()    |        | 获取线程优先级                                               |                                                              |
| setPriority(int) |        | 修改线程优先级                                               | java中规定线程优先级是1~10 的整数，较大的优先级能提高该线程被 CPU 调度的机率 |
| getState()       |        | 获取线程状态                                                 | Java 中线程状态是用 6 个 enum 表示，分别为：NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED |
| isInterrupted()  |        | 判断是否被打断                                               | 不会清除 打断标记                                            |
| isAlive()        |        | 线程是否存活（还没有运行完毕）                               |                                                              |
| interrupt()      |        | 打断线程                                                     | 如果被打断线程正在 sleep，wait，join 会导致被打断的线程抛出 InterruptedException，并清除 打断标记 ；如果打断的正在运行的线程，则会设置 打断标记 ；park 的线程被打断，也会设置 打断标记 |
| interrupted()    | static | 判断当前线程是否被打断                                       | 会清除 打断标记                                              |
| currentThread()  | static | 获取当前正在执行的线程                                       |                                                              |
| sleep(long n)    | static | 让当前执行的线程休眠n毫秒，休眠时让出 cpu 的时间片给其它线程 |                                                              |
| yield()          | static | 提示线程调度器让出当前线程对CPU的使用                        | 主要是为了测试和调试                                         |





### 3.6 start与run

#### 调用run

```java
public static void main(String[] args) {
    Thread t1 = new Thread("t1") {
        @Override
        public void run() {
            log.debug(Thread.currentThread().getName());
            FileReader.read(Constants.MP4_FULL_PATH);
        }
    };
    t1.run();
    log.debug("do other things ...");
}
```

输出

```java
19:39:14 [main] c.TestStart - main
19:39:14 [main] c.FileReader - read [1.mp4] start ...
19:39:18 [main] c.FileReader - read [1.mp4] end ... cost: 4227 ms
19:39:18 [main] c.TestStart - do other things ...
```

程序仍在 main 线程运行， FileReader.read() 方法调用还是同步的



#### 调用start

将上述代码的 t1.run() 改为

```java
t1.start();
```

输出

```java
19:41:30 [main] c.TestStart - do other things ...
19:41:30 [t1] c.TestStart - t1
19:41:30 [t1] c.FileReader - read [1.mp4] start ...
19:41:35 [t1] c.FileReader - read [1.mp4] end ... cost: 4542 ms
```

程序在 t1 线程运行， FileReader.read() 方法调用是异步的



#### 总结

- 直接调用 run 是在主线程中执行了 run，没有启动新的线程

- 使用 start 是启动新的线程，通过新的线程间接执行 run 中的代码



### 3.7 sleep与yield

#### sleep

1. 调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态（阻塞）
2. 其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException
3. 睡眠结束后的线程未必会立刻得到执行
4. 建议用 TimeUnit 的 sleep 代替 Thread 的 sleep 来获得更好的可读性

#### yield

1. 调用 yield 会让当前线程从 Running 进入 Runnable 就绪状态，然后调度执行其它线程
2. 具体的实现依赖于操作系统的任务调度器



#### 线程优先级

- 线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它
- 如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用

```java
Runnable task1 = () -> {
    int count = 0;
    for (;;) {
        System.out.println("---->1 " + count++);
     }
};
Runnable task2 = () -> {
    int count = 0;
    for (;;) {
        // Thread.yield();
        System.out.println(" ---->2 " + count++);
     }
};
Thread t1 = new Thread(task1, "t1");
Thread t2 = new Thread(task2, "t2");
// t1.setPriority(Thread.MIN_PRIORITY);
// t2.setPriority(Thread.MAX_PRIORITY);
t1.start();
t2.start();
```







### 3.8 join方法详解

#### 为什么需要 join

下面的代码执行，打印 r 是什么？



```java
static int r = 0;
public static void main(String[] args) throws InterruptedException {
    test1();
}

private static void test1() throws InterruptedException {
    log.debug("开始");
    Thread t1 = new Thread(() -> {
        log.debug("开始");
        sleep(1);
        log.debug("结束");
        r = 10;
    });
    t1.start();
    log.debug("结果为:{}", r);
    log.debug("结束");
}
```



分析

- 因为主线程和线程 t1 是并行执行的，t1 线程需要 1 秒之后才能算出 r=10
- 而主线程一开始就要打印 r 的结果，所以只能打印出 r=0

解决方法

- 用 sleep 行不行？为什么？
- 用 join，加在 t1.start() 之后即可



#### `*`应用之同步（案例1）

以调用方角度来讲，如果

- 需要等待结果返回，才能继续运行就是同步
- 不需要等待结果返回，就能继续运行就是异步



<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301111701975.png" style="zoom: 33%;" />





#### 等待多个结果

问，下面代码 cost 大约多少秒？

```java
static int r1 = 0;
static int r2 = 0;
public static void main(String[] args) throws InterruptedException {
    test2();
}

private static void test2() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        sleep(1);
        r1 = 10;
     });

    Thread t2 = new Thread(() -> {
        sleep(2);
        r2 = 20;
     });
     
    long start = System.currentTimeMillis();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    long end = System.currentTimeMillis();
    log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
}
```



分析如下

- 第一个 join：等待 t1 时, t2 并没有停止, 而在运行
- 第二个 join：1s 后, 执行到此, t2 也运行了 1s, 因此也只需再等待 1s

如果颠倒两个 join 呢？
最终都是输出

```java
20:45:43.239 [main] c.TestJoin - r1: 10 r2: 20 cost: 2005
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301112416524.png)





#### 有时效的 join

等够时间

```java
static int r1 = 0;
static int r2 = 0;
public static void main(String[] args) throws InterruptedException {
    test3();
}
public static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        sleep(1);
        r1 = 10;
     });
    long start = System.currentTimeMillis();
    t1.start();
    // 线程执行结束会导致 join 结束
    t1.join(1500);
    long end = System.currentTimeMillis();
    log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
}
```

输出：

```java
20:48:01.320 [main] c.TestJoin - r1: 10 r2: 0 cost: 1010
```



没等够时间

```java
static int r1 = 0;
static int r2 = 0;
public static void main(String[] args) throws InterruptedException {
    test3();
}
public static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        sleep(2);
        r1 = 10;
     });
    long start = System.currentTimeMillis();
    t1.start();
    // 线程执行结束会导致 join 结束
    t1.join(1500);
    long end = System.currentTimeMillis();
    log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
}
```


输出：

```java
20:52:15.623 [main] c.TestJoin - r1: 0 r2: 0 cost: 1502
```



### 3.9 interrupt 方法详解

#### 打断 sleep，wait，join 的线程

这几个方法都会让线程进入阻塞状态
打断 sleep 的线程, 会清空打断状态，以 sleep 为例

```java
private static void test1() throws InterruptedException {
    Thread t1 = new Thread(()->{
        sleep(1);
     }, "t1");
    t1.start();
    sleep(0.5);
    t1.interrupt();
    log.debug(" 打断状态: {}", t1.isInterrupted());
}
```

输出：

```java
java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at java.lang.Thread.sleep(Thread.java:340)
    at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
    at cn.itcast.n2.util.Sleeper.sleep(Sleeper.java:8)
    at cn.itcast.n4.TestInterrupt.lambda$test1$3(TestInterrupt.java:59)
    at java.lang.Thread.run(Thread.java:745)
21:18:10.374 [main] c.TestInterrupt - 打断状态: false
```



#### 打断正常运行的线程

打断正常运行的线程, 不会清空打断状态

```java
private static void test2() throws InterruptedException {
    Thread t2 = new Thread(()->{
        while(true) {
            Thread current = Thread.currentThread();
            boolean interrupted = current.isInterrupted();
            if(interrupted) {
                log.debug(" 打断状态: {}", interrupted);
                break;
             }
         }
     }, "t2");
    t2.start();
    
    sleep(0.5);
    t2.interrupt();
}
```

输出：

```java
20:57:37.964 [t2] c.TestInterrupt - 打断状态: true
```



#### 打断 park 线程

打断 park 线程, 不会清空打断状态

```java
private static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        log.debug("park...");
        LockSupport.park();
        log.debug("unpark...");
        log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
     }, "t1");
    t1.start();
    
    sleep(0.5);
    t1.interrupt();
}
```

输出：

```java
21:11:52.795 [t1] c.TestInterrupt - park... 
21:11:53.295 [t1] c.TestInterrupt - unpark... 
21:11:53.295 [t1] c.TestInterrupt - 打断状态：true
```

如果打断标记已经是 true, 则 park 会失效

```java
private static void test4() {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
            log.debug("park...");
            LockSupport.park();
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
         }
     });
    t1.start();
    
    sleep(1);
    t1.interrupt();
}
```

输出：

```java
21:13:48.783 [Thread-0] c.TestInterrupt - park... 
21:13:49.809 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.812 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.813 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.813 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.813 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true
```

> 提示
>
> 可以使用 Thread.interrupted() 清除打断状态



### 3.10 不推荐的方法

还有一些不推荐使用的方法，这些方法已过时，容易破坏同步代码块，造成线程死锁

| 方法名    | static | 功能说明             |
| --------- | ------ | -------------------- |
| stop()    |        | 停止线程运行         |
| suspend() |        | 挂起（暂停）线程运行 |
| resume()  |        | 恢复线程运行         |



### 3.11 主线程与守护线程

默认情况下，Java 进程需要等待所有线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，只要其它非守

护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束。



例：

```java
log.debug("开始运行...");
Thread t1 = new Thread(() -> {
    log.debug("开始运行...");
    sleep(2);
    log.debug("运行结束...");
}, "daemon");
// 设置该线程为守护线程
t1.setDaemon(true);
t1.start();

sleep(1);
log.debug("运行结束...");
```

输出：

```java
08:26:38.123 [main] c.TestDaemon - 开始运行... 
08:26:38.213 [daemon] c.TestDaemon - 开始运行... 
08:26:39.215 [main] c.TestDaemon - 运行结束...
```

> **注意**
>
> - 垃圾回收器线程就是一种守护线程
>
> - Tomcat 中的 Acceptor 和 Poller 线程都是守护线程，所以 Tomcat 接收到 shutdown 命令后，不会等待它们处理完当前请求



### 3.12 五种状态

这是从 **操作系统** 层面来描述的

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301135718727.png)

【初始状态】仅是在语言层面创建了线程对象，还未与操作系统线程关联

【可运行状态】（就绪状态）指该线程已经被创建（与操作系统线程关联），可以由 CPU 调度执行

【运行状态】指获取了 CPU 时间片运行中的状态
当 CPU 时间片用完，会从【运行状态】转换至【可运行状态】，会导致线程的上下文切换

【阻塞状态】

- 如果调用了阻塞 API，如 BIO 读写文件，这时该线程实际不会用到 CPU，会导致线程上下文切换，进入【阻塞状态】

- 等 BIO 操作完毕，会由操作系统唤醒阻塞的线程，转换至【可运行状态】
- 与【可运行状态】的区别是，对【阻塞状态】的线程来说只要它们一直不唤醒，调度器就一直不会考虑调度它们

【终止状态】表示线程已经执行完毕，生命周期已经结束，不会再转换为其它状态



### 3.13 六种状态

这是从 **Java API** 层面来描述的

根据 Thread.State 枚举，分为六种状态

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301142504871.png?w=600)



- `NEW` 线程刚被创建，但是还没有调用 start() 方法
- `RUNNABLE `当调用了 start() 方法之后，注意，Java API 层面的 RUNNABLE 状态涵盖了 操作系统 层面的【可运行状态】、【运行状态】和【阻塞状态】（由于 BIO 导致的线程阻塞，在 Java 里无法区分，仍然认为是可运行）
- `BLOCKED` ， `WAITING` ， `TIMED_WAITING` 都是 Java API 层面对【阻塞状态】的细分，后面会在状态转换一节详述
- `TERMINATED` 当线程代码运行结束



<hr>




### 习题









### 本章小结

本章的重点在于掌握

- 线程创建
- 线程重要 api，如 start，run，sleep，join，interrupt 等
- 线程状态
- 应用方面

> 异步调用：主线程执行期间，其它线程异步执行耗时操作
>
> 提高效率：并行计算，缩短运算时间
>
> 同步等待：join
>
> 统筹规划：合理使用线程，得到最优效果

- 原理方面

> 线程运行流程：栈、栈帧、上下文切换、程序计数器
>
> Thread 两种创建方式 的源码

- 模式方面

> 终止模式之两阶段终止





















<hr>


## 4. 共享模型之管程

- 共享问题
- synchronized
- 线程安全分析
- Monitor
- wait/notify
- 线程状态转换
- 活跃性
- Lock

<hr>


### 4.1 共享带来的问题

#### 小故事









#### Java的体现

两个线程对初始值为 0 的静态变量一个做自增，一个做自减，各做 5000 次，结果是 0 吗？

```java
static int counter = 0;

public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            counter++;
         }
     }, "t1");

    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            counter--;
         }
     }, "t2");

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    log.debug("{}",counter);
}
```



#### 问题分析

以上的结果可能是正数、负数、零。为什么呢？因为 Java 中对静态变量的自增，自减并不是原子操作，要彻底理解，必须从字节码来进行分析
例如对于 i++ 而言（i 为静态变量），实际会产生如下的 JVM 字节码指令：

```bash
getstatic i // 获取静态变量i的值
iconst_1 // 准备常量1
iadd // 自增
putstatic i // 将修改后的值存入静态变量i
```

而对应 i-- 也是类似：

```bash
getstatic i // 获取静态变量i的值
iconst_1 // 准备常量1
isub // 自减
putstatic i // 将修改后的值存入静态变量i
```



而 Java 的内存模型如下，完成静态变量的自增，自减需要在主存和工作内存中进行数据交换：







如果是单线程,以上8行代码是顺序执行（不会交错）没有问题：





但多线程下这 8 行代码可能交错运行：
出现负数的情况：





出现正数的情况：















#### 临界区Critical Section

- 一个程序运行多个线程本身是没有问题的
- 问题出在多个线程访问共享资源

>多个线程读共享资源其实也没有问题
>在多个线程对共享资源读写操作时发生指令交错，就会出现问题

- 一段代码块内如果存在对共享资源的多线程读写操作，称这段代码块为临界区



例如，下面代码中的临界区

```java
static int counter = 0;

static void increment() 
// 临界区
{ 
    counter++; 
}

static void decrement() 
// 临界区
{ 
    counter--; 
}
```





#### 竞态条件Race Condition

多个线程在临界区内执行，由于代码的**执行序列不同**而导致结果无法预测，称之为发生了**竞态条件**



<hr>


### 4.2 synchronized 解决方案

#### `*`应用之互斥

为了避免临界区的竞态条件发生，有多种手段可以达到目的。

- 阻塞式的解决方案：synchronized，Lock
- 非阻塞式的解决方案：原子变量



本次课使用阻塞式的解决方案：synchronized，来解决上述问题，即俗称的【对象锁】，它采用互斥的方式让同一时刻至多只有一个

线程能持有【对象锁】，其它线程再想获取这个【对象锁】时就会阻塞住。这样就能保证拥有锁的线程可以安全的执行临界区内的代

码，不用担心线程上下文切换



> 注意
> 虽然 java 中互斥和同步都可以采用 synchronized 关键字来完成，但它们还是有区别的：
>
> - 互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码
> - 同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点



#### synchronized

语法

```java
synchronized(对象) // 线程1， 线程2(blocked)
{
    临界区
}
```



解决

```java
static int counter = 0;
static final Object room = new Object();

public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            synchronized (room) {
                counter++;
            }
        }
    }, "t1");

    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            synchronized (room) {
                counter--;
            }
        }
    }, "t2");

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    log.debug("{}",counter);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301145711239.png)



你可以做这样的类比：

- synchronized(对象) 中的对象，可以想象为一个房间（room），有唯一入口（门）房间只能一次进入一人进行计算，线程 t1，t2 想象成两个人
- 当线程 t1 执行到 synchronized(room) 时就好比 t1 进入了这个房间，并锁住了门拿走了钥匙，在门内执行count++ 代码
- 这时候如果 t2 也运行到了 synchronized(room) 时，它发现门被锁住了，只能在门外等待，发生了上下文切换，阻塞住了
- 这中间即使 t1 的 cpu 时间片不幸用完，被踢出了门外（不要错误理解为锁住了对象就能一直执行下去哦），这时门还是锁住的，t1 仍拿着钥匙，t2 线程还在阻塞状态进不来，只有下次轮到 t1 自己再次获得时间片时才能开门进入
- 当 t1 执行完 synchronized{} 块内的代码，这时候才会从 obj 房间出来并解开门上的锁，唤醒 t2 线程把钥匙给他。t2 线程这时才可以进入 obj 房间，锁住了门拿上钥匙，执行它的 count-- 代码



用图来表示

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301150125957.png)





#### 思考

synchronized 实际是用对象锁保证了临界区内代码的原子性，临界区内的代码对外是不可分割的，不会被线程切换所打断。

为了加深理解，请思考下面的问题

- 如果把 synchronized(obj) 放在 for 循环的外面，如何理解？-- 原子性
- 如果 t1 synchronized(obj1) 而 t2 synchronized(obj2) 会怎样运作？-- 锁对象
- 如果 t1 synchronized(obj) 而 t2 没有加会怎么样？如何理解？-- 锁对象





#### 面向对象改进

把需要保护的共享变量放入一个类

```java
class Room {
    int value = 0;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public void decrement() {
        synchronized (this) {
            value--;
        }
    }

    public int get() {
        synchronized (this) {
            return value;
        }
    }
}


@Slf4j
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.decrement();
            }
        }, "t2");
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count: {}" , room.get());
    }
}
```





### 4.3 方法上的synchronized

```java
class Test{
    public synchronized void test() {

    }
}

等价于

class Test{
    public void test() {
        synchronized(this) {
        
        }
    }
}
```



```java
class Test{
    public synchronized static void test() {

    }
}

等价于

class Test{
    public static void test() {
        synchronized(Test.class) {

        }
    }
}
```



#### 不加 synchronzied 的方法

不加 synchronzied 的方法就好比不遵守规则的人，不去老实排队（好比翻窗户进去的）



#### 所谓的“线程八锁”

其实就是考察 synchronized 锁住的是哪个对象



##### 成员变量和静态变量是否线程安全？

- 如果它们没有共享，则线程安全
- 如果它们被共享了，根据它们的状态是否能够改变，又分两种情况

>如果只有读操作，则线程安全
>如果有读写操作，则这段代码是临界区，需要考虑线程安全



##### 局部变量是否线程安全？

- 局部变量是线程安全的
- 但局部变量引用的对象则未必

> 如果该对象没有逃离方法的作用访问，它是线程安全的
> 如果该对象逃离方法的作用范围，需要考虑线程安全



##### 局部变量线程安全分析

```java
public static void test1() {
    int i = 10; 
    i++; 
}
```

每个线程调用 test1() 方法时局部变量 i，会在每个线程的栈帧内存中被创建多份，因此不存在共享







### 4.4 变量的线程安全分析





### 4.5 习题



### 4.6 Monitor概念







### wait notify



### wait notify的正确姿势



### Park & Unpark



### 重新理解线程状态转换



### 多把锁

### 活跃性

### ReentrantLock

### 本章小结



















## 5. 共享模型之内存

上一章讲解的 Monitor 主要关注的是访问共享变量时，保证临界区代码的原子性

这一章我们进一步深入学习共享变量在多线程间的【可见性】问题与多条指令执行时的【有序性】问题

<hr>










## 6. 共享模型之无锁

## 7. 共享模型之不可变

## 8. 共享模型之工具







## 参考

https://www.bilibili.com/video/BV16J411h7Rd?p=12&spm_id_from=pageDriver





























