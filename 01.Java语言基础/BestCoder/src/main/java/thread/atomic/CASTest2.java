package thread.atomic;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CASTest2 {

    int i = 0;
    static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CASTest2 casTest = new CASTest2();



        //FutureTask<Integer> futureTask = new FutureTask<>(new Task1());
        //FutureTask<Integer> futureTask2 = new FutureTask<>(new Task2());

        //Thread thread1 = new Thread(futureTask);
        //Thread thread2 = new Thread(futureTask2);
        //thread1.start();
        //thread2.start();
        //
        //countDownLatch.await();
        //System.out.println(futureTask.get());


        //new Thread(() -> {
        //    for (int j = 0; j < 100; j++) {
        //        try {
        //            Thread.sleep(10);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        casTest.i++;
        //
        //    }
        //    countDownLatch.countDown();
        //}).start();

        new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                casTest.i++;
            }
            countDownLatch.countDown();
        }).start();

        //Thread.sleep(3000);
        countDownLatch.await();
        System.out.println(casTest.i);
    }

    //static class Task1 implements Callable<Integer>{
    //    @Override
    //    public Integer call() throws Exception {
    //        for (int j = 0; j < 100; j++) {
    //            try {
    //                Thread.sleep(10);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //            i++;
    //        }
    //        countDownLatch.countDown();
    //        return i;
    //    }
    //}
    //
    //static class Task2 implements Callable<Integer>{
    //    @Override
    //    public Integer call() throws Exception {
    //        for (int j = 0; j < 100; j++) {
    //            try {
    //                Thread.sleep(10);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //            i++;
    //        }
    //        countDownLatch.countDown();
    //        return i;
    //    }
    //}
}
