package basic.thread.thread_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cat
 * @description
 * @date 2022/6/1 下午3:15
 */
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
