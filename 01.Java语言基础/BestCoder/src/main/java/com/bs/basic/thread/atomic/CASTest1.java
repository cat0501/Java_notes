package com.bs.basic.thread.atomic;

import java.util.concurrent.CountDownLatch;

/**
 * @author cat
 * @description
 * @date 2022/6/7 下午4:01
 */
public class CASTest1 {

    int i = 0;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        CASTest1 casTest = new CASTest1();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                casTest.i++;
            }
        }).start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                casTest.i++;
            }
        }).start();

        //Thread.sleep(3000);

        System.out.println(casTest.i);
    }
}
