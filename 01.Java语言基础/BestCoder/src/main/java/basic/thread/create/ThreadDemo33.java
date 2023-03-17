package basic.thread.create;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 实现有返回值的线程 + 线程池
 */
public class ThreadDemo33 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建固定大小的线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        // 创建有多个返回值的任务列表list
        List<Future> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            // 创建Callable任务对象
            MyCallable33 myCallable = new MyCallable33(i);
            // 提交线程，获取Future对象并将其保存到Future List中
            Future future = pool.submit(myCallable);
            list.add(future);
        }
        // 关闭线程池,等待线程执行结束
        pool.shutdownNow();
        // 遍历所有线程的运行结果
        for (Future future : list) {
            System.out.println(future.get().toString());
        }

    }
}

// 1, 定义一个任务类，实现Callable接口，应该申明线程任务执行完毕后返回结果的数据类型
class MyCallable33 implements Callable<String>{
    private final int n;

    public MyCallable33(int n) {
        this.n = n;
    }

    // 2, 重写call()方法（任务方法）
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        // 查看下当前线程的名字和id
        System.out.println(Thread.currentThread().getName() + "&&id: " + Thread.currentThread().getId());

        return "子线程执行结果是：" + sum + "";
    }
}
