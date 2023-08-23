package com.bs.basic.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cat
 * @description
 * @date 2022/6/1 下午8:45
 */
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
