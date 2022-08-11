package thread;

import java.util.concurrent.Semaphore;

/**
 * @author cat
 * @description
 * @date 2022/6/7 下午10:58
 */
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
