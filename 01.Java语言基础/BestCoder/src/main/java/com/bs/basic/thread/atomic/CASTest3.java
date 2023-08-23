package com.bs.basic.thread.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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
