package basic.thread.create;

/**
 * @author cat
 * @description 继承 Thread 类创建线程
 * @date 2022/4/11 下午9:36
 */


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



