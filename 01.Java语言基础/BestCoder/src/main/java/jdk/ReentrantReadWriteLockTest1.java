package jdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/2/14 14:19
 *
 * https://blog.csdn.net/qq_42651904/article/details/115026768
 */
public class ReentrantReadWriteLockTest1 {

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 自己运行会发现 在运行写数据是串行的，每次写入数据都需要等待3秒，而读数据则是瞬间10个线程一起读取的
    public static void main(String[] args) {
        Integer test = 2;
        ExecutorService threadPool = Executors.newFixedThreadPool(10);


        for (int i = 0; i < 3; i++) {
            threadPool.execute(() -> {
                try {
                    readWriteLock.writeLock().lockInterruptibly();
                    System.out.println("写入数据" + test);
                    // 假装耗时操作
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            });
        }
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    readWriteLock.readLock().lockInterruptibly();
                    System.out.println("读取数据" + test);
                    // 假装耗时操作
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readWriteLock.readLock().unlock();
                }
            });
        }
    }

}
