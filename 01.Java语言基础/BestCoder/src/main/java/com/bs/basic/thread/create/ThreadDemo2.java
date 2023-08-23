package com.bs.basic.thread.create;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午9:53
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        // 3, 创建一个任务对象
        ChildrenClassThread childrenClassThread = new ChildrenClassThread();
        // 4, 交给线程对象,并启动线程
        new Thread(childrenClassThread).start();
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程执行输出..." + i);
        }
    }

}



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
