package basic.thread.create;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author cat
 * @description
 * @date 2022/6/1 上午10:34
 */
// 1 通过实现Callable接口创建MyCallable2线程
public class MyCallable2 implements Callable<String> {
    private final String name;

    public MyCallable2(String name) {
        this.name = name;
    }

    // call()方法内为线程实现逻辑
    @Override
    public String call() throws Exception {
        return name;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 2 创建一个固定大小为5的线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        // 3 创建多个有返回值的任务列表list
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < 5; i++) {
            // 4 创建一个有返回值的线程实例
            MyCallable2 c = new MyCallable2(i + " 20220601 ");
            // 5 提交线程，获取Future对象并将其保存到Future List中
            Future<String> future = pool.submit(c);
            System.out.println("submit a Callable Thread:" + Thread.currentThread().getId());
            list.add(future);
        }
        // 6 关闭线程池,等待线程执行结束
        pool.shutdown();

        // 7 遍历所有线程的运行结果
        for (Future future : list) {
            System.out.println(future.get().toString());
        }
    }


}
