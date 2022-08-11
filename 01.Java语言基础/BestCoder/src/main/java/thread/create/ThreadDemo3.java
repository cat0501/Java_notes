package thread.create;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午10:22
 */
public class ThreadDemo3 {
    public static void main(String[] args) {
        // 3, 创建Callable任务对象
        Callable<String> myCallable = new MyCallable(100);
        // 4, 把Callable任务对象  交给FutureTask 任务对象
        // FutureTask对象的作用1是：是Runnable的对象（实现了Runnable接口），可以交给Thread了
        // FutureTask对象的作用2是：线程执行完毕之后通过调用其get方法得到线程执行完成的结果
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        // 5, 交给线程处理
        Thread thread = new Thread(futureTask);
        thread.start();

        //System.out.println(Thread.currentThread().getName() + "&&id: " + Thread.currentThread().getId());

        // 获取返回值
        try {
            // 如果futureTask任务没有执行完，这里的代码会等待，直到线程1跑完才提取结果
            // 6 调用get()方法就可以获取Callable线程任务返回的数据并汇总结果
            String s = futureTask.get();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

// 1, 定义一个任务类，实现Callable接口，应该申明线程任务执行完毕后返回结果的数据类型
class MyCallable implements Callable<String>{
    private final int n;

    public MyCallable(int n) {
        this.n = n;
    }

    // 2, 重写call()方法（任务方法）
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + "&&id: " + Thread.currentThread().getId());


        return "子线程执行结果是：" + sum + "";
    }
}
