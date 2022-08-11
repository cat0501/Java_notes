package thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author cat
 * @description
 * @date 2022/6/7 下午12:41
 */
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
